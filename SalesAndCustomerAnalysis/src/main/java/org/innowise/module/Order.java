package org.innowise.module;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an order in the online store.
 * Contains information about the order, customer, items, and status.
 */
public class Order {
    private String orderId;
    private LocalDateTime orderDate;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;

    /**
     * Constructs a new Order with specified parameters.
     *
     * @param orderId the unique identifier of the order
     * @param orderDate the date and time when the order was placed
     * @param customer the customer who placed the order
     * @param items the list of items in the order
     * @param status the current status of the order
     */
    public Order(String orderId, LocalDateTime orderDate, Customer customer,
                 List<OrderItem> items, OrderStatus status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.items = items;
        this.status = status;
    }

    /**
     * Returns the order identifier.
     *
     * @return the order ID
     */
    public String getOrderId() { return orderId; }

    /**
     * Returns the date and time when the order was placed.
     *
     * @return the order date
     */
    public LocalDateTime getOrderDate() { return orderDate; }

    /**
     * Returns the customer who placed the order.
     *
     * @return the customer object
     */
    public Customer getCustomer() { return customer; }

    /**
     * Returns the list of items in the order.
     *
     * @return the list of order items
     */
    public List<OrderItem> getItems() { return items; }

    /**
     * Returns the current status of the order.
     *
     * @return the order status
     */
    public OrderStatus getStatus() { return status; }
}