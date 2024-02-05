package lk.ijse.gdse.back_end.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.back_end.bo.BOFactory;
import lk.ijse.gdse.back_end.bo.custom.ItemBO;
import lk.ijse.gdse.back_end.dto.CustomerDTO;
import lk.ijse.gdse.back_end.dto.ItemDTO;

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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

@WebServlet(name = "itemServlet", urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    ItemBO itemBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM_BO);
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
            resp.setContentType("application/json"); //set the MIME type of the content of the response (Thus, add response header called "Content-Type")
            try (Connection connection = conPool.getConnection()){
                ArrayList<ItemDTO> customerList = itemBO.getAllItems(connection);


                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(customerList,resp.getWriter());

            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Item doGet");
            }
        } else if (function.equals("GetByCode")){
            String itemCode = req.getParameter("code");
            try (Connection connection = conPool.getConnection()){
                ItemDTO itemDTO = itemBO.searchByItemCode(connection, itemCode);

                Jsonb jsonb = JsonbBuilder.create();
                String jsonObj = jsonb.toJson(itemDTO);
                resp.getWriter().write(jsonObj);
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "item searchByCode");
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

        try (Connection connection = conPool.getConnection()){

            if(itemDTO.getItemCode()==null || !itemDTO.getItemCode().matches("^(I00-)[0-9]{3}$")){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Code is empty or invalid");
                return;
            } else if (itemDTO.getItemName() == null || !itemDTO.getItemName().matches("^[A-Za-z ]{5,}$")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is empty or invalid");
                return;
            } else if (itemDTO.getQtyOnHand() == null || !itemDTO.getQtyOnHand().toString().matches("^\\d{1,4}$")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Qty is empty or invalid");
                return;
            } else if (itemDTO.getUnitPrice() == null || !itemDTO.getUnitPrice().toString().matches("\\d+(\\.\\d{1,2})")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Price is empty or invalid");
                return;
            }

            boolean isSaved = itemBO.saveItem(connection, itemDTO);
            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED, "Item saved successfully");
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Fail to save the Item");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Item is already exits!");
        } catch (Exception throwables) {
            throwables.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong in Item doPost!");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

        if(itemDTO.getItemCode()==null || !itemDTO.getItemCode().matches("^(I00-)[0-9]{3}$")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Code is empty or invalid");
            return;
        } else if (itemDTO.getItemName() == null || !itemDTO.getItemName().matches("^[A-Za-z ]{5,}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is empty or invalid");
            return;
        } else if (itemDTO.getQtyOnHand() == null || !itemDTO.getQtyOnHand().toString().matches("^\\d{1,4}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Qty is empty or invalid");
            return;
        } else if (itemDTO.getUnitPrice() == null || !itemDTO.getUnitPrice().toString().matches("\\d+(\\.\\d{1,2})")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Price is empty or invalid");
            return;
        }

        try (Connection connection = conPool.getConnection()){
            boolean isUpdated = itemBO.updateItem(connection, itemDTO);
            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Fail to update the Item");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            //e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Item already exits!");
        } catch (Exception throwables) {
            //throwables.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong Item doPut!");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong Item doPut!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        try (Connection connection = conPool.getConnection()){
            boolean isDeleted = itemBO.deleteItem(connection, code);
            if (isDeleted) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete the item");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete the item"+e.getMessage());
        }
    }
}
