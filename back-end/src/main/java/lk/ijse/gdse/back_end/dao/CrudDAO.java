package lk.ijse.gdse.back_end.dao;

import lk.ijse.gdse.back_end.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T, ID> extends SuperDAO{
    ArrayList<T> getAll(Connection conPool) throws SQLException;

    boolean save(Connection conPool, Customer entity) throws SQLException;

    boolean update(Connection conPool, Customer entity) throws SQLException;

    boolean delete(Connection conPool, String id) throws SQLException;

    //boolean exist(ID id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException;

    T search(Connection connection, ID id) throws SQLException;
}
