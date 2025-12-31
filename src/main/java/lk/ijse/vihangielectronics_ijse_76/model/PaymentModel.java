package lk.ijse.vihangielectronics_ijse_76.model;

import lk.ijse.vihangielectronics_ijse_76.db.DBConnection;
import lk.ijse.vihangielectronics_ijse_76.dto.PaymentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {

    /* ================= SAVE PAYMENT ================= */
    public boolean savePayment(PaymentDto dto) throws SQLException {

        String sql = "INSERT INTO payment VALUES (?,?,?,?,?,?)";

        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, dto.getPaymentId());
        pst.setString(2, dto.getOrderId());
        pst.setDate(3, java.sql.Date.valueOf(dto.getDate()));
        pst.setInt(4, dto.getAmount());
        pst.setString(5, dto.getPaymentMethod());
        pst.setString(6, dto.getStatus());

        return pst.executeUpdate() > 0;
    }

    /* ================= UPDATE PAYMENT ================= */
    public boolean updatePayment(PaymentDto dto) throws SQLException {

        String sql = "UPDATE payment SET order_id=?, date=?, amount=?, payment_method=?, status=? WHERE payment_id=?";

        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, dto.getOrderId());
        pst.setDate(2, java.sql.Date.valueOf(dto.getDate()));
        pst.setInt(3, dto.getAmount());
        pst.setString(4, dto.getPaymentMethod());
        pst.setString(5, dto.getStatus());
        pst.setString(6, dto.getPaymentId());

        return pst.executeUpdate() > 0;
    }

    /* ================= DELETE PAYMENT ================= */
    public boolean deletePayment(String paymentId) throws SQLException {

        String sql = "DELETE FROM payment WHERE payment_id=?";

        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, paymentId);

        return pst.executeUpdate() > 0;
    }

    /* ================= SEARCH PAYMENT ================= */
    public PaymentDto searchPayment(String paymentId) throws SQLException {

        String sql = "SELECT * FROM payment WHERE payment_id=?";

        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, paymentId);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new PaymentDto(
                    rs.getString("payment_id"),
                    rs.getString("order_id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getInt("amount"),
                    rs.getString("payment_method"),
                    rs.getString("status")
            );
        }
        return null;
    }

    /* ================= GET ALL PAYMENTS ================= */
    public ArrayList<PaymentDto> getAllPayments() throws SQLException {

        ArrayList<PaymentDto> list = new ArrayList<>();
        String sql = "SELECT * FROM payment";

        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            list.add(
                    new PaymentDto(
                            rs.getString("payment_id"),
                            rs.getString("order_id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("amount"),
                            rs.getString("payment_method"),
                            rs.getString("status")
                    )
            );
        }
        return list;
    }

    /* ================= CHECK PAYMENT EXISTS ================= */
    public boolean isPaymentExists(String paymentId) throws SQLException {

        String sql = "SELECT payment_id FROM payment WHERE payment_id=?";

        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, paymentId);

        return pst.executeQuery().next();
    }
}
