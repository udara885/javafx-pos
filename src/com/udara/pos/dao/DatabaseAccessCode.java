package com.udara.pos.dao;

import com.udara.pos.dto.CustomerDto;
import com.udara.pos.dto.UserDto;
import com.udara.pos.util.PasswordManager;

import java.sql.*;
import java.util.List;

public class DatabaseAccessCode {

    // user management

    public static boolean createUser(String email, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "INSERT INTO user VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, PasswordManager.encryptPassword(password));
        return preparedStatement.executeUpdate() > 0;
    }

    public static UserDto findUser(String email) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "SELECT * FROM user WHERE email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }

    // customer management

    public static boolean createCustomer(String email, String name, String contact, double salary) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, contact);
        preparedStatement.setDouble(4, salary);
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateCustomer(String email, String name, String contact, double salary){
        return false;
    }

    public static CustomerDto findCustomer(String email) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "SELECT * FROM customer WHERE email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    public static boolean deleteCustomer(String email){
        return false;
    }

    public static List<CustomerDto> findAllCustomer(){
        return null;
    }

    public static List<CustomerDto> searchCustomers(String searchText){
        return null;
    }
}
