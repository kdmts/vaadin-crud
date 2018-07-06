package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.OrderDao;
import com.haulmont.testtask.model.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private EntityManager em;

    private static OrderDao instance;

    private OrderDaoImpl(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsqldb-file-persistence-unit");
        em = emf.createEntityManager();
    }

    public static OrderDao getInstance(){
        if (instance == null){
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    @Override
    public void create(Order order) {
        try{
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public void update(Order order) {
        try{
            em.getTransaction().begin();
            em.merge(order);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Order order) {
        try{
            em.getTransaction().begin();
            em.remove(order);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getAll() {
        TypedQuery<Order> query = em.createNamedQuery("Order.getAll", Order.class);
        return query.getResultList();
    }

    public List<Order> filterByClient(String value){
        List<Order> orderList = null;
        try{
            em.getTransaction().begin();
            orderList = em.createQuery("select o from Order o join o.client c where c.surname like:value")
                    .setParameter("value", "%" + value + "%").getResultList();
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
        return orderList;
    }

    public List<Order> filterByStatus(String value){
        List<Order> orderList = null;
        try{
            em.getTransaction().begin();
            orderList = em.createQuery("select o from Order o where o.orderStatus = :value")
                    .setParameter("value", value).getResultList();
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
        return orderList;
    }

    public List<Order> filterByDescription(String value){
        List<Order> orderList = null;
        try{
            em.getTransaction().begin();
            orderList = em.createQuery("select o from Order o where o.description like:value")
                    .setParameter("value", "%" + value + "%").getResultList();
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
        return orderList;
    }
}
