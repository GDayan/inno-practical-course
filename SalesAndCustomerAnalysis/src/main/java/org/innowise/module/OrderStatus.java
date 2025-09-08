package org.innowise.module;

/**
 * Enum representing the status of an order.
 */
public enum OrderStatus {
    NEW,            // Order is created but not yet processed
    PROCESSING,     // Order is being prepared
    SHIPPED,        // Order has been shipped
    DELIVERED,      // Order has been delivered to the customer
    CANCELLED       // Order was cancelled
}
