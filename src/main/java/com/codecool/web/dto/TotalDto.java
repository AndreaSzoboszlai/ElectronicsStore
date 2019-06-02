package com.codecool.web.dto;

import java.util.List;

public class TotalDto {
    private List<ProductsInCartDto> productsInCart;
    private int total;

    public TotalDto(List<ProductsInCartDto> productsInCart) {
        this.productsInCart = productsInCart;
        this.total = 0;
    }

    public void countTotal() {
        for (ProductsInCartDto products : productsInCart) {
            total += products.getPrice() * products.getQuantity();
        }
    }

    public int getTotal() {
        countTotal();
        return total;
    }
}
