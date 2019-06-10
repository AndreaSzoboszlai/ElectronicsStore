package com.codecool.web.servlet;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.CouponDao;
import com.codecool.web.dao.database.DatabaseCartDao;
import com.codecool.web.dao.database.DatabaseCouponDao;
import com.codecool.web.model.User;
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

@WebServlet("/protected/coupon")
public final class CouponServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            CartDao cartDao = new DatabaseCartDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao, cartDao);

            String code = request.getParameter("coupon-code");
            User user = (User) request.getSession().getAttribute("user");
            couponService.addCouponToCart(code, user.getId());
            sendMessage(response, HttpServletResponse.SC_OK, "Coupon added to purchase.");
        } catch (SQLException | ServiceException ex) {
            sendMessage(response, HttpServletResponse.SC_BAD_REQUEST, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            CartDao cartDao = new DatabaseCartDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao, cartDao);
            int id = Integer.valueOf(request.getParameter("coupon-id"));
            couponService.deleteCouponById(id);
            sendMessage(response, HttpServletResponse.SC_OK, "Coupon successfully deleted");
        } catch (SQLException ex) {
            sendMessage(response, HttpServletResponse.SC_BAD_REQUEST, ex);
        }
    }
}
