package lk.ijse.vihangielectronics_ijse_76.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.vihangielectronics_ijse_76.db.DBConnection;
import lk.ijse.vihangielectronics_ijse_76.dto.tm.QuotationTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QuotationModel {

    public static ObservableList<String> getAllCustomerIds() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            Connection con = DBConnection.getInstance().getConnection();
            ResultSet rs = con.prepareStatement("SELECT customerId FROM customer").executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<String> getAllProductIds() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            Connection con = DBConnection.getInstance().getConnection();
            ResultSet rs = con.prepareStatement("SELECT productId FROM product").executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static double getUnitPrice(String productId) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT pricePerUnit FROM product WHERE productId=?");
            pst.setString(1, productId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getQtyOnHand(String productId) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT qty FROM product WHERE productId=?");
            pst.setString(1, productId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getProductName(String productId) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT name FROM product WHERE productId=?");
            pst.setString(1, productId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String generateNextQuotationId() {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            ResultSet rs = con.prepareStatement(
                    "SELECT quotationId FROM quotation ORDER BY quotationId DESC LIMIT 1"
            ).executeQuery();

            if (rs.next()) {
                int num = Integer.parseInt(rs.getString(1).replace("Q", "")) + 1;
                return "Q" + String.format("%03d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Q001";
    }

    public static boolean placeQuotation(
            String quotationId,
            String date,
            String customerId,
            ObservableList<QuotationTM> cartList
    ) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement pst1 = con.prepareStatement(
                    "INSERT INTO quotation VALUES (?,?,?)");
            pst1.setString(1, quotationId);
            pst1.setString(2, date);
            pst1.setString(3, customerId);
            pst1.executeUpdate();

            for (QuotationTM tm : cartList) {
                PreparedStatement pst2 = con.prepareStatement(
                        "INSERT INTO quotation_detail VALUES (?,?,?,?,?)");
                pst2.setString(1, quotationId);
                pst2.setString(2, tm.getProductId());
                pst2.setInt(3, tm.getQty());
                pst2.setDouble(4, tm.getUnitPrice());
                pst2.setDouble(5, tm.getTotal());
                pst2.executeUpdate();
            }

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                DBConnection.getInstance().getConnection().rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}
