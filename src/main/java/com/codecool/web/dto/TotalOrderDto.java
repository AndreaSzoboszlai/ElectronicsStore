package com.codecool.web.dto;

import java.util.List;

public class TotalOrderDto {
    private final List<ProductsInOrderDto> productsInOrder;
    private final double totalOrderCost;
    private final boolean status;

    public TotalOrderDto(List<ProductsInOrderDto> productsInOrder, double totalOrderCost, boolean status) {
        this.productsInOrder = productsInOrder;
        this.totalOrderCost = totalOrderCost;
        this.status = status;
    }

    public List<ProductsInOrderDto> getProductsInOrder() {
        return productsInOrder;
    }

    public double getTotalOrderCost() {
        return totalOrderCost;
    }

    public boolean isStatus() {
        return status;
    }
}
