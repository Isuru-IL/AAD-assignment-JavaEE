package lk.ijse.gdse.back_end.dao.custom.impl;

import lk.ijse.gdse.back_end.dao.custom.ItemDAO;
import lk.ijse.gdse.back_end.dao.custom.impl.util.CrudUtil;
import lk.ijse.gdse.back_end.entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM item";
        ResultSet rst = CrudUtil.execute(connection, sql);

        ArrayList<Item> items = new ArrayList<>();

        while (rst.next()) {
            items.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));
        }
        return items;
    }

    @Override
    public boolean save(Connection connection, Item entity) throws SQLException {
        String sql ="INSERT INTO item(itemCode, itemName, qtyOnHand, unitPrice)"+
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(connection,
                sql,
                entity.getItemCode(),
                entity.getItemName(),
                entity.getQtyOnHand(),
                entity.getUnitPrice());
    }

    @Override
    public boolean update(Connection connection, Item entity) throws SQLException {
        String sql = "UPDATE item SET itemName=?, qtyOnHand=?, unitPrice=? WHERE itemCode=?";
        return CrudUtil.execute(connection, sql,
                entity.getItemName(),
                entity.getQtyOnHand(),
                entity.getUnitPrice(),
                entity.getItemCode());
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        String sql = "DELETE FROM item WHERE itemCode=?";
        return CrudUtil.execute(connection, sql, id);
    }

    @Override
    public String generateNewID(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Item search(Connection connection, String id) throws SQLException {
        String sql = "SELECT * FROM item WHERE itemCode=?";
        ResultSet rst = CrudUtil.execute(connection, sql, id);
        if (rst.next()){
            return new Item(
                 rst.getString(1),
                 rst.getString(2),
                 rst.getInt(3),
                 rst.getDouble(4)
            );
        }
        return null;
    }

    @Override
    public boolean updateItemQty(Connection connection, Item item) throws SQLException {
        String sql = "UPDATE item SET qtyOnHand = (qtyOnHand - ?) WHERE itemCode = ?";
        return CrudUtil.execute(connection, sql, item.getQtyOnHand(), item.getItemCode());
    }
}
