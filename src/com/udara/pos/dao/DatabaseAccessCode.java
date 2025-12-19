package com.udara.pos.dao;

import com.udara.pos.db.DbConnection;
import com.udara.pos.dto.CustomerDto;
import com.udara.pos.dto.ProductDto;
import com.udara.pos.dto.UserDto;
import com.udara.pos.util.PasswordManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {

    // user management

    public static boolean createUser(String email, String password) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO user VALUES (?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, PasswordManager.encryptPassword(password));
        return preparedStatement.executeUpdate() > 0;
    }

    public static UserDto findUser(String email) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM user WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new UserDto(resultSet.getString(1), resultSet.getString(2));
        }
        return null;
    }

    // customer management

    public static boolean createCustomer(String email, String name, String contact, double salary) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, contact);
        preparedStatement.setDouble(4, salary);
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE customer SET name=?, contact=?, salary=? WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, contact);
        preparedStatement.setDouble(3, salary);
        preparedStatement.setString(4, email);
        return preparedStatement.executeUpdate() > 0;
    }

    public static CustomerDto findCustomer(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new CustomerDto(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4));
        }
        return null;
    }

    public static boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM customer WHERE email=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, email);
        return preparedStatement.executeUpdate() > 0;
    }

    public static List<CustomerDto> findAllCustomers() throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM customer";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<CustomerDto> dtos = new ArrayList<>();
        while (resultSet.next()) {
            dtos.add(new CustomerDto(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4)));
        }
        return dtos;
    }

    public static List<CustomerDto> searchCustomers(String searchText) throws ClassNotFoundException, SQLException {
        searchText = "%" + searchText + "%";
        String sql = "SELECT * FROM customer WHERE email LIKE ? || name LIKE ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, searchText);
        preparedStatement.setString(2, searchText);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<CustomerDto> dtos = new ArrayList<>();
        while (resultSet.next()) {
            dtos.add(new CustomerDto(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4)));
        }
        return dtos;
    }

    // product management

    public static boolean createProduct(int id, String description) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO product VALUES (?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, description);
        return preparedStatement.executeUpdate() > 0;
    }

    public static int getLastId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT code FROM product ORDER BY code DESC LIMIT 1";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static boolean updateProduct(int id, String description) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE product SET description=? WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, description);
        preparedStatement.setInt(2, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean deleteProduct(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM product WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public static List<ProductDto> findAllProducts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ProductDto> productDtos = new ArrayList<>();
        while (resultSet.next()) {
            productDtos.add(new ProductDto(resultSet.getInt(1), resultSet.getString(2)));
        }
        return productDtos;
    }

    public static List<ProductDto> searchProducts(String searchText) throws SQLException, ClassNotFoundException {
        searchText = "%" + searchText + "%";
        String sql = "SELECT * FROM product WHERE code LIKE ? || description LIKE ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, searchText);
        preparedStatement.setString(2, searchText);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ProductDto> productDtos = new ArrayList<>();
        while (resultSet.next()) {
            productDtos.add(new ProductDto(resultSet.getInt(1), resultSet.getString(2)));
        }
        return productDtos;
    }
}
