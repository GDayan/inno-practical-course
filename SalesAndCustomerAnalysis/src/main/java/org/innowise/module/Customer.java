package org.innowise.module;

import java.time.LocalDateTime;

/**
 * Represents a customer of the online store.
 */
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
    private int age;
    private String city;

    /**
     * Constructs a Customer instance.
     *
     * @param customerId   unique identifier of the customer
     * @param name         name of the customer
     * @param email        email address
     * @param registeredAt registration date
     * @param age          age of the customer
     * @param city         city of the customer
     */
    public Customer(String customerId, String name, String email, LocalDateTime registeredAt, int age, String city) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.registeredAt = registeredAt;
        this.age = age;
        this.city = city;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }
}
