package com.codecool.web.service;

import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    User loginUser(String email, String password) throws SQLException, ServiceException;

    List<User> findAll() throws SQLException;

    User addUser(String name, String email, String password, Role role) throws SQLException;

    void updateUserNameById(int id, String name) throws SQLException;

    void updateUserEmailById(int id, String email) throws SQLException;

    void updateUserPasswordById(int id, String password) throws SQLException;

    boolean doesUserExists(String email) throws SQLException;

    User findById(int id) throws SQLException;
}
