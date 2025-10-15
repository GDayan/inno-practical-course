# MiniSpring - Simplified IoC Container

MiniSpring is a **lightweight implementation of a simplified Spring Framework**, designed to demonstrate **Inversion of Control (IoC)** and **Dependency Injection (DI)** concepts in Java.  
This project is intended for educational purposes and provides hands-on experience with custom annotations, reflection-based bean instantiation, and basic bean scopes.

---

## Features

1. **IoC Container (`MiniApplicationContext`)**
    - Scans a specified package for classes annotated with `@Component`.
    - Registers and instantiates beans automatically.
    - Provides `getBean(Class<T> type)` to retrieve bean instances.

2. **Custom Annotations**
    - `@Component` - Marks a class as a managed bean.
    - `@Autowired` - Injects dependencies automatically into fields.
    - `@Scope("prototype")` (optional) - Creates a new instance of the bean on each request.

3. **Dependency Injection**
    - Beans are instantiated via reflection.
    - Fields annotated with `@Autowired` are injected automatically.

4. **Bean Lifecycle**
    - Supports the `InitializingBean` interface with `afterPropertiesSet()` method, called after dependencies are injected.

5. **Prototype Scope**
    - Beans annotated with `@Scope("prototype")` are created **on demand** instead of being singletons.

---