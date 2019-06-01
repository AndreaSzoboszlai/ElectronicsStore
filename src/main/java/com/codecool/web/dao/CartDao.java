package com.codecool.web.dao;

import com.codecool.web.dto.ProductsInCartDto;

import java.sql.SQLException;
import java.util.List;

public interface CartDao {

    List<ProductsInCartDto> findCartByUser(int id) throws SQLException;
}
