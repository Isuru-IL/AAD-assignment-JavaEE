package lk.ijse.gdse.back_end.bo.custom.impl;

import lk.ijse.gdse.back_end.bo.custom.OrderDetailBO;
import lk.ijse.gdse.back_end.dao.DAOFactory;
import lk.ijse.gdse.back_end.dao.custom.OrderDAO;
import lk.ijse.gdse.back_end.dto.OrderDTO;
import lk.ijse.gdse.back_end.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {
    OrderDAO orderDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DAO);

    @Override
    public ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException {
        ArrayList<Order> orders = orderDAO.getAll(connection);
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : orders) {
            orderDTOS.add(new OrderDTO(
                    order.getOrderId(),
                    order.getCusId(),
                    order.getTotal(),
                    order.getDate()
            ));
        }
        return orderDTOS;
    }

    @Override
    public ArrayList<OrderDTO> getOrdersByOrderId(Connection connection, String orderId) throws SQLException {
        ArrayList<Order> orders = orderDAO.getOrdersByOrderId(connection, orderId);
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : orders) {
            orderDTOS.add(new OrderDTO(
                    order.getOrderId(),
                    order.getCusId(),
                    order.getTotal(),
                    order.getDate()
            ));
        }
        return orderDTOS;
    }

    @Override
    public ArrayList<OrderDTO> getOrdersByCustomerId(Connection connection, String cusId) throws SQLException {
        ArrayList<Order> orders = orderDAO.getOrdersByCustomerId(connection, cusId);
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : orders) {
            orderDTOS.add(new OrderDTO(
                    order.getOrderId(),
                    order.getCusId(),
                    order.getTotal(),
                    order.getDate()
            ));
        }
        return orderDTOS;
    }
}
