package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.simple.PasswordService;
import com.codecool.web.service.simple.SimpleUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/profile")
public class ProfileServlet extends AbstractServlet {
    private final ObjectMapper om = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);
            int id = Integer.valueOf(request.getParameter("user-id"));
            User user = userService.findById(id);
            sendMessage(response, HttpServletResponse.SC_OK, user);
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new SimpleUserService(userDao);
            PasswordService passwordService = new PasswordService();
            User user = om.readValue(request.getInputStream(), User.class);
            if (!user.getPassword().equals("*****")) {
                userService.updateUserPasswordById(user.getId(), passwordService.getHashedPassword(user.getPassword()));
            }
            userService.updateUserNameById(user.getId(), user.getName());
            userService.updateUserEmailById(user.getId(), user.getEmail());
            sendMessage(response, HttpServletResponse.SC_OK, user);
        } catch (SQLException ex) {
            handleSqlError(response, ex);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            sendMessage(response, HttpServletResponse.SC_BAD_REQUEST, ex);
        }
    }
}
