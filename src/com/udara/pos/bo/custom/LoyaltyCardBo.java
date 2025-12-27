package com.udara.pos.bo.custom;

import com.udara.pos.dto.LoyaltyCardDto;

import java.sql.SQLException;

public interface LoyaltyCardBo {
    public boolean saveLoyaltyData(LoyaltyCardDto dto) throws SQLException, ClassNotFoundException;
}
