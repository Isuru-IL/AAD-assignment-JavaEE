package lk.ijse.gdse.back_end.dao.custom;

import lk.ijse.gdse.back_end.dao.CrudDAO;
import lk.ijse.gdse.back_end.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item, String> {
    boolean updateItemQty(Connection connection, Item item) throws SQLException;
}
