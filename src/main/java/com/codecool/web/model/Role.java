package com.codecool.web.model;

public enum Role {
    EMPLOYEE("employee"),
    CUSTOMER("customer"),
    ADMIN("admin");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
