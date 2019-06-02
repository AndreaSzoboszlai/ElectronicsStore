package com.codecool.web.service.simple;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dto.ProductsInCartDto;
import com.codecool.web.model.Cart;
import com.codecool.web.service.CartService;

import java.sql.SQLException;
import java.util.List;

public class SimpleCartService implements CartService {

    private final CartDao cartDao;

    public SimpleCartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public List<ProductsInCartDto> findCartByUser(int id) throws SQLException {
        return cartDao.findCartByUser(id);
    }

    @Override
    public Cart addCart(int userId,int productId) throws SQLException {
        if (!cartDao.doUserAlreadyHaveCart(userId)) {
            Cart cart = cartDao.addCart(userId);
            cartDao.addCartProductRelation(cart.getId(), cart.getUserId());
            return cart;
        } else {
            Cart cart = cartDao.findCartByUserId(userId);
            cartDao.addCartProductRelation(cart.getId(), productId);
        }
        return null;
    }
}
