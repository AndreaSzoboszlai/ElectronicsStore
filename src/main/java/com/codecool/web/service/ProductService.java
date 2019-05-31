package com.codecool.web.service;

import com.codecool.web.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    List<Product> findAll() throws SQLException;

    Product findById(int id) throws SQLException;

    Product addProduct(String name, int price, String description, int productInStock) throws SQLException;
}