package com.codecool.web.servlet;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.ProductDao;
import com.codecool.web.dao.database.DatabaseCartDao;
import com.codecool.web.dao.database.DatabaseProductDao;
import com.codecool.web.dto.ProductsInCartDto;
import com.codecool.web.dto.TotalDto;
import com.codecool.web.model.User;
import com.codecool.web.service.CartService;
import com.codecool.web.service.ProductService;
import com.codecool.web.service.simple.SimpleCartService;
import com.codecool.web.service.simple.SimpleProductService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/cart")
public class CartServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            ProductDao productDao = new DatabaseProductDao(connection);
            ProductService productService = new SimpleProductService(productDao);
            CartDao cartDao = new DatabaseCartDao(connection);
            CartService cartService = new SimpleCartService(cartDao, productDao);
            User user = (User) request.getSession().getAttribute("user");

            TotalDto totalDto = cartService.getTotalDto(user.getId());
            sendMessage(response, HttpServletResponse.SC_OK, totalDto);
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            ProductDao productDao = new DatabaseProductDao(connection);
            ProductService productService = new SimpleProductService(productDao);
            CartDao cartDao = new DatabaseCartDao(connection);
            CartService cartService = new SimpleCartService(cartDao, productDao);
            User user = (User) request.getSession().getAttribute("user");
            int id = Integer.valueOf(request.getParameter("product-id"));
            cartService.addCart(user.getId(), id);
            sendMessage(response, HttpServletResponse.SC_OK, "Product added to cart");
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            ProductDao productDao = new DatabaseProductDao(connection);
            ProductService productService = new SimpleProductService(productDao);
            CartDao cartDao = new DatabaseCartDao(connection);
            CartService cartService = new SimpleCartService(cartDao, productDao);
            User user = (User) request.getSession().getAttribute("user");
            int prodId = Integer.valueOf(request.getParameter("del-id"));
            cartService.deleteProductFromCart(user.getId(), prodId);
            sendMessage(response, HttpServletResponse.SC_OK, "Product deleted from cart");
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }
}
