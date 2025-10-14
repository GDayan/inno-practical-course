package org.innowise.core;

/**
 * BeanDefinition holds metadata about a bean, such as its class and scope.
 */
public class BeanDefinition {
    private final Class<?> beanClass;
    private final String scope;

    /**
     * Constructs a BeanDefinition.
     * @param beanClass the class of the bean
     * @param scope the bean scope ("singleton" or "prototype")
     */
    public BeanDefinition(Class<?> beanClass, String scope){
        this.beanClass = beanClass;
        this.scope = scope;
    }

    public Class<?> getBeanClass() { return beanClass; }
    public String getScope() { return scope; }
    public boolean isSingleton() { return "singleton".equals(scope); }
    public boolean isPrototype() { return "prototype".equals(scope); }
}
