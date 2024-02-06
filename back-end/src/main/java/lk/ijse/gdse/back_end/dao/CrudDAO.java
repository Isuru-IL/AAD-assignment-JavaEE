package lk.ijse.gdse.back_end.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T, ID> extends SuperDAO{
    ArrayList<T> getAll(Connection conPool) throws SQLException;

    boolean save(Connection connection, T entity) throws SQLException;

    boolean update(Connection connection, T entity) throws SQLException;

    boolean delete(Connection connection, ID id) throws SQLException;

    //boolean exist(ID id) throws SQLException, ClassNotFoundException;

    String generateNewID(Connection connection) throws SQLException;

    T search(Connection connection, ID id) throws SQLException;
}
