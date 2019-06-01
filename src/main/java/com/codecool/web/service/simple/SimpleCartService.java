package com.codecool.web.service.simple;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dto.ProductsInCartDto;
import com.codecool.web.service.CartService;

import java.sql.SQLException;
import java.util.List;

public class SimpleCartService implements CartService {

    private final CartDao cartDao;

    public SimpleCartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<ProductsInCartDto> findCartByUser(int id) throws SQLException {
        return cartDao.findCartByUser(id);
    }
}
