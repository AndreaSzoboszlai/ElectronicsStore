package com.codecool.web.servlet;

import com.codecool.web.dao.ProductDao;
import com.codecool.web.dao.database.DatabaseProductDao;
import com.codecool.web.model.Product;
import com.codecool.web.service.ProductService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/product")
public class ProductServlet extends AbstractServlet {
    private final ObjectMapper om = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            ProductDao productDao = new DatabaseProductDao(connection);
            ProductService productService = new SimpleProductService(productDao);
            int id = Integer.valueOf(request.getParameter("productId"));
            Product product = productService.findById(id);
            sendMessage(response, HttpServletResponse.SC_OK, product);
        } catch (SQLException ex) {
            sendMessage(response, HttpServletResponse.SC_BAD_REQUEST, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            ProductDao productDao = new DatabaseProductDao(connection);
            ProductService productService = new SimpleProductService(productDao);

            String name = request.getParameter("product-name");
            int price = Integer.valueOf(request.getParameter("product-price"));
            String description = request.getParameter("product-description");
            int stock = Integer.valueOf(request.getParameter("product-stock"));

            productService.addProduct(name, price, description, stock);
            sendMessage(response, HttpServletResponse.SC_OK, "Product added");
        } catch (SQLException ex) {
            sendMessage(response, HttpServletResponse.SC_BAD_REQUEST, ex);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            ProductDao productDao = new DatabaseProductDao(connection);
            ProductService productService = new SimpleProductService(productDao);

            int id = Integer.valueOf(request.getParameter("del-id"));
            productService.deleteProduct(id);
            sendMessage(response, HttpServletResponse.SC_OK, "Product deleted");
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        } catch (ServiceException ex) {
            sendMessage(response, HttpServletResponse.SC_OK, ex);
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            ProductDao productDao = new DatabaseProductDao(connection);
            ProductService productService = new SimpleProductService(productDao);
            Product product = om.readValue(request.getInputStream(), Product.class);

            productService.updateProductDetails(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getProductInStock());
            sendMessage(response, HttpServletResponse.SC_OK, "Product updated");
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }
}
