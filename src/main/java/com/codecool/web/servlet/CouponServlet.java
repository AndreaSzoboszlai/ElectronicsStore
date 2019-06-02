package com.codecool.web.servlet;

import com.codecool.web.dao.CouponDao;
import com.codecool.web.dao.database.DatabaseCouponDao;
import com.codecool.web.dto.CouponDto;
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

@WebServlet("/protected/coupon")
public final class CouponServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao);
            //haven't used yet
            int id = Integer.valueOf(request.getParameter("coupon-id"));
            Coupon coupon = couponService.findById(id);
            sendMessage(response, HttpServletResponse.SC_OK, coupon);
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao);

            int id = Integer.valueOf(request.getParameter("coupon-id"));
            couponService.deleteCouponById(id);
            sendMessage(response, HttpServletResponse.SC_OK, "Coupon successfully deleted");
        } catch (SQLException ex) {
            sendMessage(response, HttpServletResponse.SC_BAD_REQUEST, ex);
        }
    }
}
