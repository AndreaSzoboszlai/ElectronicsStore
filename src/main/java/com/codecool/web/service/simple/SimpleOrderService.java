package com.codecool.web.service.simple;

import com.codecool.web.dao.OrderDao;
import com.codecool.web.model.Order;
import com.codecool.web.service.OrderService;

import java.sql.SQLException;
import java.util.List;

public class SimpleOrderService implements OrderService {
    private final OrderDao orderDao;

    public SimpleOrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Order> findAll() throws SQLException {
        return orderDao.findAll();
    }

    public List<Order> findOrdersByUser(int id) throws SQLException {
        return orderDao.findOrdersByUser(id);
    }

    public  Order findOrderById(int id) throws SQLException {
        return orderDao.findOrderById(id);
    }
}
