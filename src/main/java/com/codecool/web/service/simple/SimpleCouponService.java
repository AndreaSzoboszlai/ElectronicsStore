package com.codecool.web.service.simple;

import com.codecool.web.dao.CouponDao;
import com.codecool.web.model.Coupon;
import com.codecool.web.service.CouponService;

import java.sql.SQLException;
import java.util.List;

public final class SimpleCouponService implements CouponService {

    private final CouponDao couponDao;

    public SimpleCouponService(CouponDao couponDao) {
        this.couponDao = couponDao;
    }

    public List<Coupon> findAll() throws SQLException {
        return couponDao.findAll();
    }

    public Coupon findById(int id) throws SQLException {
        return couponDao.findById(id);
    }

    public Coupon findByName(String name) throws SQLException{
        return couponDao.findByName(name);
    }

    public Coupon add(String name, int percentage) throws SQLException {
        return couponDao.add(name, percentage);
    }

    public boolean doesCouponExists(String name) throws SQLException {
        return couponDao.doesCouponExists(name);
    }

    public void deleteCouponById(int id) throws SQLException {
        couponDao.deleteCouponById(id);
    }
}
