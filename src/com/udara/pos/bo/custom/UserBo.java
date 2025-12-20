package com.udara.pos.bo.custom;

import com.udara.pos.dto.UserDto;

import java.util.List;

public interface UserBo {
    public boolean saveUser(UserDto dto);

    public boolean UpdateUser(UserDto dto);

    public boolean deleteUser(String email);

    public UserDto findUser(String email);

    public List<UserDto> findAllUsers();
}
