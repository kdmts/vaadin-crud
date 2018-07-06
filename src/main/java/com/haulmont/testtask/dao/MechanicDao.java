package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Mechanic;

import java.sql.SQLException;
import java.util.List;

public interface MechanicDao {
    void create(Mechanic mechanic);
    //void read();
    void update(Mechanic mechanic);
    void delete(Mechanic mechanic) throws SQLException;
    List<Mechanic> getAll();
}
