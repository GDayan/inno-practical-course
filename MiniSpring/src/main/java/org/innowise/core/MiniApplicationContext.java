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

/**
 * MiniApplicationContext is a lightweight IoC container for managing beans.
 * It supports component scanning, dependency injection, singleton and prototype scopes,
 * and the InitializingBean lifecycle callback.
 */
public class MiniApplicationContext {

    private final Map<Class<?>, Object> singletonBeans = new HashMap<>();
    private final Map<Class<?>, BeanDefinition> beanDefinitions = new HashMap<>();

    /**
     * Constructs a MiniApplicationContext and scans the specified package for components.
     * @param basePackage the package to scan for @Component annotated classes
     */
    public MiniApplicationContext(String basePackage) {
        System.out.println("Starting MiniSpring container...");
        scanAndInstantiateBeans(basePackage);
        System.out.println("Container initialized successfully.\n");
    }

    /**
     * Retrieves a bean of the specified type from the container.
     * @param type the class type of the bean
     * @param <T> the bean type
     * @return an instance of the bean
     * @throws RuntimeException if the bean cannot be found or created
     */
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

    /**
     * Returns all registered bean classes in the container.
     * @return a set of bean classes
     */
    public Set<Class<?>> getBeanNames() {
        return beanDefinitions.keySet();
    }

    /**
     * Returns the number of singleton beans currently instantiated.
     * @return singleton bean count
     */
    public int getSingletonBeanCount() {
        return singletonBeans.size();
    }

    /**
     * Scans the given package for classes annotated with @Component
     * and registers their BeanDefinitions.
     * @param basePackage the package to scan
     */
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

    /**
     * Recursively scans the directory for .class files, loads classes,
     * and registers BeanDefinitions if annotated with @Component.
     * @param packageName the current package name
     * @param directory the directory to scan
     */
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
                    System.err.println("Could not load class: " + className);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Creates an instance of the given class, injects dependencies,
     * and calls afterPropertiesSet() if the bean implements InitializingBean.
     * @param clazz the class to instantiate
     * @return the created bean instance
     * @throws Exception if creation or injection fails
     */
    private Object createBean(Class<?> clazz) throws Exception {
        Object instance = clazz.getDeclaredConstructor().newInstance();
        injectDependencies(instance);

        if (instance instanceof InitializingBean) {
            ((InitializingBean) instance).afterPropertiesSet();
        }

        System.out.println("Created bean: " + clazz.getSimpleName());
        return instance;
    }

    /**
     * Injects dependencies into fields annotated with @Autowired.
     * @param instance the bean instance
     * @throws Exception if injection fails
     */
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

    /**
     * Finds the BeanDefinition for a given type.
     * Supports assigning superclasses and interfaces.
     * @param type the class type
     * @return the BeanDefinition or null if not found
     */
    private BeanDefinition findBeanDefinition(Class<?> type) {
        for (BeanDefinition definition : beanDefinitions.values()) {
            if (type.isAssignableFrom(definition.getBeanClass())) {
                return definition;
            }
        }
        return null;
    }
}
