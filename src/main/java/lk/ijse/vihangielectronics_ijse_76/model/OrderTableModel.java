/*package lk.ijse.vihangielectronics_ijse_76.model;

//import lk.ijse.vihangielectronics_ijse_76.dto.OrderTableDto;

//import java.util.ArrayList;
public class OrderTableModel {
    //public boolean orderTableModelSave(OrderTableDto orderTablelDto) {}
    //public boolean orderTableModelUpdate(OrderTableDto  orderTableDto) {}
    //public boolean orderDetailsDelete(OrderTableId orderDetailsId) {}
    //public ArrayList<OrderTableDto> orderDetailsGetAll(){}
}*/

package lk.ijse.vihangielectronics_ijse_76.model;

import lk.ijse.vihangielectronics_ijse_76.db.DBConnection;
import lk.ijse.vihangielectronics_ijse_76.dto.OrderTableDto;
import lk.ijse.vihangielectronics_ijse_76.dto.OrderTableDto;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrderTableModel {

    /* ================= SAVE ================= */
    public boolean saveOrder(OrderTableDto dto) throws SQLException {
        String sql = "INSERT INTO OrderTable (orderId, customerId, date, time, description, totalAmount, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(sql);

        pstm.setString(1, dto.getOrderId());
        pstm.setString(2, dto.getCustomerId());
        pstm.setDate(3, Date.valueOf(dto.getDate()));
        pstm.setTime(4, Time.valueOf(dto.getTime()));
        pstm.setString(5, dto.getDescription());
        pstm.setDouble(6, dto.getTotalAmount());
        pstm.setString(7, dto.getStatus());

        return pstm.executeUpdate() > 0;
    }

    /* ================= UPDATE ================= */
    public boolean updateOrder(OrderTableDto dto) throws SQLException {
        String sql = "UPDATE OrderTable SET customerId=?, date=?, time=?, description=?, totalAmount=?, status=? " +
                "WHERE orderId=?";

        PreparedStatement pstm = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(sql);

        pstm.setString(1, dto.getCustomerId());
        pstm.setDate(2, Date.valueOf(dto.getDate()));
        pstm.setTime(3, Time.valueOf(dto.getTime()));
        pstm.setString(4, dto.getDescription());
        pstm.setDouble(5, dto.getTotalAmount());
        pstm.setString(6, dto.getStatus());
        pstm.setString(7, dto.getOrderId());

        return pstm.executeUpdate() > 0;
    }

    /* ================= DELETE ================= */
    public boolean deleteOrder(String orderId) throws SQLException {
        String sql = "DELETE FROM OrderTable WHERE orderId=?";

        PreparedStatement pstm = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(sql);

        pstm.setString(1, orderId);

        return pstm.executeUpdate() > 0;
    }

    /* ================= SEARCH ================= */
    public OrderTableDto searchOrder(String orderId) throws SQLException {
        String sql = "SELECT * FROM OrderTable WHERE orderId=?";

        PreparedStatement pstm = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(sql);

        pstm.setString(1, orderId);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return new OrderTableDto(
                    rs.getString("orderId"),
                    rs.getString("customerId"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("description"),
                    rs.getDouble("totalAmount"),
                    rs.getString("status")
            );
        }
        return null;
    }

    /* ================= GET ALL ================= */
    public List<OrderTableDto> getAllOrders() throws SQLException {
        List<OrderTableDto> list = new ArrayList<>();

        String sql = "SELECT * FROM OrderTable ORDER BY orderId DESC";
        Statement stm = DBConnection.getInstance()
                .getConnection()
                .createStatement();

        ResultSet rs = stm.executeQuery(sql);

        while (rs.next()) {
            list.add(new OrderTableDto(
                    rs.getString("orderId"),
                    rs.getString("customerId"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("description"),
                    rs.getDouble("totalAmount"),
                    rs.getString("status")
            ));
        }
        return list;
    }

    /* ================= AUTO ID ================= */
    public String getNextOrderId() throws SQLException {
        String sql = "SELECT orderId FROM OrderTable ORDER BY orderId DESC LIMIT 1";
        Statement stm = DBConnection.getInstance()
                .getConnection()
                .createStatement();

        ResultSet rs = stm.executeQuery(sql);

        if (rs.next()) {
            String lastId = rs.getString(1); // O001
            int num = Integer.parseInt(lastId.substring(1));
            return String.format("O%03d", num + 1);
        }
        return "O001";
    }
}



