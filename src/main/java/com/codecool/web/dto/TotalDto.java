package com.codecool.web.dto;

import java.util.List;

public final class TotalDto {
    private final List<ProductsInCartDto> productsInCart;
    private final int totalCartCost;

    public TotalDto(List<ProductsInCartDto> productsInCart, int totalCartCost) {
        this.productsInCart = productsInCart;
        this.totalCartCost = totalCartCost;
    }

    public List<ProductsInCartDto> getProductsInCart() {
        return productsInCart;
    }

    public int getTotalCartCost() {
        return totalCartCost;
    }
}
