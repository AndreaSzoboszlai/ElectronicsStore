package com.codecool.web.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order extends AbstractModel{
    private int id;
    private boolean order_status;
    private int totalPrice;
    private Map<Product, Integer> orderedProducts;

    public Order(int id, boolean order_status, int totalPrice) {
        this.orderedProducts = new HashMap<>();
        this.id = id;
        this.order_status = order_status;
        this.totalPrice = totalPrice;
    }

    public void addProduct(Product product, int quantity) {
        orderedProducts.put(product, quantity);
    }

    public boolean isOrder_status() {
        return order_status;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return order_status == order.order_status &&
            totalPrice == order.totalPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_status, totalPrice);
    }
}
