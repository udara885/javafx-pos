package com.udara.pos.bo.custom.impl;

import com.udara.pos.bo.custom.ProductDetailBo;
import com.udara.pos.dao.DaoFactory;
import com.udara.pos.dao.custom.ProductDetailDao;
import com.udara.pos.dto.ProductDetailDto;
import com.udara.pos.entitiy.ProductDetail;
import com.udara.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailBoImpl implements ProductDetailBo {
    ProductDetailDao dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT_DETAIL);

    @Override
    public boolean saveProductDetail(ProductDetailDto dto) throws SQLException, ClassNotFoundException {
        return dao.save(new ProductDetail(dto.getCode(), dto.getBarcode(), dto.getQtyOnHand(), dto.getSellingPrice(), dto.getBuyingPrice(), dto.getShowPrice(), dto.getProductCode(), dto.isDiscountAvailability()));
    }

    @Override
    public List<ProductDetailDto> findAllProductDetails(int code) throws SQLException, ClassNotFoundException {
        List<ProductDetailDto> dtos = new ArrayList<>();
        for (ProductDetail p : dao.findAllProductDetails(code)) {
            dtos.add(new ProductDetailDto(p.getCode(), p.getBarcode(), p.getQtyOnHand(), p.getSellingPrice(),
                    p.getBuyingPrice(), p.getShowPrice(), p.getProductCode(), p.isDiscountAvailability()));
        }
        return dtos;
    }

    @Override
    public boolean deleteProductDetail(String code) throws SQLException, ClassNotFoundException {
        return dao.delete(code);
    }
}
