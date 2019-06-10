package com.codecool.web.dto;

import java.util.List;

public final class TotalDto {
    private final List<ProductsInCartDto> productsInCart;
    private final int totalCartCost;
    private final int percentage;

    public TotalDto(List<ProductsInCartDto> productsInCart, int totalCartCost, int percentage) {
        this.productsInCart = productsInCart;
        this.totalCartCost = totalCartCost;
        this.percentage = percentage;
    }

    public List<ProductsInCartDto> getProductsInCart() {
        return productsInCart;
    }

    public int getTotalCartCost() {
        return totalCartCost;
    }

    public int getPercentage() {
        return percentage;
    }
}
