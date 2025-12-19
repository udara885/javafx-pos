package com.udara.pos.dao.custom;

import com.udara.pos.dao.CrudDao;
import com.udara.pos.entitiy.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends CrudDao<Customer, String> {
    //-------------------
    public List<Customer> searchCustomers(String searchText) throws SQLException, ClassNotFoundException;
}
