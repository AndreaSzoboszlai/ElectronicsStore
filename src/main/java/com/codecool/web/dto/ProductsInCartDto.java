package com.codecool.web.dto;

public class ProductsInCartDto {
    private int productId;
    private String name;
    private int quantity;
    private int price;
    private int totalPrice;

    public ProductsInCartDto(int productId, String name, int price, int quantity, int totalPrice) {
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
