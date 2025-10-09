package org.innowise.module;

/**
 * Enum representing possible order statuses in the system.
 * Tracks the progression of an order from creation to completion.
 */
public enum OrderStatus {
    /** Order has been created but not yet processed */
    NEW,
    /** Order is being prepared for shipment */
    PROCESSING,
    /** Order has been shipped to the customer */
    SHIPPED,
    /** Order has been successfully delivered to the customer */
    DELIVERED,
    /** Order has been cancelled */
    CANCELLED
}