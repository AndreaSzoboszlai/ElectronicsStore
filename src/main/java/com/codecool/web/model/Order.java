package com.codecool.web.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order extends AbstractModel{
    private int id;
    private boolean orderStatus;
    private int totalPrice;
    private Map<Product, Integer> orderedProducts;

    public Order(int id, boolean orderStatus, int totalPrice) {
        super(id);
        this.orderedProducts = new HashMap<>();
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }

    public void addProduct(Product product, int quantity) {
        orderedProducts.put(product, quantity);
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderStatus == order.orderStatus &&
            totalPrice == order.totalPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStatus, totalPrice);
    }
}
