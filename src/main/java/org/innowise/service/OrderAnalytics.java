package org.innowise.service;

import org.innowise.model.Customer;
import org.innowise.model.Order;
import org.innowise.model.OrderItem;
import org.innowise.model.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for analyzing a list of {@link Order} objects
 * and calculating various business metrics using Stream API.
 */
public class OrderAnalytics {

    /**
     * Returns a set of unique cities from which the orders came.
     *
     * @param orders the list of orders to analyze
     * @return a {@link Set} of unique city names
     */
    public static Set<String> getUniqueCities(List<Order> orders){
        return orders.stream()
                .map(order -> order.getCustomer().getCity())
                .collect(Collectors.toSet());
    }

    /**
     * Calculates the total income from all delivered orders.
     * Income is calculated as the sum of (price * quantity) of all items in delivered orders.
     *
     * @param orders the list of orders to analyze
     * @return the total income as a double
     */
    public static double getTotalIncome(List<Order> orders){
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    /**
     * Finds the most popular product by total quantity sold across all orders.
     *
     * @param orders the list of orders to analyze
     * @return an {@link Optional} containing the name of the most popular product,
     *         or {@link Optional#empty()} if there are no orders
     */
    public static Optional<String> getMostPopularProduct(List<Order> orders){
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    /**
     * Calculates the average check (total order value) for delivered orders.
     * The total order value is calculated as the sum of (price * quantity) of all items in the order.
     *
     * @param orders the list of orders to analyze
     * @return the average check value as a double, or 0.0 if there are no delivered orders
     */
    public static double getAverageCheck(List<Order> orders){
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(item -> item.getQuantity() * item.getPrice())
                        .sum())
                .average()
                .orElse(0.0);
    }

    /**
     * Returns a list of customers who have more than 5 orders.
     *
     * @param orders the list of orders to analyze
     * @return a {@link List} of {@link Customer} objects who have placed more than 5 orders
     */
    public static List<Customer> getCustomersWithMoreThanFiveOrders(List<Order> orders){
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
