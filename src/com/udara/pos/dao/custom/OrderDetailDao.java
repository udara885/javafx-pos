package com.udara.pos.dao.custom;

import com.udara.pos.dao.CrudDao;
import com.udara.pos.entitiy.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailDao extends CrudDao<OrderDetail, Integer> {
    public int getLastId() throws SQLException, ClassNotFoundException;
}
