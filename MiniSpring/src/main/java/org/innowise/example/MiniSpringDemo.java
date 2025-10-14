package org.innowise.example;

import org.innowise.core.MiniApplicationContext;

public class MiniSpringDemo {
    public static void main(String[] args) {
        MiniApplicationContext context = new MiniApplicationContext("org.innowise.example");

        System.out.println("\n=== Working with singleton beans ===");
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.getUserInfo());
        System.out.println(userService.getUserInfoById(123));

        System.out.println("\n=== Demonstrating prototype scope ===");
        PrototypeService proto1 = context.getBean(PrototypeService.class);
        PrototypeService proto2 = context.getBean(PrototypeService.class);
        PrototypeService proto3 = context.getBean(PrototypeService.class);

        System.out.println(proto1.getInfo());
        System.out.println(proto2.getInfo());
        System.out.println(proto3.getInfo());

        System.out.println("\n=== Statistics ===");
        System.out.println("Total registered beans: " + context.getBeanNames().size());
        System.out.println("Singleton beans created: " + context.getSingletonBeanCount());

        System.out.println("\n=== Checking the same singleton instance ===");
        UserService sameUserService = context.getBean(UserService.class);
        System.out.println("Same instance? " + (userService == sameUserService));
    }
}
