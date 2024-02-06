package lk.ijse.gdse.back_end.dao;

import lk.ijse.gdse.back_end.bo.BOFactory;
import lk.ijse.gdse.back_end.bo.SuperBO;
import lk.ijse.gdse.back_end.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.back_end.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.back_end.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse.back_end.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse.back_end.dao.custom.impl.OrderDetailDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getDAOFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER_DAO, ITEM_DAO, ORDER_DAO, ORDER_DETAIL_DAO
    }

    public <T extends SuperDAO>T getDAO(DAOFactory.DAOTypes res){
        switch (res){
            case CUSTOMER_DAO:
                return (T) new CustomerDAOImpl();
            case ITEM_DAO:
                return (T) new ItemDAOImpl();
            case ORDER_DAO:
                return (T) new OrderDAOImpl();
            case ORDER_DETAIL_DAO:
                return (T) new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
