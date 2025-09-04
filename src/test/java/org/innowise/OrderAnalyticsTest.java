package org.innowise;

import org.innowise.model.*;
import org.innowise.service.OrderAnalytics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderAnalyticsTest {
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private List<Order> orders;

    @BeforeEach
    void setUp(){
        customer1 = new Customer("1", "Dayan", "dayan@gmail.com", LocalDateTime.now(), 24, "Minsk");
        customer2 = new Customer("2", "Lera", "lera@gmail.com", LocalDateTime.now(), 20, "Minsk");
        customer3 = new Customer("3", "Alex", "alex@gmail.com", LocalDateTime.now(), 22, "Brest");

        OrderItem orderItem1 = new OrderItem("Smartphone", 1, 500.0, Category.ELECTRONICS);
        OrderItem orderItem2 = new OrderItem("Book", 2, 10.0, Category.BOOKS);
        OrderItem orderItem3 = new OrderItem("T-shirt", 6, 15.0, Category.CLOTHING);

        orders = List.of(
                new Order("1", LocalDateTime.now(), customer1, List.of(orderItem1), OrderStatus.DELIVERED),
                new Order("2", LocalDateTime.now(), customer2, List.of(orderItem2), OrderStatus.NEW),
                new Order("3", LocalDateTime.now(), customer3, List.of(orderItem3), OrderStatus.PROCESSING)
        );

    }

    @Test
    void testUniqueCities(){
        assertEquals(Set.of("Minsk", "Brest"), OrderAnalytics.getUniqueCities(orders));
    }

    @Test
    void testTotalIncome(){
        assertEquals(500.0, OrderAnalytics.getTotalIncome(orders));
    }

    @Test
    void testMostPopularProduct(){
        Optional<String> product = OrderAnalytics.getMostPopularProduct(orders);
        assertTrue(product.isPresent());
        assertEquals("T-shirt", product.get());
    }

    @Test
    void testAverageCheck(){
        assertEquals(500.0, OrderAnalytics.getAverageCheck(orders));
    }

    @Test
    void testCustomersWithMoreThanFiveOrders(){
        List<Customer> customers = OrderAnalytics.getCustomersWithMoreThanFiveOrders(orders);
        assertTrue(customers.isEmpty());
    }
}
