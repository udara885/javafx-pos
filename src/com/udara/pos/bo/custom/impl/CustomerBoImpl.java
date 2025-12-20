package com.udara.pos.bo.custom.impl;

import com.udara.pos.bo.custom.CustomerBo;
import com.udara.pos.dto.CustomerDto;

import java.util.Collections;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    @Override
    public boolean saveCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean UpdateCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String email) {
        return false;
    }

    @Override
    public CustomerDto findCustomer(String email) {
        return null;
    }

    @Override
    public List<CustomerDto> findAllCustomers() {
        return Collections.emptyList();
    }
}
