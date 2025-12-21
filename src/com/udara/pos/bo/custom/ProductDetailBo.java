package com.udara.pos.bo.custom;

import com.udara.pos.bo.SuperBo;
import com.udara.pos.dto.ProductDetailDto;

import java.sql.SQLException;

public interface ProductDetailBo extends SuperBo {
    public boolean saveProductDetail(ProductDetailDto dto) throws SQLException, ClassNotFoundException;
}
