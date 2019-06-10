package com.codecool.web.service;

import com.codecool.web.model.Coupon;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface CouponService {

    List<Coupon> findAll() throws SQLException;

    Coupon findById(int id) throws SQLException;

    Coupon findByCode(String code) throws SQLException;

    Coupon add(String name, int percentage, String code) throws SQLException;

    boolean doesCouponExists(String name) throws SQLException;

    void deleteCouponById(int id) throws SQLException;

    void addCouponToCart(String code, int userId) throws SQLException, ServiceException;
}
