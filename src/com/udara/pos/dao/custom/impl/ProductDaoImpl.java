package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.custom.ProductDao;
import com.udara.pos.entitiy.Product;

import java.util.Collections;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean saveProduct(Product customer) {
        return false;
    }

    @Override
    public boolean updateProduct(Product customer) {
        return false;
    }

    @Override
    public boolean deleteProduct(int code) {
        return false;
    }

    @Override
    public Product findProduct(int code) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return Collections.emptyList();
    }
}
