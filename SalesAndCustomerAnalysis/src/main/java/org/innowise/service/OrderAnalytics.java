package org.innowise.service;

import org.innowise.module.Customer;
import org.innowise.module.Order;
import org.innowise.module.OrderItem;
import org.innowise.module.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for analyzing order data and generating business metrics.
 * Provides various analytical methods using Stream API for processing order collections.
 */
public class OrderAnalytics {

    /**
     * Retrieves a set of unique cities from which orders have been placed.
     * Processes all orders and extracts customer cities, filtering out null values.
     *
     * @param orders the list of orders to analyze
     * @return a set of unique city names where orders originated from
     */
    public Set<String> getUniqueCities(List<Order> orders) {
        return orders.stream()
                .map(order -> order.getCustomer().getCity())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Calculates the total income from all completed (delivered) orders.
     * Only considers orders with status DELIVERED and sums the total value of all items.
     *
     * @param orders the list of orders to analyze
     * @return the total income from delivered orders as a double value
     */
    public double getTotalIncomeFromCompletedOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    /**
     * Finds the most popular product based on total quantity sold across all non-cancelled orders.
     * Groups products by name and sums their quantities, then returns the product with highest total.
     *
     * @param orders the list of orders to analyze
     * @return the name of the most popular product, or "No products found" if no products exist
     */
    public String getMostPopularProduct(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getProductName,
                        Collectors.summingInt(OrderItem::getQuantity)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No products found");
    }

    /**
     * Calculates the average order value for successfully delivered orders.
     * Computes the total value of each delivered order and calculates the average across all delivered orders.
     *
     * @param orders the list of orders to analyze
     * @return the average order value for delivered orders, or 0.0 if no delivered orders exist
     */
    public double getAverageCheckForDeliveredOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(item -> item.getQuantity() * item.getPrice())
                        .sum())
                .average()
                .orElse(0.0);
    }

    /**
     * Identifies customers who have placed more than 5 orders.
     * Groups orders by customer and counts the number of orders per customer,
     * then filters for customers with order count greater than 5.
     *
     * @param orders the list of orders to analyze
     * @return a list of customers who have more than 5 orders
     */
    public List<Customer> getCustomersWithMoreThan5Orders(List<Order> orders) {
        Map<Customer, Long> customerOrderCount = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()));

        return customerOrderCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}