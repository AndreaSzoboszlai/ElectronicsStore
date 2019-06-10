package com.codecool.web.dao;

import com.codecool.web.dto.ProductsInOrderDto;
import com.codecool.web.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    List<Order> findAll() throws SQLException;

    List<Order> findOrdersByUser(int id) throws SQLException;

    Order findOrderById(int id) throws SQLException;

    Order addOrder(double orderedTotal, int userId) throws SQLException;

    void addOrderProductRelation(int orderId, int quantity, int prodPerTotal, int prodId ) throws SQLException;

    List<ProductsInOrderDto> findOrderByUser(int orderId) throws SQLException;

    boolean findOrderStatusById(int orderId) throws SQLException;

    void orderStatusToShipped(int orderId) throws SQLException;
}
