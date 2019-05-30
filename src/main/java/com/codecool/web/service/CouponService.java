package com.codecool.web.service;

import com.codecool.web.model.Coupon;

import java.sql.SQLException;
import java.util.List;

public interface CouponService {

    List<Coupon> findAll() throws SQLException;

    Coupon findById(int id) throws SQLException;

    Coupon findByName(String name) throws SQLException;

    Coupon add(String name, int percentage) throws SQLException;

    boolean doesCouponExists(String name) throws SQLException;

    void deleteCouponById(int id) throws SQLException;
}
