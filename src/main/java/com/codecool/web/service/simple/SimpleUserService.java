package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;
//import javafx.concurrent.Service;

import java.sql.SQLException;
import java.util.List;

public final class SimpleUserService implements UserService {

    private final UserDao userDao;

    public SimpleUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email, String password) throws SQLException, ServiceException {
        try {
            User user = userDao.findByEmail(email);
            if (user == null || !user.getPassword().equals(password)) {
                throw new ServiceException("Wrong username or password");
            }
            return user;
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<User> findAll() throws SQLException {
        return userDao.findAll();
    }

    public User findByEmail(String email) throws SQLException {
        return userDao.findByEmail(email);
    }

    public User addUser(String name, String email, String password, Role role) throws SQLException {
        return userDao.addUser(name, email, password, role);
    }

    public void updateUserNameById(int id, String name) throws SQLException{
        userDao.updateUserNameById(id, name);
    }

    public void updateUserEmailById(int id, String email) throws SQLException{
        userDao.updateUserEmailById(id, email);
    }

    public void updateUserPasswordById(int id, String password) throws SQLException {
        userDao.updateUserPasswordById(id, password);
    }

    public boolean doesUserExists(String email) throws SQLException {
        return userDao.doesUserExists(email);
    }
}
