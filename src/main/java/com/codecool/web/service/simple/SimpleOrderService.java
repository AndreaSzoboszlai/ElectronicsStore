package com.codecool.web.service.simple;

import com.codecool.web.dao.CartDao;
import com.codecool.web.dao.OrderDao;
import com.codecool.web.dto.ProductsInCartDto;
import com.codecool.web.dto.ProductsInOrderDto;
import com.codecool.web.dto.TotalDto;
import com.codecool.web.dto.TotalOrderDto;
import com.codecool.web.model.Order;
import com.codecool.web.service.OrderService;

import java.sql.SQLException;
import java.util.List;

public class SimpleOrderService implements OrderService {
    private final OrderDao orderDao;
    private final CartDao cartDao;

    public SimpleOrderService(OrderDao orderDao, CartDao cartDao) {
        this.orderDao = orderDao;
        this.cartDao = cartDao;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        return orderDao.findAll();
    }

    @Override
    public List<Order> findOrdersByUser(int id) throws SQLException {
        return orderDao.findOrdersByUser(id);
    }

    @Override
    public  Order findOrderById(int id) throws SQLException {
        return orderDao.findOrderById(id);
    }

    @Override
    public void placeOrder(int userId) throws SQLException {
        List<ProductsInCartDto> products = cartDao.findCartByUser(userId);
        TotalDto totalDto = new TotalDto(products, cartDao.getTotalCartCost(userId), cartDao.findCartByCartId(userId).getCartDiscount());
        Order order = orderDao.addOrder(totalDto.getTotalCartCost(), userId);
        for (ProductsInCartDto orderedProducts : products) {
            orderDao.addOrderProductRelation(order.getId(), orderedProducts.getQuantity(), orderedProducts.getTotalPrice(), orderedProducts.getProductId());
        }
        cartDao.deleteCartByUser(userId);
    }

    @Override
    public TotalOrderDto findOrderByUser(int orderId) throws SQLException {
        List<ProductsInOrderDto> productsInOrderDtos = orderDao.findOrderByUser(orderId);
        TotalOrderDto totalOrderDto = new TotalOrderDto(productsInOrderDtos, orderDao.findOrderById(orderId).getTotalPrice(), orderDao.findOrderStatusById(orderId));
        return  totalOrderDto;
    }

    @Override
    public void orderStatusToShipped(int orderId) throws SQLException {
        orderDao.orderStatusToShipped(orderId);
    }
}
