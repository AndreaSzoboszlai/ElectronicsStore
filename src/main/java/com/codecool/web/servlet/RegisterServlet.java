package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.simple.PasswordService;
import com.codecool.web.service.simple.SimpleUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends AbstractServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);
            PasswordService passwordService = new PasswordService();

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Role userRole = Role.CUSTOMER;

            if (!userService.doesUserExists(email)) {
                User user = userService.addUser(name, email, passwordService.getHashedPassword(password), userRole);
                request.setAttribute("user", user);
                sendMessage(response, HttpServletResponse.SC_OK, user);
            }

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            sendMessage(response, HttpServletResponse.SC_BAD_REQUEST, ex);
        }
    }
}
