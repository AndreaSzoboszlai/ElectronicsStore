package com.codecool.web.servlet;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.OrderDao;
import com.codecool.web.dao.database.DatabaseCartDao;
import com.codecool.web.dao.database.DatabaseOrderDao;
import com.codecool.web.dto.TotalDto;
import com.codecool.web.model.User;
import com.codecool.web.service.OrderService;
import com.codecool.web.service.simple.SimpleOrderService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/submitorder")
public class SubmitOrderServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            OrderDao orderDao = new DatabaseOrderDao(connection);
            CartDao cartDao = new DatabaseCartDao(connection);
            OrderService orderService = new SimpleOrderService(orderDao, cartDao);
            User user = (User) request.getSession().getAttribute("user");
            orderService.placeOrder(user.getId());

            sendMessage(response, HttpServletResponse.SC_OK, "Order succesfully added, and can't be changed now.");
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }
}
