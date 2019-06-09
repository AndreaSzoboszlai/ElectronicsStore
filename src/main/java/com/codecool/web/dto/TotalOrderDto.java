package com.codecool.web.dto;

import java.util.List;

public class TotalOrderDto {
    private final List<ProductsInOrderDto> productsInOrder;
    private final int totalOrderCost;
    private final boolean status;

    public TotalOrderDto(List<ProductsInOrderDto> productsInOrder, int totalOrderCost, boolean status) {
        this.productsInOrder = productsInOrder;
        this.totalOrderCost = totalOrderCost;
        this.status = status;
    }

    public List<ProductsInOrderDto> getProductsInOrder() {
        return productsInOrder;
    }

    public int getTotalOrderCost() {
        return totalOrderCost;
    }

    public boolean isStatus() {
        return status;
    }
}
