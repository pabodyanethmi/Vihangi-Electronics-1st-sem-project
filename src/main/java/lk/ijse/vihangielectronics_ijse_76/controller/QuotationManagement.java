package lk.ijse.vihangielectronics_ijse_76.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.vihangielectronics_ijse_76.dto.tm.QuotationTM;
import lk.ijse.vihangielectronics_ijse_76.model.QuotationModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class QuotationManagement implements Initializable {

    @FXML
    private TextField txtQuotationId, txtDate, txtQty;

    @FXML
    private ComboBox<String> cmbCustomerId, cmbProductId;

    @FXML
    private Label lblUnitPrice, lblQtyOnHand, lblNetTotal;

    @FXML
    private TableView<QuotationTM> tblQuotation;

    @FXML
    private TableColumn<?, ?> colProductId, colProductName, colQty, colUnitPrice, colTotal;

    private ObservableList<QuotationTM> cartList = FXCollections.observableArrayList();

    private double netTotal = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadCustomerIds();
        loadProductIds();
        setDate();
        generateQuotationId();
    }

    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void setDate() {
        txtDate.setText(LocalDate.now().toString());
    }

    private void generateQuotationId() {
        txtQuotationId.setText(QuotationModel.generateNextQuotationId());
    }

    private void loadCustomerIds() {
        cmbCustomerId.setItems(QuotationModel.getAllCustomerIds());
    }

    private void loadProductIds() {
        cmbProductId.setItems(QuotationModel.getAllProductIds());
    }

    @FXML
    void cmbProductOnAction() {
        String productId = cmbProductId.getValue();
        if (productId != null) {
            lblUnitPrice.setText(String.valueOf(QuotationModel.getUnitPrice(productId)));
            lblQtyOnHand.setText(String.valueOf(QuotationModel.getQtyOnHand(productId)));
        }
    }

    @FXML
    void handleAddToCart() {
        String productId = cmbProductId.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        String productName = QuotationModel.getProductName(productId);

        double total = qty * unitPrice;
        netTotal += total;

        cartList.add(new QuotationTM(
                productId,
                productName,
                qty,
                unitPrice,
                total
        ));

        tblQuotation.setItems(cartList);
        lblNetTotal.setText(String.format("%.2f", netTotal));
        txtQty.clear();
    }

    @FXML
    void handlePlaceQuotation() {
        boolean isSaved = QuotationModel.placeQuotation(
                txtQuotationId.getText(),
                txtDate.getText(),
                cmbCustomerId.getValue(),
                cartList
        );

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Quotation Placed Successfully").show();
            handleReset();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Place Quotation").show();
        }
    }

    @FXML
    void handleReset() {
        cartList.clear();
        tblQuotation.refresh();
        lblNetTotal.setText("0.00");
        generateQuotationId();
    }

    @FXML
    void cmbCustomerOnAction() {
        // optional
    }
}
