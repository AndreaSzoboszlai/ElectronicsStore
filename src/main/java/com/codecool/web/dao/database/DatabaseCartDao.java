package com.codecool.web.dao.database;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dto.ProductsInCartDto;
import com.codecool.web.model.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private ProductsInCartDto fetchProducts(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("product_id");
        String name = resultSet.getString("product_name");
        int price = resultSet.getInt("product_price");
        int quantity = resultSet.getInt("quantity_ordered");
        int totalPrice = resultSet.getInt("total_price");
        return new ProductsInCartDto(productId, name, price, quantity, totalPrice);
    }
}
