package lk.ijse.gdse.back_end.dao.custom.impl;

import lk.ijse.gdse.back_end.dao.custom.CustomerDAO;
import lk.ijse.gdse.back_end.dao.custom.impl.util.CrudUtil;
import lk.ijse.gdse.back_end.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException {
        //System.out.println("CustomerDAOImpl getAll()");
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM customer");
        ResultSet rst = stm.executeQuery();

        ArrayList<Customer> customerList = new ArrayList<>();

        while (rst.next()) {
            String id = rst.getString("id");
            String name = rst.getString("name");
            String address = rst.getString("address");
            Double salary = rst.getDouble("salary");

            customerList.add(new Customer(id, name, address, salary));
        }
        return customerList;
    }

    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException {
        String sql ="INSERT INTO customer(id, name, address, salary)"+
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(connection, sql,
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getSalary()
        );
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException {
        String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        return CrudUtil.execute(connection, sql,
                entity.getName(),
                entity.getAddress(),
                entity.getSalary(),
                entity.getId());
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE id=?";
        return CrudUtil.execute(connection, sql, id);
    }

    @Override
    public String generateNewID(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Customer search(Connection connection, String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id=?";
        ResultSet resultSet = CrudUtil.execute(connection, sql, id);
        if (resultSet.next()){
            return new Customer(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getDouble("salary")
            );
        }
        return null;
    }
}
