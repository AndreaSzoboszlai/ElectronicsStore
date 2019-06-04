package com.codecool.web.service;

import com.codecool.web.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    List<Order> findAll() throws SQLException;

    List<Order> findOrdersByUser(int id) throws SQLException;

    Order findOrderById(int id) throws SQLException;

    void placeOrder(int userId) throws SQLException;
}
