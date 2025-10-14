package org.innowise.core;

import org.innowise.annotation.Autowired;
import org.innowise.annotation.Component;
import org.innowise.annotation.Scope;
import org.innowise.lifecycle.InitializingBean;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class MiniApplicationContext {
    private final Map<Class<?>, Object> singletonBeans = new HashMap<>();
    private final Map<Class<?>, BeanDefinition> beanDefinitions = new HashMap<>();
    private final String basePackage;

    public MiniApplicationContext(String basePackage){
        this.basePackage = basePackage;
        scanAndInstantiateBeans();
    }

    private void scanAndInstantiateBeans(){
        try{
            List<Class<?>> classes = getClassesInPackage(basePackage);

            for(Class<?> clazz : classes){
                if(clazz.isAnnotationPresent(Component.class)){
                    String scope = getScope(clazz);
                    beanDefinitions.put(clazz, new BeanDefinition(clazz, scope));
                }
            }

            for(BeanDefinition beanDefinition : beanDefinitions.values()){
                if(beanDefinition.isSingleton()){
                    Object bean = createBean(beanDefinition.getBeanClass());
                    singletonBeans.put(beanDefinition.getBeanClass(), bean);
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException("Error while scanning the package: " + basePackage);
        }


    }

    private List<Class<?>> getClassesInPackage(String packageName) throws Exception{
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(path);

        if(resource == null){
            throw new RuntimeException("Package not found: " + packageName);
        }

        File directory = new File(resource.getFile());
        if(directory.exists()){
            scanDirectory(packageName, directory, classes);
        }

        return classes;
    }

    private void scanDirectory(String packageName, File directory, List<Class<?>> classes){
        File[] files = directory.listFiles();
        if(files == null){
            return;
        }

        for(File file : files){
            if(file.isDirectory()){
                scanDirectory(packageName + "." + file.getName(), file, classes);
            } else if(file.getName().endsWith(".class")){
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);

                try{
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e){
                    System.err.println("Not download class: " + className);
                }
            }

        }
    }

    private String getScope(Class<?> clazz){
        if(clazz.isAnnotationPresent(Scope.class)){
            Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
            return scopeAnnotation.value();
        }
        return "singleton";
    }

    private Object createBean(Class<?> clazz) throws Exception {
        Object instance = clazz.getDeclaredConstructor().newInstance();
        injectDependencies(instance);

        if(instance instanceof InitializingBean){
            ((InitializingBean) instance).afterPropertiesSet();
        }

        return instance;
    }

    private void injectDependencies(Object bean)throws Exception{
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields){
            if(field.isAnnotationPresent(Autowired.class)){
                field.setAccessible(true);

                Class<?> fieldType = field.getType();

                Object dependency = getBean(fieldType);
                if(dependency == null){
                    throw new RuntimeException("Not found bean fot this type: " + fieldType);
                }
                field.set(bean, dependency);
            }
        }
    }

    @SuppressWarnings("unckecked")
    public <T> T getBean(Class<T> type){
        Object bean = singletonBeans.get(type);
        if(bean == null){
            return (T) bean;
        }

        BeanDefinition beanDefinition = findBeanDefinition(type);
        if(beanDefinition != null){
            if(beanDefinition.isPrototype()){
                try{
                    return (T) createBean(beanDefinition.getBeanClass());
                }
                catch (Exception e){
                    throw new RuntimeException("Error while creating a prototype bean: " + type, e);
                }
            }
        }

        for(Class<?> beanClass : singletonBeans.keySet()){
            if(type.isAssignableFrom(beanClass)){
                return (T) singletonBeans.get(beanClass);
            }
        }

        throw new RuntimeException("Bean bot found for this type: " + type);
    }

    private BeanDefinition findBeanDefinition(Class<?> type){
        BeanDefinition definition = beanDefinitions.get(type);
        if(definition != null){
            return definition;
        }

        for(BeanDefinition beanDefinition : beanDefinitions.values()){
            if(type.isAssignableFrom(beanDefinition.getBeanClass())){
                return beanDefinition;
            }
        }

        return null;
    }
}
