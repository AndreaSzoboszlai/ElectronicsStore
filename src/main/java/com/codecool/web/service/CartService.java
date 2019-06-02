package com.codecool.web.service;

import com.codecool.web.dto.ProductsInCartDto;
import com.codecool.web.dto.TotalDto;
import com.codecool.web.model.Cart;

import java.sql.SQLException;
import java.util.List;

public interface CartService {

    List<ProductsInCartDto> findCartByUser(int id) throws SQLException;

    Cart addCart(int userId, int productId) throws SQLException;

    TotalDto getTotalDto(int id) throws SQLException;
}
