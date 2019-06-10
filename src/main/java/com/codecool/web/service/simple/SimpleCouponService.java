package com.codecool.web.service.simple;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.CouponDao;
import com.codecool.web.model.Cart;
import com.codecool.web.model.Coupon;
import com.codecool.web.service.CouponService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleCouponService implements CouponService {

    private final CouponDao couponDao;
    private final CartDao cartDao;

    public SimpleCouponService(CouponDao couponDao, CartDao cartDao) {
        this.couponDao = couponDao;
        this.cartDao = cartDao;
    }

    public List<Coupon> findAll() throws SQLException {
        return couponDao.findAll();
    }

    public Coupon findById(int id) throws SQLException {
        return couponDao.findById(id);
    }

    public Coupon findByCode(String code) throws SQLException {
        return couponDao.findByCode(code);
    }

    public Coupon add(String name, int percentage, String code) throws SQLException {
        return couponDao.add(name, percentage, code);
    }

    public boolean doesCouponExists(String name) throws SQLException {
        return couponDao.doesCouponExists(name);
    }

    public void deleteCouponById(int id) throws SQLException {
        couponDao.deleteCouponById(id);
    }

    public void addCouponToCart(String code, int userId) throws SQLException, ServiceException {
        if (couponDao.doesCouponExists(code)) {
            Cart cart = cartDao.findCartByUserId(userId);
            if (cart.getCartDiscount() == 0) {
                Coupon coupon = findByCode(code);
                double num = (float)coupon.getPercentage();
                double obtained = 100.0 - num;
                double end = obtained / 100.0;
                double discount = (double)(cart.getTotalPrice())* end;
                cartDao.updateDiscountInCart(cart.getId(), discount, coupon.getPercentage());
            } else {
                throw new ServiceException("Coupon already added to purchase");
            }
        } else {
            throw new ServiceException("Coupon doesn't exists");
        }
    }
}
