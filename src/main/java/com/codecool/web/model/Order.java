package com.codecool.web.model;

import java.util.Objects;

public class Order extends AbstractModel{
    private final boolean orderStatus;
    private final int totalPrice;
    private final int userId;

    public Order(int id, boolean orderStatus, int totalPrice, int userId) {
        super(id);
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public int getTotalPrice() {
        return totalPrice;
    }


    public int getUserId() {
        return userId;
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
