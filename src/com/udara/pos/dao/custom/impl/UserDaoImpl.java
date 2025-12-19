package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.CrudUtil;
import com.udara.pos.dao.custom.UserDao;
import com.udara.pos.entitiy.User;
import com.udara.pos.util.PasswordManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO user VALUES (?,?)", user.getEmail(), PasswordManager.encryptPassword(user.getPassword()));
    }

    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE user SET password=? WHERE email=?",PasswordManager.encryptPassword(user.getPassword()),user.getEmail() );
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM user WHERE email=?",s);
    }

    @Override
    public User find(String s) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE email=?",s);
        if (resultSet.next()) {
            return new User(resultSet.getString(1), resultSet.getString(2));
        }
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user");
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            userList.add(new User(resultSet.getString(1), resultSet.getString(2)));
        }
        return userList;
    }
}
