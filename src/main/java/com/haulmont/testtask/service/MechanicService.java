package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.MechanicDao;
import com.haulmont.testtask.dao.impl.MechanicDaoImpl;
import com.haulmont.testtask.model.Mechanic;

import java.sql.SQLException;
import java.util.List;

public class MechanicService {

    private static MechanicService instance;

    private MechanicDao mechanicDao;

    private MechanicService(){
        mechanicDao = MechanicDaoImpl.getInstance();
    }

    public static MechanicService getInstance(){
        if (instance == null){
            instance = new MechanicService();
        }
        return instance;
    }

    public void create(Mechanic mechanic) {
        mechanicDao.create(mechanic);
    }

    public void update(Mechanic mechanic) {
        mechanicDao.update(mechanic);
    }

    public void delete(Mechanic mechanic) throws SQLException {
        mechanicDao.delete(mechanic);
    }

    public List<Mechanic> getAll() {
        return mechanicDao.getAll();
    }

}
