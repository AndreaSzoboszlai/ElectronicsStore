package com.codecool.web.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cart extends AbstractModel{
    private int totalPrice;
    private int cartDiscount;
    private Map<Product, Integer> productsInCart;

    public Cart(int id, int totalPrice, int cartDiscount) {
        super(id);
        this.productsInCart = new HashMap<>();
        this.totalPrice = totalPrice;
        this.cartDiscount = cartDiscount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getCartDiscount() {
        return cartDiscount;
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
