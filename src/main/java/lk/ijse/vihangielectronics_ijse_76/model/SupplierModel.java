package lk.ijse.vihangielectronics_ijse_76.model;

import lk.ijse.vihangielectronics_ijse_76.util.CrudUtil;
import lk.ijse.vihangielectronics_ijse_76.dto.SupplierDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    // Save Supplier
    public boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Supplier (supplierId, name, contact, email, address) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                dto.getSupplierId(),
                dto.getName(),
                dto.getContact(),
                dto.getEmail(),
                dto.getAddress()
        );
    }

    // Update Supplier
    public boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Supplier SET name=?, contact=?, email=?, address=? WHERE supplierId=?";
        return CrudUtil.execute(
                sql,
                dto.getName(),
                dto.getContact(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getSupplierId()
        );
    }

    // Delete Supplier
    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Supplier WHERE supplierId=?";
        return CrudUtil.execute(sql, supplierId);
    }

    // Search Supplier by ID
    public SupplierDto searchSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Supplier WHERE supplierId=?";
        ResultSet rs = CrudUtil.execute(sql, supplierId);

        if (rs.next()) {
            return new SupplierDto(
                    rs.getString("supplierId"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getString("address")
            );
        }
        return null;
    }

    // Get All Suppliers
    public List<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Supplier";
        ResultSet rs = CrudUtil.execute(sql);

        List<SupplierDto> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new SupplierDto(
                    rs.getString("supplierId"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getString("address")
            ));
        }
        return list;
    }

    // Get Last Supplier ID (for auto ID generation)
    public String getLastSupplierId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT supplierId FROM Supplier ORDER BY supplierId DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getString("supplierId");
        }
        return null;
    }
}
