package com.codecool.web.service.simple;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.ProductDao;
import com.codecool.web.dto.ProductsInCartDto;
import com.codecool.web.dto.TotalDto;
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
            if (cartDao.doesProductInCartUserRelationExists(userId, productId)) {
                int newCount = cartDao.getCartSingleDto(userId).getQuantity() + 1;
                cartDao.updateProductCount(newCount, productId, cart.getId());

            } else {
                cartDao.addCartProductRelation(cart.getId(), productId);
            }
            return cart;
        }
    }

    public int countTotalByCart(int id) throws SQLException {
        TotalDto totalDto = new TotalDto(cartDao.findCartByUser(id));
        return totalDto.getTotal();
    }
}
