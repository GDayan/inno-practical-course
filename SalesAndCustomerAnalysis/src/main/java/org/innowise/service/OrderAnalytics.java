package org.innowise.service;

import org.innowise.module.Customer;
import org.innowise.module.Order;
import org.innowise.module.OrderItem;
import org.innowise.module.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for analyzing orders in an online store.
 * Provides methods to calculate various business metrics using Java Stream API.
 */
public class OrderAnalytics {

    /**
     * Returns a set of unique cities from which orders were placed.
     *
     * @param orders list of orders to analyze
     * @return set of unique city names
     */
    public static Set<String> getUniqueCities(List<Order> orders) {
        return orders.stream()
                .map(order -> order.getCustomer().getCity())
                .collect(Collectors.toSet());
    }

    /**
     * Calculates the total income from all completed (DELIVERED) orders.
     * The income is calculated as the sum of (price * quantity) for each item in each delivered order.
     *
     * @param orders list of orders to analyze
     * @return total income of delivered orders
     */
    public static double getTotalIncome(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Finds the most popular product by total quantity sold across all orders.
     *
     * @param orders list of orders to analyze
     * @return an Optional containing the name of the most popular product,
     *         or Optional.empty() if there are no products
     */
    public static Optional<String> getMostPopularProduct(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    /**
     * Calculates the average check (average order value) for successfully delivered orders.
     * The order value is calculated as the sum of (price * quantity) for all items in the order.
     *
     * @param orders list of orders to analyze
     * @return average order value of delivered orders, or 0.0 if no orders were delivered
     */
    public static double getAverageCheck(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                        .sum())
                .average()
                .orElse(0.0);
    }

    /**
     * Returns a list of customers who have placed more than five orders.
     *
     * @param orders list of orders to analyze
     * @return list of frequent customers (more than five orders)
     */
    public static List<Customer> getCustomersWithMoreThanFiveOrders(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)
                .toList();
    }
}
