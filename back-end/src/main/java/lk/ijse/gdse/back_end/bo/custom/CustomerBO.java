package lk.ijse.gdse.back_end.bo.custom;

import lk.ijse.gdse.back_end.bo.SuperBO;
import lk.ijse.gdse.back_end.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomers(Connection conPool) throws SQLException;

    boolean saveCustomer(Connection conPool, CustomerDTO dto) throws SQLException;

    boolean updateCustomer(Connection conPool, CustomerDTO dto) throws SQLException;

    boolean deleteCustomer(Connection conPool, String id) throws SQLException;

    String generateNewCusID() throws SQLException;

    CustomerDTO searchByCusID(Connection connection, String id) throws SQLException;
}
