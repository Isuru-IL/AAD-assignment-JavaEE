package lk.ijse.gdse.back_end.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.back_end.bo.BOFactory;
import lk.ijse.gdse.back_end.bo.custom.OrderDetailBO;
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
import java.util.ArrayList;

@WebServlet(name = "orderDetailServlet", urlPatterns = "/orderDetail")
public class OrderDetailServlet extends HttpServlet {
    OrderDetailBO orderDetailBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER_DETAIL_BO);
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
        String function = req.getParameter("function");

        if (function.equals("GetAll")){
            resp.setContentType("application/json");
            try (Connection connection = conPool.getConnection()) {
                ArrayList<OrderDTO> orderDTOList = orderDetailBO.getAllOrders(connection);

                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(orderDTOList,resp.getWriter());

            } catch (SQLException throwables) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "OrderDetail doGet");
            }
        }
    }
}
