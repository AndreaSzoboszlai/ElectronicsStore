package com.codecool.web.servlet;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.OrderDao;
import com.codecool.web.dao.database.DatabaseCartDao;
import com.codecool.web.dao.database.DatabaseOrderDao;
import com.codecool.web.dto.TotalOrderDto;
import com.codecool.web.model.Order;
import com.codecool.web.service.OrderService;
import com.codecool.web.service.simple.SimpleOrderService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/orders")
public class OrdersServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            OrderDao orderDao = new DatabaseOrderDao(connection);
            CartDao cartDao = new DatabaseCartDao(connection);
            OrderService orderService = new SimpleOrderService(orderDao, cartDao);
            List<Order> orders = orderService.findAll();

            sendMessage(response, HttpServletResponse.SC_OK, orders);
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            OrderDao orderDao = new DatabaseOrderDao(connection);
            CartDao cartDao = new DatabaseCartDao(connection);
            OrderService orderService = new SimpleOrderService(orderDao, cartDao);
            int id = Integer.valueOf(request.getParameter("orderId"));
            orderService.orderStatusToShipped(id);
            sendMessage(response, HttpServletResponse.SC_OK, "Order status changed to shipped");
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }
}
