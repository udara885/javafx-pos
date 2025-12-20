package com.udara.pos.bo.custom.impl;

import com.udara.pos.bo.custom.UserBo;
import com.udara.pos.dto.UserDto;

import java.util.Collections;
import java.util.List;

public class UserBoImpl implements UserBo {
    @Override
    public boolean saveUser(UserDto dto) {
        return false;
    }

    @Override
    public boolean UpdateUser(UserDto dto) {
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }

    @Override
    public UserDto findUser(String email) {
        return null;
    }

    @Override
    public List<UserDto> findAllUsers() {
        return Collections.emptyList();
    }
}
