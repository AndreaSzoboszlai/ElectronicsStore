package com.codecool.web.service.simple;

import com.codecool.web.dao.ProductDao;
import com.codecool.web.model.Product;
import com.codecool.web.service.ProductService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SimpleProductService implements ProductService {
    private final ProductDao productDao;


    public SimpleProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return productDao.findAll();
    }

    @Override
    public Product findById(int id) throws SQLException {
        return productDao.findById(id);
    }

    @Override
    public Product addProduct(String name, int price, String description, int productInStock) throws SQLException {
        return productDao.addProduct(name, price, description, productInStock);
    }

    @Override
    public void deleteProduct(int id) throws SQLException, ServiceException {
        if (productDao.doesProductExistInCart(id)) {
            throw new ServiceException("Product already is somene's cart you can't delete it");
        }

        if (productDao.doesProductExistInOrder(id)) {
            throw new ServiceException("Product is already in someone's order, so it can't be deleted");
        }
        productDao.deleteProduct(id);

    }
}
