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
        String sql = "SELECT * FROM orders";
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

    @Override
    public Order addOrder(int orderedTotal, int userId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO orders(ordered_total_price, user_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, orderedTotal);
            preparedStatement.setInt(2,userId);
            executeInsert(preparedStatement);
            connection.commit();
            int id = fetchGeneratedId(preparedStatement);
            return new Order(id, false, orderedTotal, userId);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public  void addOrderProductRelation(int orderId, int quantity, int prodPerTotal, int prodId ) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO orders_products(order_id, quantity_ordered, product_per_total, product_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, prodPerTotal);
            preparedStatement.setInt(4, prodId);
            executeInsert(preparedStatement);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public Order fetchOrder(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("order_id");
        boolean orderStatus = resultSet.getBoolean("order_status");
        int totalPrice = resultSet.getInt("ordered_total_price");
        int userId = resultSet.getInt("user_id");
        return new Order(id, orderStatus, totalPrice, userId);
    }
}
