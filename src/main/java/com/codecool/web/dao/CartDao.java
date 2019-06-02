package com.codecool.web.dao;

import com.codecool.web.dto.ProductsInCartDto;
import com.codecool.web.model.Cart;

import java.sql.SQLException;
import java.util.List;

public interface CartDao {

    List<ProductsInCartDto> findCartByUser(int id) throws SQLException;

    Cart addCart(int userId) throws SQLException;

    void addCartProductRelation(int cartId, int productId) throws SQLException;

    boolean doUserAlreadyHaveCart(int userId) throws SQLException;

    Cart findCartByUserId(int userId) throws SQLException;

    ProductsInCartDto getCartSingleDto(int id) throws SQLException;

    void updateProductCount(int quantity, int prodId, int cartId) throws SQLException;

    boolean doesProductInCartUserRelationExists(int userId, int prodId) throws SQLException;
}
