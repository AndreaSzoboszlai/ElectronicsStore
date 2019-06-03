package com.codecool.web.dto;

import java.util.ArrayList;
import java.util.List;

public final class TotalDto {
    private List<ProductsInCartDto> productsInCart;
    private int total;

    public TotalDto(List<ProductsInCartDto> productsInCart) {
        this.productsInCart = productsInCart;
    }

    public void countTotal() {
        this.total = 0;
        for (ProductsInCartDto products : productsInCart) {
            total = total + products.getPrice() * products.getQuantity();
        }
    }

    public List<ProductsInCartDto> getProductsInCart() {
        return productsInCart;
    }

    public int getTotal() {
        countTotal();
        return total;
    }
}
