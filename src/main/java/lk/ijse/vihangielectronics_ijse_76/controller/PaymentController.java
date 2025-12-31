package lk.ijse.vihangielectronics_ijse_76.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.vihangielectronics_ijse_76.dto.PaymentDto;
import lk.ijse.vihangielectronics_ijse_76.model.PaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    /* ================= FXML FIELDS ================= */

    @FXML
    private TextField txtPaymentId;

    @FXML
    private ComboBox<String> cmbOrderId;

    @FXML
    private TextField txtPaymentAmount;

    @FXML
    private ComboBox<String> cmbPaymentMethod;

    @FXML
    private ComboBox<String> cmbPaymentStatus;

    @FXML
    private DatePicker dpPaymentDate;

    @FXML
    private TextField txtSearchPayment;

    @FXML
    private TableView<PaymentDto> tblPayment;

    @FXML
    private TableColumn<PaymentDto, String> colPayId;

    @FXML
    private TableColumn<PaymentDto, String> colOrderId;

    @FXML
    private TableColumn<PaymentDto, LocalDate> colPayDate;

    @FXML
    private TableColumn<PaymentDto, Integer> colAmount;

    @FXML
    private TableColumn<PaymentDto, String> colMethod;

    @FXML
    private TableColumn<PaymentDto, String> colStatus;

    @FXML
    private Button btnCompletePayment;

    /* ================= OBJECTS ================= */

    private final PaymentModel paymentModel = new PaymentModel();
    private final ObservableList<PaymentDto> paymentList = FXCollections.observableArrayList();

    /* ================= INITIALIZE ================= */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCellValueFactories();
        loadOrderIds();
        loadAllPayments();
        setDefaultValues();
        tableRowSelectListener();
    }

    /* ================= TABLE SETUP ================= */

    private void setCellValueFactories() {
        colPayId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPayDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    /* ================= LOAD DATA ================= */

    private void loadOrderIds() {
        // Temporary order IDs (replace with OrderModel later)
        cmbOrderId.getItems().addAll("OD001", "OD002", "OD003");
    }

    private void loadAllPayments() {
        try {
            paymentList.clear();
            paymentList.addAll(paymentModel.getAllPayments());
            tblPayment.setItems(paymentList);
        } catch (SQLException e) {
            showError("Failed to load payments");
        }
    }

    /* ================= BUTTON ACTION ================= */

    @FXML
    private void btnCompletePaymentOnAction() {

        try {
            PaymentDto dto = collectPaymentData();

            if (paymentModel.isPaymentExists(dto.getPaymentId())) {
                showWarning("Payment ID already exists!");
                return;
            }

            boolean saved = paymentModel.savePayment(dto);

            if (saved) {
                showInfo("Payment saved successfully!");
                loadAllPayments();
                clearFields();
            }

        } catch (NumberFormatException e) {
            showWarning("Invalid amount!");
        } catch (SQLException e) {
            showError("Database error!");
        }
    }

    /* ================= SEARCH ================= */

    @FXML
    private void txtSearchPaymentOnAction() {

        try {
            PaymentDto dto = paymentModel.searchPayment(txtSearchPayment.getText());

            if (dto != null) {
                tblPayment.getSelectionModel().select(dto);
                fillFields(dto);
            } else {
                showWarning("Payment not found!");
            }

        } catch (SQLException e) {
            showError("Search failed!");
        }
    }

    /* ================= TABLE CLICK ================= */

    private void tableRowSelectListener() {
        tblPayment.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                fillFields(newVal);
            }
        });
    }

    /* ================= HELPERS ================= */

    private PaymentDto collectPaymentData() {
        return new PaymentDto(
                txtPaymentId.getText(),
                cmbOrderId.getValue(),
                dpPaymentDate.getValue(),
                Integer.parseInt(txtPaymentAmount.getText()),
                cmbPaymentMethod.getValue(),
                cmbPaymentStatus.getValue()
        );
    }

    private void fillFields(PaymentDto dto) {
        txtPaymentId.setText(dto.getPaymentId());
        cmbOrderId.setValue(dto.getOrderId());
        dpPaymentDate.setValue(dto.getDate());
        txtPaymentAmount.setText(String.valueOf(dto.getAmount()));
        cmbPaymentMethod.setValue(dto.getPaymentMethod());
        cmbPaymentStatus.setValue(dto.getStatus());
    }

    private void clearFields() {
        txtPaymentId.clear();
        txtPaymentAmount.clear();
        txtSearchPayment.clear();
        cmbOrderId.getSelectionModel().clearSelection();
        cmbPaymentMethod.getSelectionModel().clearSelection();
        cmbPaymentStatus.getSelectionModel().clearSelection();
        dpPaymentDate.setValue(null);
    }

    private void setDefaultValues() {
        dpPaymentDate.setValue(LocalDate.now());
    }

    /* ================= ALERTS ================= */

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).show();
    }

    private void showWarning(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).show();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }
}
