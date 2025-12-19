package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.custom.UserDao;
import com.udara.pos.entitiy.User;

import java.util.Collections;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean saveUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }

    @Override
    public User findUser(String email) {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return Collections.emptyList();
    }
}
