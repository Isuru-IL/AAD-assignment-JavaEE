package lk.ijse.gdse.back_end.bo.custom;

import lk.ijse.gdse.back_end.bo.SuperBO;
import lk.ijse.gdse.back_end.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {

    String getLastOrderId(Connection connection) throws SQLException;

    boolean placeOrder(Connection connection, OrderDTO orderDTO) throws SQLException;
}
