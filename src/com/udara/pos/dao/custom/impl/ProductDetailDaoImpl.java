package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.CrudUtil;
import com.udara.pos.dao.custom.ProductDetailDao;
import com.udara.pos.entitiy.ProductDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDetailDaoImpl implements ProductDetailDao {
    @Override
    public boolean save(ProductDetail productDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO product_detail VALUES (?,?,?,?,?,?,?,?)", productDetail.getCode(), productDetail.getBarcode(), productDetail.getQtyOnHand(), productDetail.getSellingPrice(), productDetail.getBuyingPrice(), productDetail.isDiscountAvailability(), productDetail.getShowPrice(), productDetail.getProductCode());
    }

    @Override
    public boolean update(ProductDetail productDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String string) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM product_detail WHERE code=?", string);
    }

    @Override
    public ProductDetail find(String string) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ProductDetail> findAll() throws SQLException, ClassNotFoundException {
        return Collections.emptyList();
    }

    @Override
    public List<ProductDetail> findAllProductDetails(int code) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product_detail WHERE product_code=?", code);
        List<ProductDetail> list = new ArrayList<>();
        while (set.next()) {
            list.add(new ProductDetail(set.getString(1), set.getString(2), set.getInt(3), set.getDouble(4), set.getDouble(5), set.getDouble(7), set.getInt(8), set.getBoolean(6)));
        }
        return list;
    }

    @Override
    public ProductDetail findProductDetail(String code) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product_detail WHERE code=?", code);
        if (set.next()) {
            return new ProductDetail(set.getString(1), set.getString(2), set.getInt(3), set.getDouble(4), set.getDouble(5), set.getDouble(7), set.getInt(8), set.getBoolean(6));
        }
        return null;
    }
}
