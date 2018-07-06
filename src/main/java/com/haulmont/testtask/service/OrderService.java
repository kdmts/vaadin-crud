package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.OrderDao;
import com.haulmont.testtask.dao.impl.OrderDaoImpl;
import com.haulmont.testtask.model.Order;

import java.util.List;

public class OrderService {
    private static OrderService instance;

    private OrderDao orderDao;

    private OrderService(){
        orderDao = OrderDaoImpl.getInstance();
    }

    public static OrderService getInstance(){
        if (instance == null){
            instance = new OrderService();
        }
        return instance;
    }

    public void create(Order order) {
        orderDao.create(order);
    }

    public void update(Order order) {
        orderDao.update(order);
    }

    public void delete(Order order) {
        orderDao.delete(order);
    }

    public List<Order> getAll() {
        return orderDao.getAll();
    }

    public List<Order> filterByClient(String value){return orderDao.filterByClient(value);}
    public List<Order> filterByStatus(String value){return orderDao.filterByStatus(value);}
    public List<Order> filterByDescription(String value){return orderDao.filterByDescription(value);}
}
