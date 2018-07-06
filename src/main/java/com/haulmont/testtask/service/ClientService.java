package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.ClientDao;
import com.haulmont.testtask.dao.impl.ClientDaoImpl;
import com.haulmont.testtask.model.Client;

import java.sql.SQLException;
import java.util.List;

public class ClientService {

    private static ClientService instance;

    private ClientDao clientDao;

    private ClientService(){
        clientDao = ClientDaoImpl.getInstance();
    }

    public static ClientService getInstance(){
        if (instance == null){
            instance = new ClientService();
        }
        return instance;
    }

    public void create(Client client) {
        clientDao.create(client);
    }

    public void update(Client client) {
        clientDao.update(client);
    }

    public void delete(Client client) throws SQLException {
        clientDao.delete(client);
    }

    public List<Client> getAll() {
        return clientDao.getAll();
    }

}
