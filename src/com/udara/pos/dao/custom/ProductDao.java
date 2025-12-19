package com.udara.pos.dao.custom;

import com.udara.pos.dao.CrudDao;
import com.udara.pos.entitiy.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends CrudDao<Product, Integer> {
    //-------------

    public int getLastId() throws SQLException, ClassNotFoundException;

    public List<Product> searchProducts(String searchText) throws SQLException, ClassNotFoundException;
}
