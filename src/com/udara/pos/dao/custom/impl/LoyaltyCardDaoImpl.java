package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.CrudUtil;
import com.udara.pos.dao.custom.LoyaltyCardDao;
import com.udara.pos.entitiy.LoyaltyCard;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class LoyaltyCardDaoImpl implements LoyaltyCardDao {
    @Override
    public boolean save(LoyaltyCard loyaltyCard) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO loyalty_card VALUES (?,?,?,?)", loyaltyCard.getCode(),
                loyaltyCard.getCardType().name(), loyaltyCard.getBarcode(), loyaltyCard.getEmail());
    }

    @Override
    public boolean update(LoyaltyCard loyaltyCard) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public LoyaltyCard find(Integer integer) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<LoyaltyCard> findAll() throws SQLException, ClassNotFoundException {
        return Collections.emptyList();
    }
}
