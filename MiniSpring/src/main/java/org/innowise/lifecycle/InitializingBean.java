package org.innowise.lifecycle;

/**
 * Interface for beans that require a callback after all properties are set.
 */
public interface InitializingBean {
    /**
     * Called by the container after all dependencies have been injected.
     * @throws Exception if initialization fails
     */
    void afterPropertiesSet() throws Exception;
}
