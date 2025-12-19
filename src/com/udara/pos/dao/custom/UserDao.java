package com.udara.pos.dao.custom;

import com.udara.pos.entitiy.User;

import java.util.List;

public interface UserDao {
    public boolean saveUser(User user);

    public boolean updateUser(User user);

    public boolean deleteUser(String email);

    public User findUser(String email);

    public List<User> findAllUsers();
}
