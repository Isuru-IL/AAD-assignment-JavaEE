package lk.ijse.gdse.back_end.bo.custom.impl;

import lk.ijse.gdse.back_end.bo.custom.CustomerBO;
import lk.ijse.gdse.back_end.dao.DAOFactory;
import lk.ijse.gdse.back_end.dao.custom.CustomerDAO;
import lk.ijse.gdse.back_end.dto.CustomerDTO;
import lk.ijse.gdse.back_end.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER_DAO);
    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        ArrayList<Customer> cusList = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> customerDTOList = new ArrayList<>();

        for (Customer customer : cusList) {
            customerDTOList.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            ));
        }
        //System.out.println("CustomerBOImpl getAllCustomers()");
        return customerDTOList;
    }

    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO dto) throws SQLException {
        return customerDAO.save(connection, new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()
        ));
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException {
        return customerDAO.update(connection, new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()
        ));
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) throws SQLException {
        return customerDAO.delete(connection, id);
    }

    @Override
    public String generateNewCusID() throws SQLException {
        return null;
    }

    @Override
    public CustomerDTO searchByCusID(Connection connection, String id) throws SQLException {
        Customer customer = customerDAO.search(connection, id);

        if (customer != null) {
            return new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            );
        }
        return null;
    }
}
