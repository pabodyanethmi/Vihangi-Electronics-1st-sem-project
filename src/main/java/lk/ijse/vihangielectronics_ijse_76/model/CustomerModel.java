package lk.ijse.vihangielectronics_ijse_76.model;

import lk.ijse.vihangielectronics_ijse_76.util.CrudUtil;
import lk.ijse.vihangielectronics_ijse_76.dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    // Save Customer
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer (customerId, name, contact, email, address) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                dto.getCustomerId(),
                dto.getName(),
                dto.getContact(),
                dto.getEmail(),
                dto.getAddress()
        );
    }

    // Update Customer
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customer SET name=?, contact=?, email=?, address=? WHERE customerId=?";
        return CrudUtil.execute(
                sql,
                dto.getName(),
                dto.getContact(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getCustomerId()
        );
    }

    // Delete Customer
    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE customerId=?";
        return CrudUtil.execute(sql, customerId);
    }

    // Get Last Customer ID
    public String getLastCustomerId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT customerId FROM Customer ORDER BY customerId DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getString("customerId");
        }
        return null;
    }

    // Get All Customers
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer";
        ResultSet rs = CrudUtil.execute(sql);

        ArrayList<CustomerDto> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new CustomerDto(
                    rs.getString("customerId"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getString("address")
            ));
        }
        return list;
    }

    // Search Customer by ID (Optional but Useful)
    public CustomerDto searchCustomer(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer WHERE customerId=?";
        ResultSet rs = CrudUtil.execute(sql, customerId);

        if (rs.next()) {
            return new CustomerDto(
                    rs.getString("customerId"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getString("address")
            );
        }
        return null;
    }
}
