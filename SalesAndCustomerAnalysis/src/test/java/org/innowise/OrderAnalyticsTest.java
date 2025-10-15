package org.innowise;

import org.innowise.module.*;
import org.innowise.service.OrderAnalytics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderAnalyticsTest {
    private OrderAnalytics analyzer;
    private List<Order> testOrders;
    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        analyzer = new OrderAnalytics();

        customer1 = new Customer("C1", "John Doe", "john@test.com",
                LocalDateTime.now().minusDays(100), 30, "Moscow");
        customer2 = new Customer("C2", "Jane Smith", "jane@test.com",
                LocalDateTime.now().minusDays(50), 25, "Saint Petersburg");

        OrderItem laptop = new OrderItem("Laptop", 1, 1000.0, Category.ELECTRONICS);
        OrderItem phone = new OrderItem("Phone", 2, 500.0, Category.ELECTRONICS);
        OrderItem book = new OrderItem("Book", 5, 20.0, Category.BOOKS);
        OrderItem tshirt = new OrderItem("T-Shirt", 3, 25.0, Category.CLOTHING);

        testOrders = Arrays.asList(
                new Order("O1", LocalDateTime.now(), customer1,
                        Arrays.asList(laptop, book), OrderStatus.DELIVERED),
                new Order("O2", LocalDateTime.now(), customer2,
                        Arrays.asList(phone, tshirt), OrderStatus.DELIVERED),
                new Order("O3", LocalDateTime.now(), customer1,
                        Arrays.asList(book, tshirt), OrderStatus.PROCESSING),
                new Order("O4", LocalDateTime.now(), customer1,
                        Arrays.asList(phone), OrderStatus.CANCELLED),
                new Order("O5", LocalDateTime.now(), customer1,
                        Arrays.asList(laptop), OrderStatus.DELIVERED),
                new Order("O6", LocalDateTime.now(), customer1,
                        Arrays.asList(book), OrderStatus.DELIVERED),
                new Order("O7", LocalDateTime.now(), customer1,
                        Arrays.asList(tshirt), OrderStatus.DELIVERED)
        );
    }

    @Test
    void testGetUniqueCities() {
        Set<String> cities = analyzer.getUniqueCities(testOrders);

        assertEquals(2, cities.size());
        assertTrue(cities.contains("Moscow"));
        assertTrue(cities.contains("Saint Petersburg"));
    }

    @Test
    void testGetTotalIncomeFromCompletedOrders() {
        double totalIncome = analyzer.getTotalIncomeFromCompletedOrders(testOrders);

        assertEquals(3350.0, totalIncome, 0.001);
    }

    @Test
    void testGetMostPopularProduct() {
        String mostPopular = analyzer.getMostPopularProduct(testOrders);

        assertEquals("Book", mostPopular);
    }

    @Test
    void testGetAverageCheckForDeliveredOrders() {
        double averageCheck = analyzer.getAverageCheckForDeliveredOrders(testOrders);

        assertEquals(670.0, averageCheck, 0.001);
    }

    @Test
    void testGetCustomersWithMoreThan5Orders() {
        List<Customer> customers = analyzer.getCustomersWithMoreThan5Orders(testOrders);

        assertEquals(1, customers.size());
        assertEquals("C1", customers.get(0).getCustomerId());
    }
}
