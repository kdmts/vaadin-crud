package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientDao {
    void create(Client client);
    //void read();
    void update(Client client);
    void delete(Client client) throws SQLException;
    List<Client> getAll();
}
