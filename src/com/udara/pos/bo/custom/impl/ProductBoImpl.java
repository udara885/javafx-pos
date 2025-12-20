package com.udara.pos.bo.custom.impl;

import com.udara.pos.bo.custom.ProductBo;
import com.udara.pos.dto.ProductDto;

import java.util.Collections;
import java.util.List;

public class ProductBoImpl implements ProductBo {
    @Override
    public boolean saveProduct(ProductDto dto) {
        return false;
    }

    @Override
    public boolean UpdateProduct(ProductDto dto) {
        return false;
    }

    @Override
    public boolean deleteProduct(int code) {
        return false;
    }

    @Override
    public ProductDto findProduct(int code) {
        return null;
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return Collections.emptyList();
    }
}
