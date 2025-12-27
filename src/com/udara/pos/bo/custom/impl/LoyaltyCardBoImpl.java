package com.udara.pos.bo.custom.impl;

import com.udara.pos.bo.custom.LoyaltyCardBo;
import com.udara.pos.dao.DaoFactory;
import com.udara.pos.dao.custom.LoyaltyCardDao;
import com.udara.pos.dto.LoyaltyCardDto;
import com.udara.pos.entitiy.LoyaltyCard;
import com.udara.pos.enums.DaoType;

import java.sql.SQLException;

public class LoyaltyCardBoImpl implements LoyaltyCardBo {
    private LoyaltyCardDao loyaltyCardDao = DaoFactory.getInstance().getDao(DaoType.LOYALTY_CARD);

    @Override
    public boolean saveLoyaltyData(LoyaltyCardDto dto) throws SQLException, ClassNotFoundException {
        return loyaltyCardDao.save(new LoyaltyCard(dto.getCode(), dto.getCardType(), dto.getBarcode(), dto.getEmail()));
    }
}
