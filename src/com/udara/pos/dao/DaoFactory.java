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

    public SuperDao getDao(DaoType daoType){
        switch (daoType){
            case USER:
                return new UserDaoImpl();
            case PRODUCT:
                return new ProductDaoImpl();
            case CUSTOMER:
                return new CustomerDaoImpl();
            default:
                return null;
        }
    }
}
