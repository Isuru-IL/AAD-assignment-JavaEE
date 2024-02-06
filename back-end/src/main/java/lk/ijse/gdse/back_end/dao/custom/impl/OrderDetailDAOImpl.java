package lk.ijse.gdse.back_end.dao.custom.impl;

import lk.ijse.gdse.back_end.dao.custom.OrderDetailDAO;
import lk.ijse.gdse.back_end.dao.custom.impl.util.CrudUtil;
import lk.ijse.gdse.back_end.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public ArrayList<OrderDetail> getAll(Connection conPool) throws SQLException {
        return null;
    }

    @Override
    public boolean save(Connection connection, OrderDetail entity) throws SQLException {
        String sql = "INSERT INTO orderDetail(orderId, itemCode, qty)"+
                "VALUES(?, ?, ?)";
        return CrudUtil.execute(connection, sql,
                entity.getOrderId(),
                entity.getItemCode(),
                entity.getQty());
    }

    @Override
    public boolean update(Connection connection, OrderDetail entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException {
        return false;
    }

    @Override
    public String generateNewID(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public OrderDetail search(Connection connection, String s) throws SQLException {
        return null;
    }
}
