package com.codecool.web.servlet;

import com.codecool.web.dao.CouponDao;
import com.codecool.web.dao.database.DatabaseCouponDao;
import com.codecool.web.model.Coupon;
import com.codecool.web.service.CouponService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleCouponService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/coupons")
public final class CouponsServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao);
            List<Coupon> coupons = couponService.findAll();
            sendMessage(resp, HttpServletResponse.SC_OK, coupons);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao);

            String name = req.getParameter("couponName");
            int percentage = Integer.valueOf(req.getParameter("couponPercentage"));
            Coupon coupon = couponService.add(name, percentage);

            sendMessage(resp, HttpServletResponse.SC_OK, "Coupon sucesfully added");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
