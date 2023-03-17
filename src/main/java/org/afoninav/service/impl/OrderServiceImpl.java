package org.afoninav.service.impl;

import org.afoninav.model.Order;
import org.afoninav.repository.jdbc.JdbcOrderRepositoryImpl;
import org.afoninav.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private JdbcOrderRepositoryImpl mySqlOrderRepository;

    public OrderServiceImpl() {
        mySqlOrderRepository = new JdbcOrderRepositoryImpl();
    }

    @Override
    public boolean create(Order order) {
        return mySqlOrderRepository.create(order);
    }

    @Override
    public Order readById(Long id) {
        return mySqlOrderRepository.readById(id);
    }

    @Override
    public List<Order> readAll() {
        return mySqlOrderRepository.readAll();
    }

    @Override
    public boolean update(Order order) {
        return mySqlOrderRepository.update(order);
    }

    @Override
    public boolean deleteById(Long id) {
        return mySqlOrderRepository.deleteByID(id);
    }
}
