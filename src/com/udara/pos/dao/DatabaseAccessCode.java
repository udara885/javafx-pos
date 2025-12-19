package com.udara.pos.dao;

import com.udara.pos.dao.custom.CustomerDao;
import com.udara.pos.dao.custom.ProductDao;
import com.udara.pos.dao.custom.UserDao;
import com.udara.pos.dao.custom.impl.CustomerDaoImpl;
import com.udara.pos.dao.custom.impl.ProductDaoImpl;
import com.udara.pos.dao.custom.impl.UserDaoImpl;
import com.udara.pos.dto.CustomerDto;
import com.udara.pos.dto.ProductDto;
import com.udara.pos.dto.UserDto;
import com.udara.pos.entitiy.Customer;
import com.udara.pos.entitiy.Product;
import com.udara.pos.entitiy.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {
    UserDao userDao = new UserDaoImpl();
    CustomerDao customerDao = new CustomerDaoImpl();
    ProductDao productDao = new ProductDaoImpl();

    // user management

    public boolean createUser(String email, String password) throws ClassNotFoundException, SQLException {
        return userDao.saveUser(new User(email, password));
    }

    public UserDto findUser(String email) throws ClassNotFoundException, SQLException {
        User user = userDao.findUser(email);
        if (user != null) {
            return new UserDto(user.getEmail(), user.getPassword());
        }
        return null;
    }

    // customer management

    public boolean createCustomer(String email, String name, String contact, double salary) throws SQLException, ClassNotFoundException {
        return customerDao.saveCustomer(new Customer(email, name, contact, salary));
    }

    public boolean updateCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        return customerDao.updateCustomer(new Customer(email, name, contact, salary));
    }

    public CustomerDto findCustomer(String email) throws SQLException, ClassNotFoundException {
        Customer customer = customerDao.findCustomer(email);
        if (customer != null) {
            return new CustomerDto(customer.getEmail(), customer.getName(), customer.getContact(), customer.getSalary());
        }
        return null;
    }

    public boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {
        return customerDao.deleteCustomer(email);
    }

    public List<CustomerDto> findAllCustomers() throws ClassNotFoundException, SQLException {
        List<CustomerDto> dtos = new ArrayList<>();
        for (Customer c : customerDao.findAllCustomers()) {
            dtos.add(new CustomerDto(c.getEmail(), c.getName(), c.getContact(), c.getSalary()));
        }
        return dtos;
    }

    public List<CustomerDto> searchCustomers(String searchText) throws ClassNotFoundException, SQLException {
        List<CustomerDto> dtos = new ArrayList<>();
        for (Customer c : customerDao.searchCustomers(searchText)) {
            dtos.add(new CustomerDto(c.getEmail(), c.getName(), c.getContact(), c.getSalary()));
        }
        return dtos;
    }

    // product management

    public boolean createProduct(int id, String description) throws SQLException, ClassNotFoundException {
        return productDao.saveProduct(new Product(id, description));
    }

    public int getLastId() throws SQLException, ClassNotFoundException {
        return productDao.getLastId();
    }

    public boolean updateProduct(int id, String description) throws SQLException, ClassNotFoundException {
        return productDao.updateProduct(new Product(id, description));
    }

    public boolean deleteProduct(int id) throws SQLException, ClassNotFoundException {
        return productDao.deleteProduct(id);
    }

    public List<ProductDto> findAllProducts() throws SQLException, ClassNotFoundException {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productDao.findAllProducts()) {
            productDtos.add(new ProductDto(p.getCode(), p.getDescription()));
        }
        return productDtos;
    }

    public List<ProductDto> searchProducts(String searchText) throws SQLException, ClassNotFoundException {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productDao.findAllProducts()) {
            productDtos.add(new ProductDto(p.getCode(), p.getDescription()));
        }
        return productDtos;
    }
}
