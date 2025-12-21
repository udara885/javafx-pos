package com.udara.pos.bo.custom.impl;

import com.udara.pos.bo.custom.ProductDetailBo;
import com.udara.pos.dao.DaoFactory;
import com.udara.pos.dao.custom.ProductDetailDao;
import com.udara.pos.dto.ProductDetailDto;
import com.udara.pos.entitiy.ProductDetail;
import com.udara.pos.enums.DaoType;

import java.sql.SQLException;

public class ProductDetailBoImpl implements ProductDetailBo {
    ProductDetailDao dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT_DETAIL);

    @Override
    public boolean saveProductDetail(ProductDetailDto dto) throws SQLException, ClassNotFoundException {
        return dao.save(new ProductDetail(dto.getCode(), dto.getBarcode(), dto.getQtyOnHand(), dto.getSellingPrice(), dto.getBuyingPrice(), dto.getShowPrice(), dto.getProductCode(), dto.isDiscountAvailability()));
    }
}
