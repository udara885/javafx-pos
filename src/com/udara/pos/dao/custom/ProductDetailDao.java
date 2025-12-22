package com.udara.pos.dao.custom;

import com.udara.pos.dao.CrudDao;
import com.udara.pos.entitiy.ProductDetail;

import java.sql.SQLException;
import java.util.List;

public interface ProductDetailDao extends CrudDao<ProductDetail, String> {
    public List<ProductDetail> findAllProductDetails(int code) throws SQLException, ClassNotFoundException;

    public ProductDetail findProductDetail(String code) throws SQLException, ClassNotFoundException;
}
