package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.CrudUtil;
import com.udara.pos.dao.custom.ItemDetailDao;
import com.udara.pos.entitiy.ItemDetail;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ItemDetailDaoImpl implements ItemDetailDao {
    @Override
    public boolean save(ItemDetail itemDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO product_detail_has_order_detail VALUES (?,?,?,?,?)",itemDetail.getDetailCode(),itemDetail.getOrder(),itemDetail.getQty(),itemDetail.getDiscount(),itemDetail.getAmount());
    }

    @Override
    public boolean update(ItemDetail itemDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String string) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ItemDetail find(String string) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ItemDetail> findAll() throws SQLException, ClassNotFoundException {
        return Collections.emptyList();
    }
}
