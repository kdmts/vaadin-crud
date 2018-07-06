package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.ClientDao;
import com.haulmont.testtask.model.Client;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

    private static ClientDao instance;

    private EntityManager em;

    private ClientDaoImpl(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsqldb-file-persistence-unit");
        em = emf.createEntityManager();
    }

    public static ClientDao getInstance(){
        if (instance == null){
            instance = new ClientDaoImpl();
        }
        return instance;
    }

    @Override
    public void create(Client client) {
        try{
            em.getTransaction().begin();
            em.persist(client);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public void update(Client client) {
        try{
            em.getTransaction().begin();
            em.merge(client);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Client client) throws SQLException {
        try{
            em.getTransaction().begin();
            em.remove(client);
            em.getTransaction().commit();
        } catch (RollbackException e){
            throw new SQLException();
        }catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<Client> getAll() {
        TypedQuery<Client> query = em.createNamedQuery("Client.getAll", Client.class);
        return query.getResultList();
    }
}
