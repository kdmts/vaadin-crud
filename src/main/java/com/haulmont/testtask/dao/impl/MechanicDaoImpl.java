package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.MechanicDao;
import com.haulmont.testtask.model.Mechanic;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

public class MechanicDaoImpl implements MechanicDao {

    private static MechanicDao instance;

    private EntityManager em;

    private MechanicDaoImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsqldb-file-persistence-unit");
        em = emf.createEntityManager();
    }

    public static MechanicDao getInstance(){
        if (instance == null){
            instance = new MechanicDaoImpl();
        }
        return instance;
    }



    @Override
    public void create(Mechanic mechanic) {
        try{
            em.getTransaction().begin();
            em.persist(mechanic);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public void update(Mechanic mechanic) {
        try{
            em.getTransaction().begin();
            em.merge(mechanic);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Mechanic mechanic) throws SQLException {
        try{
            em.getTransaction().begin();
            em.remove(mechanic);
            em.getTransaction().commit();
        }catch (RollbackException e){
            throw new SQLException();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<Mechanic> getAll() {
        TypedQuery<Mechanic> query = em.createNamedQuery("Mechanic.getAll", Mechanic.class);
        return query.getResultList();
    }
}
