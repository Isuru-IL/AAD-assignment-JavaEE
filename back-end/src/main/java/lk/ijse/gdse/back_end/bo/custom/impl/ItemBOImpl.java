package lk.ijse.gdse.back_end.bo.custom.impl;

import lk.ijse.gdse.back_end.bo.custom.ItemBO;
import lk.ijse.gdse.back_end.dao.DAOFactory;
import lk.ijse.gdse.back_end.dao.custom.ItemDAO;
import lk.ijse.gdse.back_end.dto.CustomerDTO;
import lk.ijse.gdse.back_end.dto.ItemDTO;
import lk.ijse.gdse.back_end.entity.Customer;
import lk.ijse.gdse.back_end.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM_DAO);
    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException {
        ArrayList<Item> cusList = itemDAO.getAll(connection);
        ArrayList<ItemDTO> itemDTOList = new ArrayList<>();

        for (Item item : cusList) {
            itemDTOList.add(new ItemDTO(
                    item.getItemCode(),
                    item.getItemName(),
                    item.getQtyOnHand(),
                    item.getUnitPrice()
            ));
        }
        return itemDTOList;
    }

    @Override
    public boolean saveItem(Connection connection, ItemDTO dto) throws SQLException {
        return itemDAO.save(connection, new Item(
                dto.getItemCode(),
                dto.getItemName(),
                dto.getQtyOnHand(),
                dto.getUnitPrice()
        ));
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO dto) throws SQLException {
        return itemDAO.update(connection, new Item(
                dto.getItemCode(),
                dto.getItemName(),
                dto.getQtyOnHand(),
                dto.getUnitPrice()
        ));
    }

    @Override
    public boolean deleteItem(Connection connection, String id) throws SQLException {
        return itemDAO.delete(connection, id);
    }

    @Override
    public String generateNewCusID() throws SQLException {
        return null;
    }

    @Override
    public ItemDTO searchByItemCode(Connection connection, String id) throws SQLException {
        Item item = itemDAO.search(connection, id);

        if (item != null) {
            return new ItemDTO(
                    item.getItemCode(),
                    item.getItemName(),
                    item.getQtyOnHand(),
                    item.getUnitPrice()
            );
        }
        return null;
    }
}
