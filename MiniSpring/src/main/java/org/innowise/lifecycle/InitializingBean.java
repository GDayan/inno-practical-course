package org.innowise.lifecycle;

public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
