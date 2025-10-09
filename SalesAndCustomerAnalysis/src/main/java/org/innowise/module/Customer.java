package org.innowise.module;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a customer of the online store.
 * Contains customer personal information and registration details.
 */
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
    private int age;
    private String city;

    /**
     * Constructs a new Customer with specified parameters.
     *
     * @param customerId the unique identifier of the customer
     * @param name the full name of the customer
     * @param email the email address of the customer
     * @param registeredAt the date and time when the customer registered
     * @param age the age of the customer
     * @param city the city where the customer resides
     */
    public Customer(String customerId, String name, String email,
                    LocalDateTime registeredAt, int age, String city) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.registeredAt = registeredAt;
        this.age = age;
        this.city = city;
    }

    /**
     * Returns the customer identifier.
     *
     * @return the customer ID
     */
    public String getCustomerId() { return customerId; }

    /**
     * Returns the customer's full name.
     *
     * @return the customer name
     */
    public String getName() { return name; }

    /**
     * Returns the customer's email address.
     *
     * @return the email address
     */
    public String getEmail() { return email; }

    /**
     * Returns the registration date and time.
     *
     * @return the registration timestamp
     */
    public LocalDateTime getRegisteredAt() { return registeredAt; }

    /**
     * Returns the customer's age.
     *
     * @return the age
     */
    public int getAge() { return age; }

    /**
     * Returns the city where the customer resides.
     *
     * @return the city name
     */
    public String getCity() { return city; }

    /**
     * Compares this customer to the specified object for equality.
     * Two customers are considered equal if they have the same customerId.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId);
    }

    /**
     * Returns a hash code value for this customer.
     * The hash code is based on the customerId.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}