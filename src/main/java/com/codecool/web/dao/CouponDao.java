package com.codecool.web.dao;

import com.codecool.web.model.Coupon;

import java.sql.SQLException;
import java.util.List;

public interface CouponDao {

    List<Coupon> findAll() throws SQLException;

    Coupon findById(int id) throws SQLException;

    Coupon findByCode(String code) throws SQLException;

    Coupon add(String name, int percentage , String code) throws SQLException;

    boolean doesCouponExists(String name) throws SQLException;

    void deleteCouponById(int id) throws SQLException;
}
