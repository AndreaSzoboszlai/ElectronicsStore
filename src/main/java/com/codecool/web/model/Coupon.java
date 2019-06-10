package com.codecool.web.model;

import java.util.Objects;

public final class Coupon extends AbstractModel {

    private final String name;
    private final int percentage;
    private final String code;

    public Coupon(int id, String name, int percentage, String code) {
        super(id);
        this.name = name;
        this.percentage = percentage;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Coupon coupon = (Coupon) o;
        return percentage == coupon.percentage &&
            Objects.equals(name, coupon.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, percentage);
    }
}
