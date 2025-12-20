package com.udara.pos.bo.custom;

import com.udara.pos.bo.SuperBo;
import com.udara.pos.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserBo extends SuperBo {
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException;

    public boolean UpdateUser(UserDto dto) throws SQLException, ClassNotFoundException;

    public boolean deleteUser(String email) throws SQLException, ClassNotFoundException;

    public UserDto findUser(String email) throws SQLException, ClassNotFoundException;

    public List<UserDto> findAllUsers() throws SQLException, ClassNotFoundException;
}
