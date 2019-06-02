package com.codecool.web.dao.database;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dto.ProductsInCartDto;
import com.codecool.web.model.Cart;
import com.codecool.web.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseCartDao extends AbstractDao implements CartDao {

    public DatabaseCartDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<ProductsInCartDto> findCartByUser(int id) throws SQLException {
        List<ProductsInCartDto> productsInCart = new ArrayList<>();
        String sql = "SELECT products.product_id, product_name, product_price, quantity_ordered, total_price FROM products JOIN (SELECT carts.user_id, quantity_ordered, total_price, product_id FROM carts JOIN carts_products ON carts.cart_id = carts_products.cart_id WHERE carts.user_id = ?) AS temp_table ON temp_table.product_id = products.product_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    productsInCart.add(fetchProducts(resultSet));
                }
            }
        }
        return productsInCart;
    }

    public Cart findCartByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM carts WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int cartId = resultSet.getInt("cart_id");
                    int userOwnId = resultSet.getInt("user_id");
                    return new Cart(cartId, userOwnId);
                }
            }
        }
        return null;
    }

    @Override
    public Cart addCart(int userId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO carts (user_id) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, userId);
            executeInsert(preparedStatement);
            int id = fetchGeneratedId(preparedStatement);
            connection.commit();
            return new Cart(id, userId);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void addCartProductRelation(int cartId, int productId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO carts_products(cart_id, quantity_ordered, product_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2,1);
            preparedStatement.setInt(3, productId);
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
    public boolean doUserAlreadyHaveCart(int userId) throws SQLException {
        String sql = "SELECT * FROM carts WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    public ProductsInCartDto getCartSingleDto(int id) throws SQLException {
        String sql = "SELECT products.product_id, product_name, product_price, quantity_ordered, total_price FROM products JOIN (SELECT carts.user_id, quantity_ordered, total_price, product_id FROM carts JOIN carts_products ON carts.cart_id = carts_products.cart_id WHERE carts.user_id = ?) AS temp_table ON temp_table.product_id = products.product_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchProducts(resultSet);
                }
            }
        }
        return null;
    }

    public void updateProductCount(int quantity, int prodId, int cartId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE carts_products SET quantity_ordered = ? WHERE product_id = ? AND cart_id = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, prodId);
            preparedStatement.setInt(3, cartId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exc) {
            connection.rollback();
            throw exc;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public boolean doesProductInCartUserRelationExists(int userId, int prodId) throws SQLException {
        String sql = "SELECT quantity_ordered FROM products JOIN (SELECT carts.user_id, carts.cart_id, quantity_ordered, total_price, product_id FROM carts JOIN carts_products ON carts.cart_id = carts_products.cart_id WHERE carts.user_id = ? AND product_id = ?) AS temp_table ON temp_table.product_id = products.product_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, prodId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    private ProductsInCartDto fetchProducts(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("product_id");
        String name = resultSet.getString("product_name");
        int price = resultSet.getInt("product_price");
        int quantity = resultSet.getInt("quantity_ordered");
        return new ProductsInCartDto(productId, name, price, quantity);
    }
}
