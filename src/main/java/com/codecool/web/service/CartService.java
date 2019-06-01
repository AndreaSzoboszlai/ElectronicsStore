package com.codecool.web.service;

import com.codecool.web.dto.ProductsInCartDto;

import java.sql.SQLException;
import java.util.List;

public interface CartService {

    List<ProductsInCartDto> findCartByUser(int id) throws SQLException;
}
