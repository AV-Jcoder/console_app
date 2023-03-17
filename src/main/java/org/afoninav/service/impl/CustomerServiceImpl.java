package org.afoninav.service.impl;

import org.afoninav.model.Customer;
import org.afoninav.repository.jdbc.JdbcCustomerRepositoryImpl;
import org.afoninav.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    JdbcCustomerRepositoryImpl mySqlCustomerRepository;

    public CustomerServiceImpl() {
        mySqlCustomerRepository = new JdbcCustomerRepositoryImpl();
    }

    @Override
    public boolean create(Customer customer) {
        return mySqlCustomerRepository.create(customer);
    }

    @Override
    public Customer readById(Long id) {
        return mySqlCustomerRepository.readById(id);
    }

    @Override
    public List<Customer> readAll() {
        return mySqlCustomerRepository.readAll();
    }

    @Override
    public boolean update(Customer customer) {
        return mySqlCustomerRepository.update(customer);
    }

    @Override
    public boolean deleteById(Long id) {
        return mySqlCustomerRepository.deleteByID(id);
    }
}
