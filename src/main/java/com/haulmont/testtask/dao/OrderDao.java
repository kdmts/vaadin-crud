package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Order;

import java.util.List;

public interface OrderDao {
    void create(Order order);
    //void read();
    void update(Order order);
    void delete(Order order);
    List<Order> getAll();
    List<Order> filterByClient(String value);
    List<Order> filterByStatus(String value);
    List<Order> filterByDescription(String value);
}
