package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.custom.ProductDao;
import com.udara.pos.db.DbConnection;
import com.udara.pos.entitiy.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean save(Product product) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO product VALUES (?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, product.getCode());
        preparedStatement.setString(2, product.getDescription());
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public boolean update(Product product) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE product SET description=? WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, product.getDescription());
        preparedStatement.setInt(2, product.getCode());
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Integer i) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM product WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, i);
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public Product find(Integer i) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product WHERE code=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, i);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Product(resultSet.getInt(1), resultSet.getString(2));
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Product> productList = new ArrayList<>();
        while (resultSet.next()) {
            productList.add(new Product(resultSet.getInt(1), resultSet.getString(2)));
        }
        return productList;
    }

    @Override
    public int getLastId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT code FROM product ORDER BY code DESC LIMIT 1";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public List<Product> searchProducts(String searchText) throws SQLException, ClassNotFoundException {
        searchText = "%" + searchText + "%";
        String sql = "SELECT * FROM product WHERE code LIKE ? || description LIKE ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, searchText);
        preparedStatement.setString(2, searchText);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Product> productList = new ArrayList<>();
        while (resultSet.next()) {
            productList.add(new Product(resultSet.getInt(1), resultSet.getString(2)));
        }
        return productList;
    }
}
