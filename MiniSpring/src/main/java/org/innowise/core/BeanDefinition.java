package org.innowise.core;

public class BeanDefinition {
    private final Class<?> beanClass;
    private final String scope;

    public BeanDefinition(Class<?> beanClass, String scope){
        this.beanClass = beanClass;
        this.scope = scope;
    }

    public Class<?> getBeanClass(){
        return beanClass;
    }

    public String getScope(){
        return scope;
    }

    public boolean isSingleton(){
        return "singleton".equals(scope);
    }

    public boolean isPrototype(){
        return "prototype".equals(scope);
    }
}
