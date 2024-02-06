package lk.ijse.gdse.back_end.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.back_end.bo.BOFactory;
import lk.ijse.gdse.back_end.bo.custom.PlaceOrderBO;
import lk.ijse.gdse.back_end.dto.OrderDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "orderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    PlaceOrderBO placeOrderBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACE_ORDER_BO);

    DataSource conPool;
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ic = new InitialContext();
            conPool = (DataSource) ic.lookup("java:/comp/env/jdbc/javaee_assignment");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = conPool.getConnection()){
            String lastOrderId = placeOrderBO.getLastOrderId(connection);
            resp.getWriter().write(lastOrderId);
        } catch (SQLException throwables) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getLast orderId");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);

        if(orderDTO.getCusId()==null || !orderDTO.getCusId().matches("^(C00-)[0-9]{3}$")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "cusId is empty or invalid");
            return;
        } else if (orderDTO.getOrderId() == null || !orderDTO.getOrderId().matches("^(OD-)[0-9]{4}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "orderId is empty or invalid");
            return;
        } else if (orderDTO.getTotal() == null || !orderDTO.getTotal().toString().matches("\\d+(\\.\\d{1,2})")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Total is empty or invalid");
            return;
        } else if(orderDTO.getOrderDetailDTOList().size()==0){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "order details list is empty");
            return;
        }

        try (Connection connection = conPool.getConnection()){
            boolean isPlaced = placeOrderBO.placeOrder(connection, orderDTO);
            if (isPlaced) {
                resp.setStatus(HttpServletResponse.SC_CREATED, "Order placed successfully");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to order placed");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SQLException");
        }
    }
}
