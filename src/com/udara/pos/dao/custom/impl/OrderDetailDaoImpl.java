package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.CrudUtil;
import com.udara.pos.dao.custom.OrderDetailDao;
import com.udara.pos.entitiy.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO order_detail VALUES (?,?,?,?,?,?)", orderDetail.getCode(), orderDetail.getIssuedDate(), orderDetail.getTotalCost(), orderDetail.getCustomerEmail(), orderDetail.getDiscount(), orderDetail.getUserEmail());
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail find(Integer integer) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<OrderDetail> findAll() throws SQLException, ClassNotFoundException {
        return Collections.emptyList();
    }

    @Override
    public int getLastId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT code FROM order_detail ORDER BY code DESC LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
