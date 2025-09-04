# Order Analytics

This project provides a utility class for analyzing a list of orders received by an online store. It uses **Java 17** (or higher) and **Stream API** to calculate various business metrics. All metrics are covered with **JUnit 5** unit tests.

## Features / Metrics

The `OrderAnalytics` class provides the following metrics:

1. **Unique cities** – returns a set of unique cities where orders were placed.
2. **Total income** – calculates total income from all delivered orders.
3. **Most popular product** – finds the product with the highest total quantity sold.
4. **Average check** – calculates the average order value for delivered orders.
5. **Active customers** – lists customers who have placed more than 5 orders.
