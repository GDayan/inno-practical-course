package org.innowise.module;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an order placed by a customer.
 */
public class Order {
    private String orderId;
    private LocalDateTime orderDate;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;

    /**
     * Constructs an Order instance.
     *
     * @param orderId   unique identifier of the order
     * @param orderDate date and time when the order was placed
     * @param customer  customer who placed the order
     * @param items     list of items in the order
     * @param status    current status of the order
     */
    public Order(String orderId, LocalDateTime orderDate, Customer customer, List<OrderItem> items, OrderStatus status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.items = items;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
