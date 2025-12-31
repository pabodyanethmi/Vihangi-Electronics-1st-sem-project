package lk.ijse.vihangielectronics_ijse_76.model;

import lk.ijse.vihangielectronics_ijse_76.dto.StockDto;
import lk.ijse.vihangielectronics_ijse_76.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {


    public static boolean saveStock(StockDto dto) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Stock (productId, stockQuantity) VALUES (?,?)";
        return CrudUtil.execute(
                sql,
                dto.getProductId(),
                dto.getStockQuantity()
        );
    }


    public static boolean updateStock(StockDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Stock SET stockQuantity=? WHERE productId=?";
        return CrudUtil.execute(
                sql,
                dto.getStockQuantity(),
                dto.getProductId()
        );
    }


    public static boolean deleteStock(String productId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Stock WHERE productId=?";
        return CrudUtil.execute(sql, productId);
    }


    public static StockDto searchStock(String productId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Stock WHERE productId=?";
        ResultSet rs = CrudUtil.execute(sql, productId);

        if (rs.next()) {
            return new StockDto(
                    rs.getInt("stockId"),
                    rs.getString("productId"),
                    rs.getInt("stockQuantity")
            );
        }
        return null;
    }


    public static List<StockDto> getAllStocks() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Stock ORDER BY stockId DESC";
        ResultSet rs = CrudUtil.execute(sql);

        List<StockDto> stockList = new ArrayList<>();

        while (rs.next()) {
            StockDto dto = new StockDto(
                    rs.getInt("stockId"),
                    rs.getString("productId"),
                    rs.getInt("stockQuantity")
            );
            stockList.add(dto);
        }
        return stockList;
    }


    public String generateNextStockId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT stockId FROM Stock ORDER BY stockId DESC LIMIT 1");

        if (rs.next()) {
            int lastId = rs.getInt(1);
            return String.valueOf(lastId + 1);
        }
        return "1";
    }
}