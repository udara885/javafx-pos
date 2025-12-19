package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.custom.UserDao;
import com.udara.pos.db.DbConnection;
import com.udara.pos.entitiy.User;
import com.udara.pos.util.PasswordManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean saveUser(User user) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO user VALUES (?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, PasswordManager.encryptPassword(user.getPassword()));
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE user SET password=? WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, PasswordManager.encryptPassword(user.getPassword()));
        preparedStatement.setString(2, user.getEmail());
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public boolean deleteUser(String email) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM user WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, email);
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public User findUser(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new User(resultSet.getString(1), resultSet.getString(2));
        }
        return null;
    }

    @Override
    public List<User> findAllUsers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            userList.add(new User(resultSet.getString(1), resultSet.getString(2)));
        }
        return userList;
    }
}
