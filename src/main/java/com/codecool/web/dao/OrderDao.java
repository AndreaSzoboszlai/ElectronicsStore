package com.codecool.web.dao;

import com.codecool.web.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    List<Order> findAll() throws SQLException;

    List<Order> findOrdersByUser(int id) throws SQLException;

    Order findOrderById(int id) throws SQLException;

    Order addOrder(int orderedTotal, int userId) throws SQLException;

    void addOrderProductRelation(int orderId, int quantity, int prodPerTotal, int prodId ) throws SQLException;
}
