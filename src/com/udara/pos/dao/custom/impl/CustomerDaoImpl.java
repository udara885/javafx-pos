package com.udara.pos.dao.custom.impl;

import com.udara.pos.dao.custom.CustomerDao;
import com.udara.pos.entitiy.Customer;

import java.util.Collections;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean saveCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String email) {
        return false;
    }

    @Override
    public Customer findCustomer(String email) {
        return null;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return Collections.emptyList();
    }
}
