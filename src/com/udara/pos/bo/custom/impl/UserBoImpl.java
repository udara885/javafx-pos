package com.udara.pos.bo.custom.impl;

import com.udara.pos.bo.custom.UserBo;
import com.udara.pos.dao.DaoFactory;
import com.udara.pos.dao.custom.UserDao;
import com.udara.pos.dto.UserDto;
import com.udara.pos.entitiy.User;
import com.udara.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDao.save(new User(dto.getEmail(), dto.getPassword()));
    }

    @Override
    public boolean UpdateUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDao.update(new User(dto.getEmail(), dto.getPassword()));
    }

    @Override
    public boolean deleteUser(String email) throws SQLException, ClassNotFoundException {
        return userDao.delete(email);
    }

    @Override
    public UserDto findUser(String email) throws SQLException, ClassNotFoundException {
        User user = userDao.find(email);
        if (user != null) {
            return new UserDto(user.getEmail(), user.getPassword());
        }
        return null;
    }

    @Override
    public List<UserDto> findAllUsers() throws SQLException, ClassNotFoundException {
        List<UserDto> dtos = new ArrayList<>();
        for (User u : userDao.findAll()) {
            dtos.add(new UserDto(u.getEmail(), u.getPassword()));
        }
        return dtos;
    }
}
