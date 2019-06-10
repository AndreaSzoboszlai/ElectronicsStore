package com.codecool.web.dao.database;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.dto.ProductsInOrderDto;
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
    public Order addOrder(double orderedTotal, int userId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO orders(ordered_total_price, user_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, orderedTotal);
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

    @Override
    public List<ProductsInOrderDto> findOrderByUser(int orderId) throws SQLException {
        List<ProductsInOrderDto> productsInCart = new ArrayList<>();
        String sql = "SELECT products.product_id, product_name, product_per_total, product_price, quantity_ordered, ordered_total_price, order_status FROM products JOIN (SELECT product_per_total, orders.user_id, quantity_ordered, ordered_total_price, product_id, order_status FROM orders JOIN orders_products ON orders.order_id = orders_products.order_id WHERE orders.order_id = ?) AS temp_table ON temp_table.product_id = products.product_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    productsInCart.add(fetchProducts(resultSet));
                }
            }
        }
        return productsInCart;
    }

    @Override
    public boolean findOrderStatusById(int orderId) throws SQLException {
        String sql = "SELECT order_status FROM products JOIN (SELECT product_id, order_status FROM orders JOIN orders_products ON orders.order_id = orders_products.order_id WHERE orders.order_id = ?) AS temp_table ON temp_table.product_id = products.product_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("order_status");
                }
            }
        }
        return false;
    }

    @Override
    public void orderStatusToShipped(int orderId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    private Order fetchOrder(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("order_id");
        boolean orderStatus = resultSet.getBoolean("order_status");
        double totalPrice = resultSet.getDouble("ordered_total_price");
        int userId = resultSet.getInt("user_id");
        return new Order(id, orderStatus, totalPrice, userId);
    }

    private ProductsInOrderDto fetchProducts(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("product_id");
        String name = resultSet.getString("product_name");
        int price = resultSet.getInt("product_price");
        int quantity = resultSet.getInt("quantity_ordered");
        int totalProdPrice = resultSet.getInt("product_per_total");
        return new ProductsInOrderDto(productId, name, price, quantity, totalProdPrice);
    }
}
