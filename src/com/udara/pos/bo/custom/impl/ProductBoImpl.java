package com.udara.pos.bo.custom.impl;

import com.udara.pos.bo.custom.ProductBo;
import com.udara.pos.dao.DaoFactory;
import com.udara.pos.dao.custom.ProductDao;
import com.udara.pos.dto.ProductDto;
import com.udara.pos.entitiy.Product;
import com.udara.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBoImpl implements ProductBo {
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public boolean saveProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDao.save(new Product(dto.getId(), dto.getDescription()));
    }

    @Override
    public boolean updateProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDao.update(new Product(dto.getId(), dto.getDescription()));
    }

    @Override
    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException {
        return productDao.delete(code);
    }

    @Override
    public ProductDto findProduct(int code) throws SQLException, ClassNotFoundException {
        Product product = productDao.find(code);
        if (product != null) {
            return new ProductDto(product.getCode(), product.getDescription());
        }
        return null;
    }

    @Override
    public List<ProductDto> findAllProducts() throws SQLException, ClassNotFoundException {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productDao.findAll()) {
            productDtos.add(new ProductDto(p.getCode(), p.getDescription()));
        }
        return productDtos;
    }

    public int getLastId() throws SQLException, ClassNotFoundException {
        return productDao.getLastId();
    }

    public List<ProductDto> searchProducts(String searchText) throws SQLException, ClassNotFoundException {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productDao.searchProducts(searchText)) {
            productDtos.add(new ProductDto(p.getCode(), p.getDescription()));
        }
        return productDtos;
    }
}
