package lk.ijse.vihangielectronics_ijse_76.model;

//import lk.ijse.vihangielectronics_ijse_76.dto.ProductDto;

//import java.util.ArrayList;

import lk.ijse.vihangielectronics_ijse_76.dto.CustomerDto;
import lk.ijse.vihangielectronics_ijse_76.dto.ProductDto;
import lk.ijse.vihangielectronics_ijse_76.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {
    public static boolean saveProduct(ProductDto dto) throws SQLException {
        String sql = "INSERT INTO product (productId,name,qty,pricePerUnit) VALUES (?,?,?,?)";
        return CrudUtil.execute(
                sql,
                dto.getProductId(),
                dto.getName(),
                dto.getQuantity(),
                dto.getPricePerUnit()

        );
    }

    public boolean updateproduct(ProductDto dto) throws SQLException {
        String sql = "UPDATE product SET name=?,qty=?,pricePerUnit=? WHERE productId=?";
        return CrudUtil.execute(
                sql,

                dto.getName(),
                dto.getQuantity(),
                dto.getPricePerUnit(),
                dto.getProductId()

        );
    }

    public boolean deleteProduct(String productId) throws SQLException {
        boolean result =
                CrudUtil.execute(
                        "DELETE FROM product WHERE productId=?",
                        productId
                );

        return result;
    }

    public List<ProductDto> getProducts() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM product ORDER BY productId DESC");

        List<ProductDto> productDtoList = new ArrayList<>();

        while (rs.next()) {
            ProductDto productDto = new ProductDto(
                    rs.getString("productId"),
                    rs.getString("name"),
                    rs.getString("qty"),
                    rs.getString("pricePerUnit")

            );
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    public ProductDto searchProducts(String id) throws SQLException {
        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM product WHERE productId=?",
                        id
                );

        if (rs.next()) {
            String productId = rs.getString("productId");
            String name = rs.getString("name");
            String qty = rs.getString("qty");
            double pricePerUnit = rs.getDouble("pricePerUnit");

            return new ProductDto(productId, name, qty, pricePerUnit);
        }
        return null;
    }

    public String generateNextProductId() throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT productId FROM product ORDER BY productId DESC LIMIT 1"
        );

        if (rs.next()) {
            String lastId = rs.getString(1); // C005
            int number = Integer.parseInt(lastId.substring(1));
            number++;
            return String.format("P%03d", number);
        }
        return "P001";
    }   }