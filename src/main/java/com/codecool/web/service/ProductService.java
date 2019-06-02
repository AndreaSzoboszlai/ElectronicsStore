package com.codecool.web.service;

import com.codecool.web.model.Product;
import com.codecool.web.service.exception.ServiceException;
import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    List<Product> findAll() throws SQLException;

    Product findById(int id) throws SQLException;

    Product addProduct(String name, int price, String description, int productInStock) throws SQLException;

    void deleteProduct(int id) throws SQLException, ServiceException;

    void updateProductDetails(int id, String name, int price, String description, int stock) throws SQLException;
}
