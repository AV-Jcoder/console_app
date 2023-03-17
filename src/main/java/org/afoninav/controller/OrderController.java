package org.afoninav.controller;

import org.afoninav.model.Order;
import org.afoninav.service.OrderService;
import org.afoninav.service.impl.OrderServiceImpl;

import java.util.List;

public class OrderController {

    private OrderService orderService;

    public OrderController() {
        orderService = new OrderServiceImpl();
    }

    public boolean create(Order order) {
        return orderService.create(order);
    }

    public Order readByID(Long id) {
        return orderService.readById(id);
    }

    public List<Order> readAll() {
        return orderService.readAll();
    }

    public boolean update(Order order) {
        return orderService.update(order);
    }

    public boolean deleteById(Long id) {
        return orderService.deleteById(id);
    }


}
