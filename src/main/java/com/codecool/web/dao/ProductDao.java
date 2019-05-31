package com.codecool.web.dao;

import com.codecool.web.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> findAll() throws SQLException;

    Product findById(int id) throws SQLException;

    Product addProduct(String name, int price, String description, int productInStock) throws SQLException;
}
