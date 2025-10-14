package org.innowise.core;

import org.innowise.annotation.Autowired;
import org.innowise.annotation.Component;
import org.innowise.annotation.Scope;
import org.innowise.lifecycle.InitializingBean;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MiniApplicationContext {

    private final Map<Class<?>, Object> singletonBeans = new HashMap<>();
    private final Map<Class<?>, BeanDefinition> beanDefinitions = new HashMap<>();

    public MiniApplicationContext(String basePackage) {
        System.out.println("Starting MiniSpring container");
        scanAndInstantiateBeans(basePackage);
        System.out.println("Container initialized successfully.\n");
    }

    private void scanAndInstantiateBeans(String basePackage) {
        try {
            String path = basePackage.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(path);

            if (resource == null) {
                throw new RuntimeException("Package not found: " + basePackage);
            }

            File directory = new File(resource.toURI());
            scanDirectory(basePackage, directory);

            for (BeanDefinition definition : beanDefinitions.values()) {
                if (definition.isSingleton()) {
                    getBean(definition.getBeanClass());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while scanning the package: " + basePackage, e);
        }
    }

    private void scanDirectory(String packageName, File directory) {
        if (!directory.exists()) return;

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(packageName + "." + file.getName(), file);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                try {
                    Class<?> clazz = Class.forName(className);

                    if (clazz.isAnnotationPresent(Component.class)) {
                        String scope = "singleton";

                        if (clazz.isAnnotationPresent(Scope.class)) {
                            scope = clazz.getAnnotation(Scope.class).value();
                        }

                        BeanDefinition definition = new BeanDefinition(clazz, scope);
                        beanDefinitions.put(clazz, definition);
                        System.out.println("Found component: " + clazz.getSimpleName() +
                                " [" + scope + "]");
                    }

                } catch (ClassNotFoundException e) {
                    System.err.println("⚠️ Could not load class: " + className);
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        Object existingBean = singletonBeans.get(type);
        if (existingBean != null) {
            return (T) existingBean;
        }

        BeanDefinition definition = findBeanDefinition(type);
        if (definition == null) {
            throw new RuntimeException("Bean not found for this type: " + type);
        }

        try {
            if (definition.isSingleton()) {
                Object bean = createBean(definition.getBeanClass());
                singletonBeans.put(type, bean);
                return (T) bean;
            } else if (definition.isPrototype()) {
                return (T) createBean(definition.getBeanClass());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while creating bean: " + type, e);
        }

        throw new RuntimeException("Unknown scope for bean: " + type);
    }

    private Object createBean(Class<?> clazz) throws Exception {
        Object instance = clazz.getDeclaredConstructor().newInstance();

        injectDependencies(instance);

        if (instance instanceof InitializingBean) {
            ((InitializingBean) instance).afterPropertiesSet();
        }

        System.out.println("Created bean: " + clazz.getSimpleName());
        return instance;
    }

    private void injectDependencies(Object instance) throws Exception {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Object dependency = getBean(field.getType());
                field.setAccessible(true);
                field.set(instance, dependency);
                System.out.println("Injected dependency: " + field.getType().getSimpleName() +
                        " into " + instance.getClass().getSimpleName());
            }
        }
    }

    private BeanDefinition findBeanDefinition(Class<?> type) {
        for (BeanDefinition definition : beanDefinitions.values()) {
            if (type.isAssignableFrom(definition.getBeanClass())) {
                return definition;
            }
        }
        return null;
    }

    public Set<Class<?>> getBeanNames() {
        return beanDefinitions.keySet();
    }

    public int getSingletonBeanCount() {
        return singletonBeans.size();
    }
}
