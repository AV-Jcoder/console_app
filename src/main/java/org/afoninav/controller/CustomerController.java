package org.afoninav.controller;

import org.afoninav.model.Customer;
import org.afoninav.service.CustomerService;
import org.afoninav.service.impl.CustomerServiceImpl;

import java.util.List;

public class CustomerController {

    private CustomerService customerService;

    public CustomerController() {
        customerService = new CustomerServiceImpl();
    }

    public boolean create(Customer customer) {
        return customerService.create(customer);
    }

    public Customer readByID(Long id) {
        return customerService.readById(id);
    }

    public List<Customer> readAll() {
        return customerService.readAll();
    }

    public boolean update(Customer customer) {
        return customerService.update(customer);
    }

    public boolean deleteById(Long id) {
        return customerService.deleteById(id);
    }














}
