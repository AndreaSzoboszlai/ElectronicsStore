package com.codecool.web.dao.database;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseOrderDao extends AbstractDao implements OrderDao {

    public DatabaseOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Order> findAll() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM coupons";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                orders.add(fetchOrder(resultSet));
            }
        }
        return orders;
    }

    @Override
    public List<Order> findOrdersByUser(int id) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(fetchOrder(resultSet));
                }
            }
        }
        return orders;
    }

    @Override
    public Order findOrderById(int id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchOrder(resultSet);
                }
            }
        }
        return null;
    }


    public Order fetchOrder(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("order_id");
        boolean orderStatus = resultSet.getBoolean("order_status");
        int totalPrice = resultSet.getInt("ordered_total_price");
        return new Order(id, orderStatus, totalPrice);
    }
}
