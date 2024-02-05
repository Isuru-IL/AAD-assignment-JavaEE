package lk.ijse.gdse.back_end.bo.custom;

import lk.ijse.gdse.back_end.bo.SuperBO;
import lk.ijse.gdse.back_end.dto.CustomerDTO;
import lk.ijse.gdse.back_end.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException;

    boolean saveItem(Connection connection, ItemDTO dto) throws SQLException;

    boolean updateItem(Connection connection, ItemDTO dto) throws SQLException;

    boolean deleteItem(Connection connection, String id) throws SQLException;

    String generateNewCusID() throws SQLException;

    ItemDTO searchByItemCode(Connection connection, String id) throws SQLException;
}
