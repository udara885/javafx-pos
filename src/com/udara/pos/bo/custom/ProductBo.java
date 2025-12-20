package com.udara.pos.bo.custom;

import com.udara.pos.dto.ProductDto;

import java.util.List;

public interface ProductBo {
    public boolean saveProduct(ProductDto dto);

    public boolean UpdateProduct(ProductDto dto);

    public boolean deleteProduct(int code);

    public ProductDto findProduct(int code);

    public List<ProductDto> findAllProducts();
}
