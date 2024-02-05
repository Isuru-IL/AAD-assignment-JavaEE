package lk.ijse.gdse.back_end.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.back_end.bo.BOFactory;
import lk.ijse.gdse.back_end.bo.custom.CustomerBO;
import lk.ijse.gdse.back_end.dto.CustomerDTO;

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

@WebServlet(name = "customerServlet", urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    private CustomerBO customerBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER_BO);

    DataSource conPool;
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ic = new InitialContext();
            conPool = (DataSource) ic.lookup("java:/comp/env/jdbc/pos");
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
                ArrayList<CustomerDTO> customerList = customerBO.getAllCustomers(connection);


                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(customerList,resp.getWriter());

            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (IOException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else if (function.equals("GetByID")){
            String cusId = req.getParameter("id");
            try (Connection connection = conPool.getConnection()){
                CustomerDTO customerDTO = customerBO.searchByCusID(connection, cusId);

                Jsonb jsonb = JsonbBuilder.create();
                String jsonObj = jsonb.toJson(customerDTO);
                resp.getWriter().write(jsonObj);
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                System.out.println("customerSearchByID "+e.getMessage());
            } catch (IOException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                System.out.println("customerSearchByID "+e.getMessage());
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                System.out.println("customerSearchByID "+e.getMessage());
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        if(customerDTO.getId()==null || !customerDTO.getId().matches("^(C00-)[0-9]{3}$")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is empty or invalid");
            return;
        } else if (customerDTO.getName() == null || !customerDTO.getName().matches("^[A-Za-z ]{5,}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is empty or invalid");
            return;
        } else if (customerDTO.getAddress() == null || !customerDTO.getAddress().matches("^[A-Za-z0-9 ]{5,}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address is empty or invalid");
            return;
        }

        try (Connection connection = conPool.getConnection()){
            boolean isSaved = customerBO.saveCustomer(connection, customerDTO);
            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Fail to save the customer");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Customer already exits!");
        } catch (Exception throwables) {
            //throwables.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong in customer doPost!");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        if(customerDTO.getId()==null || !customerDTO.getId().matches("^(C00-)[0-9]{3}$")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is empty or invalid");
            return;
        } else if (customerDTO.getName() == null || !customerDTO.getName().matches("^[A-Za-z ]{5,}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is empty or invalid");
            return;
        } else if (customerDTO.getAddress() == null || !customerDTO.getAddress().matches("^[A-Za-z0-9 ]{5,}$")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address is empty or invalid");
            return;
        }

        try (Connection connection = conPool.getConnection()){
            boolean isUpdated = customerBO.updateCustomer(connection, customerDTO);
            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Fail to update the customer");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            //e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Customer already exits!");
        } catch (Exception throwables) {
            //throwables.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong customer doPut!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try (Connection connection = conPool.getConnection()){
            boolean isDeleted = customerBO.deleteCustomer(connection, id);
            if (isDeleted) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete the customer");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete the customer");
        }
    }
}
