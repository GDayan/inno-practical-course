package org.innowise.test;

import org.innowise.core.MiniApplicationContext;
import org.innowise.example.PrototypeService;
import org.innowise.example.UserRepository;
import org.innowise.example.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MiniSpringTest {

    @Test
    void testSingletonBean() {
        MiniApplicationContext context = new MiniApplicationContext("org.innowise.example");

        UserService userService1 = context.getBean(UserService.class);
        UserService userService2 = context.getBean(UserService.class);

        assertNotNull(userService1);
        assertNotNull(userService2);
        assertSame(userService1, userService2, "UserService should be singleton");
    }

    @Test
    void testDependencyInjection() {
        MiniApplicationContext context = new MiniApplicationContext("org.innowise.example");
        UserService userService = context.getBean(UserService.class);

        assertNotNull(userService);
        assertEquals("User: Dayan processed via UserService", userService.getUserInfo());
        assertEquals("User #123: Dayan processed via UserService", userService.getUserInfoById(123));
    }

    @Test
    void testPrototypeBean() {
        MiniApplicationContext context = new MiniApplicationContext("org.innowise.example");

        PrototypeService proto1 = context.getBean(PrototypeService.class);
        PrototypeService proto2 = context.getBean(PrototypeService.class);
        PrototypeService proto3 = context.getBean(PrototypeService.class);

        assertNotSame(proto1, proto2);
        assertNotSame(proto2, proto3);
        assertNotSame(proto1, proto3);
    }

    @Test
    void testBeanStatistics() {
        MiniApplicationContext context = new MiniApplicationContext("org.innowise.example");

        assertTrue(context.getBeanNames().contains(UserService.class));
        assertTrue(context.getBeanNames().contains(UserRepository.class));
        assertTrue(context.getBeanNames().contains(PrototypeService.class));

        assertEquals(2, context.getSingletonBeanCount());
    }
}

