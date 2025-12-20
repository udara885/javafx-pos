package com.udara.pos.dao;

import com.udara.pos.entitiy.SuperEntity;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T extends SuperEntity, ID> extends SuperDao{
    public boolean save(T t) throws SQLException, ClassNotFoundException;

    public boolean update(T t) throws SQLException, ClassNotFoundException;

    public boolean delete(ID id) throws SQLException, ClassNotFoundException;

    public T find(ID id) throws SQLException, ClassNotFoundException;

    public List<T> findAll() throws SQLException, ClassNotFoundException;
}
