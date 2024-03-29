package lk.ijse.gdse.back_end.bo.custom;

import lk.ijse.gdse.back_end.bo.SuperBO;
import lk.ijse.gdse.back_end.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
    ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException;

    ArrayList<OrderDTO> getOrdersByOrderId(Connection connection, String orderId) throws SQLException;

    ArrayList<OrderDTO> getOrdersByCustomerId(Connection connection, String cusId) throws SQLException;
}
