package com.codecool.web.dto;

public class ProductsInOrderDto {
    private final int productId;
    private final String name;
    private final int quantity;
    private final int price;
    private final int totalPrice;

    public ProductsInOrderDto(int productId, String name, int price, int quantity, int totalPrice) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
