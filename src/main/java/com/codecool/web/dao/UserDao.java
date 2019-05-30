package com.codecool.web.dao;

import com.codecool.web.model.Role;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<User> findAll() throws SQLException;

    User findByEmail(String email) throws SQLException;

    User addUser(String name, String email, String password, Role role) throws SQLException;

    void updateUserNameById(int id, String name) throws SQLException;

    void updateUserEmailById(int id, String email) throws SQLException;

    void updateUserPasswordById(int id, String password) throws SQLException;

    boolean doesUserExists(String email) throws SQLException;
}
