package lk.ijse.gdse.back_end.bo.custom.impl;

import lk.ijse.gdse.back_end.bo.custom.PlaceOrderBO;
import lk.ijse.gdse.back_end.dao.DAOFactory;
import lk.ijse.gdse.back_end.dao.custom.ItemDAO;
import lk.ijse.gdse.back_end.dao.custom.OrderDAO;
import lk.ijse.gdse.back_end.dao.custom.OrderDetailDAO;
import lk.ijse.gdse.back_end.dto.OrderDTO;
import lk.ijse.gdse.back_end.dto.OrderDetailDTO;
import lk.ijse.gdse.back_end.entity.Item;
import lk.ijse.gdse.back_end.entity.Order;
import lk.ijse.gdse.back_end.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDAO orderDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DAO);
    OrderDetailDAO orderDetailDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL_DAO);
    ItemDAO itemDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM_DAO);

    @Override
    public String getLastOrderId(Connection connection) throws SQLException {
        return orderDAO.generateNewID(connection);
    }

    @Override
    public boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException {
        try {
            connection.setAutoCommit(false);
            boolean isOrderSaved = orderDAO.save(connection, new Order(dto.getOrderId(), dto.getCusId(), dto.getTotal(), dto.getOrderDate()));
            if (isOrderSaved) {
                boolean isOrderDetailSaved = saveOrderDetails(connection, dto.getOrderDetailDTOList());
                if (isOrderDetailSaved){
                    boolean isItemUpdated = updateItemQty(connection, dto.getOrderDetailDTOList());
                    if (isItemUpdated) {
                        connection.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            System.out.println("place order= "+er);
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private boolean updateItemQty(Connection connection, ArrayList<OrderDetailDTO> orderDetailDTOList) throws SQLException {
        for (OrderDetailDTO detailDTO : orderDetailDTOList) {

            Item item = new Item();
            item.setItemCode(detailDTO.getItemCode());
            item.setQtyOnHand(detailDTO.getQty());

            if (!itemDAO.updateItemQty(connection, item)){
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(Connection connection, ArrayList<OrderDetailDTO> orderDetailDTOList) throws SQLException {
        for (OrderDetailDTO detailDTO : orderDetailDTOList) {
            if (!orderDetailDAO.save(connection, new OrderDetail(detailDTO.getOrderId(), detailDTO.getItemCode(), detailDTO.getQty()))){
                return false;
            }
        }
        return true;
    }
}
