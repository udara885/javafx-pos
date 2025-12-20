package com.udara.pos.bo.custom;

import com.udara.pos.dto.CustomerDto;

import java.util.List;

public interface CustomerBo {
    public boolean saveCustomer(CustomerDto dto);

    public boolean UpdateCustomer(CustomerDto dto);

    public boolean deleteCustomer(String email);

    public CustomerDto findCustomer(String email);

    public List<CustomerDto> findAllCustomers();
}
