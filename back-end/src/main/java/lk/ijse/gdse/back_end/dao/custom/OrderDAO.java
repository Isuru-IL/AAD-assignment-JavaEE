package lk.ijse.gdse.back_end.dao.custom;

import lk.ijse.gdse.back_end.dao.CrudDAO;
import lk.ijse.gdse.back_end.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Order, String> {
    ArrayList<Order> getOrdersByOrderId(Connection connection, String orderId) throws SQLException;

    ArrayList<Order> getOrdersByCustomerId(Connection connection, String cusId) throws SQLException;
}
