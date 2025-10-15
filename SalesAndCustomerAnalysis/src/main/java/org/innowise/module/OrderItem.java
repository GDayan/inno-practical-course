package org.innowise.module;

/**
 * Represents an item within an order.
 * Contains product details including name, quantity, price, and category.
 */
public class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;

    /**
     * Constructs a new OrderItem with specified parameters.
     *
     * @param productName the name of the product
     * @param quantity the quantity of the product in the order
     * @param price the price per unit of the product
     * @param category the category of the product
     */
    public OrderItem(String productName, int quantity, double price, Category category) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    /**
     * Returns the product name.
     *
     * @return the name of the product
     */
    public String getProductName() { return productName; }

    /**
     * Returns the quantity of the product in the order.
     *
     * @return the quantity
     */
    public int getQuantity() { return quantity; }

    /**
     * Returns the price per unit of the product.
     *
     * @return the price per unit
     */
    public double getPrice() { return price; }

    /**
     * Returns the category of the product.
     *
     * @return the product category
     */
    public Category getCategory() { return category; }
}