package com.udara.pos.bo.custom;

import com.udara.pos.bo.SuperBo;
import com.udara.pos.dto.ProductDetailDto;
import com.udara.pos.dto.ProductDetailJoinDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductDetailBo extends SuperBo {
    public boolean saveProductDetail(ProductDetailDto dto) throws SQLException, ClassNotFoundException;

    public List<ProductDetailDto> findAllProductDetails(int code) throws SQLException, ClassNotFoundException;

    public ProductDetailDto findProductDetail(String code) throws SQLException, ClassNotFoundException;

    public ProductDetailJoinDto findProductJoinDetail(String code) throws SQLException, ClassNotFoundException;

    public boolean deleteProductDetail(String code) throws SQLException, ClassNotFoundException;
}
