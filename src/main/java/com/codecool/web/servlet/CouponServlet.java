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

    // https://www.postgresql.org/docs/current/static/errcodes-appendix.html
    private static final String SQL_ERROR_CODE_UNIQUE_VIOLATION = "23505";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
/*            ShopDao shopDao = new DatabaseShopDao(connection);
            ShopService shopService = new SimpleShopService(shopDao);
            CouponService couponService = new SimpleCouponService(couponDao, shopDao);

            String id = req.getParameter("id");

            Coupon coupon = couponService.getCoupon(id);
            List<Shop> allShops = shopService.getShops();
            List<Shop> couponShops = couponService.getCouponShops(id);

            sendMessage(resp, HttpServletResponse.SC_OK, new CouponDto(coupon, couponShops, allShops));*/
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
/*            CouponDao couponDao = new DatabaseCouponDao(connection);
            ShopDao shopDao = new DatabaseShopDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao, shopDao);

            String couponId = req.getParameter("id");
            String[] shopIds = req.getParameterValues("shopIds");

            couponService.addCouponToShops(couponId, shopIds);*/

            doGet(req, resp);
        } catch (SQLException ex) {
            if (SQL_ERROR_CODE_UNIQUE_VIOLATION.equals(ex.getSQLState())) {
                sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "Coupon has been already added to one of the selected shops");
            } else {
                handleSqlError(resp, ex);
            }
        }
    }
}
