package lk.ijse.gdse.back_end.dao.custom.impl;

import lk.ijse.gdse.back_end.dao.custom.OrderDAO;
import lk.ijse.gdse.back_end.dao.custom.impl.util.CrudUtil;
import lk.ijse.gdse.back_end.entity.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM orders";
        ResultSet rst = CrudUtil.execute(connection, sql);
        ArrayList<Order> orders = new ArrayList<>();

        while (rst.next()){
            orders.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4)
            ));
        }
        return orders;
    }

    @Override
    public boolean save(Connection connection, Order entity) throws SQLException {
        String sql = "INSERT INTO orders(orderId, cutId, total, date)"+
                "VALUES(?, ?, ?, ?)";
        LocalDate date = LocalDate.now();
        return CrudUtil.execute(connection, sql,
                entity.getOrderId(),
                entity.getCusId(),
                entity.getTotal(),
                date);
    }

    @Override
    public boolean update(Connection connection, Order entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException {
        return false;
    }

    @Override
    public String generateNewID(Connection connection) throws SQLException {
        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        ResultSet rst = CrudUtil.execute(connection, sql);

        if (rst.next()){
            return rst.getString(1);
        }
        return "unDefined";
    }

    @Override
    public Order search(Connection connection, String s) throws SQLException {
        return null;
    }
}
