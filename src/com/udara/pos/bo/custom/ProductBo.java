package com.udara.pos.bo.custom;

import com.udara.pos.dto.ProductDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductBo {
    public boolean saveProduct(ProductDto dto) throws SQLException, ClassNotFoundException;

    public boolean updateProduct(ProductDto dto) throws SQLException, ClassNotFoundException;

    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException;

    public ProductDto findProduct(int code) throws SQLException, ClassNotFoundException;

    public List<ProductDto> findAllProducts() throws SQLException, ClassNotFoundException;

    public int getLastId() throws SQLException, ClassNotFoundException;

    public List<ProductDto> searchProducts(String searchText) throws SQLException, ClassNotFoundException;
}
