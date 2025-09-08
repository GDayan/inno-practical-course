# Order Analytics

This project provides a simple analytics service for an online store.  
It calculates various business metrics from a list of orders using **Java Stream API**.

---

## Data Structure

### Order

```java
class Order {
    private String orderId;
    private LocalDateTime orderDate;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;
}
```

### OrderItem

```java
class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;
}
```

### Customer
```java
class Customer {
    private String customerId;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
    private int age;
    private String city;
}
```

### Enums
```java
enum OrderStatus {
    NEW, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}

enum Category {
    ELECTRONICS, CLOTHING, BOOKS, HOME, BEAUTY, TOYS
}
```

## Metrics

The project calculates the following **business metrics** from a list of orders:

### 1. Unique Cities
- **Description:** List of unique cities from which orders were placed.
- **Metric type:** Set of city names (`Set<String>`)

### 2. Total Income
- **Description:** Total income for all completed orders (`DELIVERED`).
- **Calculation:** Sum of `price * quantity` for all items in delivered orders.
- **Metric type:** `double`

### 3. Most Popular Product
- **Description:** The product with the highest number of items sold across all orders.
- **Calculation:** Count of sold quantities per product, return product with max count.
- **Metric type:** `Optional<String>`

### 4. Average Check
- **Description:** Average order value for successfully delivered orders.
- **Calculation:** Sum of `price * quantity` for each delivered order, divided by the number of delivered orders.
- **Metric type:** `double`

### 5. Frequent Customers
- **Description:** Customers who have placed more than 5 orders.
- **Calculation:** Count orders per customer and filter those with count > 5.
- **Metric type:** `List<Customer>`
