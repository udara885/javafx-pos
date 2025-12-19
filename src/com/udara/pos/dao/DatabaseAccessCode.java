package com.udara.pos.dao;

import com.udara.pos.db.DbConnection;
import com.udara.pos.dto.CustomerDto;
import com.udara.pos.dto.ProductDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {

    // customer management

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

    public static int getLastId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT code FROM product ORDER BY code DESC LIMIT 1";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
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
