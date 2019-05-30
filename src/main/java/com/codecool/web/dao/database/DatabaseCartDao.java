package com.codecool.web.dao.database;

import com.codecool.web.model.Cart;

import java.sql.Connection;

public final class DatabaseCartDao extends AbstractDao {

    public DatabaseCartDao(Connection connection) {
        super(connection);
    }

    public Cart findCartByUser() {
        return null;
    }
}
