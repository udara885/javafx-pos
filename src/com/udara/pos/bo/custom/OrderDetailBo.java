package com.udara.pos.bo.custom;

import com.udara.pos.dto.OrderDetailDto;

import java.sql.SQLException;

public interface OrderDetailBo{
    public boolean makeOrder(OrderDetailDto dto) throws SQLException;

    public int getLastId() throws SQLException, ClassNotFoundException;
}
