package com.codecool.web.model;

import java.util.Objects;

public class Product extends AbstractModel {
    private String name;
    private int price;
    private String description;
    private int productInStock;

    public Product(int id, String name, int price, String description, int productInStock) {
        super(id);
        this.name = name;
        this.price = price;
        this.description = description;
        this.productInStock = productInStock;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getProductInStock() {
        return productInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return price == product.price &&
            productInStock == product.productInStock &&
            Objects.equals(name, product.name) &&
            Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, price, description, productInStock);
    }
}
