package com.codecool.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class User extends AbstractModel {

    private String name;
    private String email;
    private String password;
    private Role userRole;
    private int numberOfPurchases;
    private List<Order> orders;
    private Cart cart;

    public User(int id, String name, String email, String password, Role userRole, int numberOfPurchases) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.numberOfPurchases = numberOfPurchases;
        this.orders = new ArrayList<>();
    }


    public User(int id, String name, String email, String password, Role userRole) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.numberOfPurchases = 0;
    }

    public User() {

    }

    public void addCart(Cart cart) {
        this.cart = cart;
    }

    public void emptyCart() {
        this.cart = null;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getUserRole() {
        return userRole;
    }

    public int getNumberOfPurchases() {
        return numberOfPurchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return numberOfPurchases == user.numberOfPurchases &&
            Objects.equals(name, user.name) &&
            Objects.equals(email, user.email) &&
            Objects.equals(password, user.password) &&
            userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, email, password, userRole, numberOfPurchases);
    }
}
