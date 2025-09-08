package org.innowise.module;

/**
 * Represents a single item in an order.
 */
public class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;

    /**
     * Constructs an OrderItem instance.
     *
     * @param productName name of the product
     * @param quantity    quantity of the product
     * @param price       price per unit
     * @param category    product category
     */
    public OrderItem(String productName, int quantity, double price, Category category) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }
}
