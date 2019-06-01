package com.codecool.web.dao;

import com.codecool.web.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    List<Order> findAll() throws SQLException;

    List<Order> findOrdersByUser(int id) throws SQLException;

    Order findOrderById(int id) throws SQLException;
}
