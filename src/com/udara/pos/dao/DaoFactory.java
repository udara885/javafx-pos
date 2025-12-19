package com.udara.pos.dao;

import com.udara.pos.dao.custom.impl.CustomerDaoImpl;
import com.udara.pos.dao.custom.impl.ProductDaoImpl;
import com.udara.pos.dao.custom.impl.UserDaoImpl;
import com.udara.pos.enums.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return daoFactory == null ? daoFactory = new DaoFactory() : daoFactory;
    }

    public <T> T getDao(DaoType daoType){
        switch (daoType){
            case USER:
                return (T) new UserDaoImpl();
            case PRODUCT:
                return (T) new ProductDaoImpl();
            case CUSTOMER:
                return (T) new CustomerDaoImpl();
            default:
                return null;
        }
    }
}
