package com.codecool.web.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cart extends AbstractModel{
    private int totalPrice;
    private int cartDiscount;
    private int userId;

    public Cart(int id, int totalPrice, int cartDiscount, int userId) {
        super(id);
        this.totalPrice = totalPrice;
        this.cartDiscount = cartDiscount;
        this.userId = userId;
    }

    public Cart(int id, int totalPrice, int cartDiscount) {
        super(id);
        this.totalPrice = totalPrice;
        this.cartDiscount = cartDiscount;
    }

    public Cart(int id, int userId) {
        super(id);
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getCartDiscount() {
        return cartDiscount;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        if (!super.equals(o)) return false;
        Cart cart = (Cart) o;
        return totalPrice == cart.totalPrice &&
            cartDiscount == cart.cartDiscount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), totalPrice, cartDiscount);
    }
}
