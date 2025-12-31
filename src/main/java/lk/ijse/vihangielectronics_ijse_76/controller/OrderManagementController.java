package lk.ijse.vihangielectronics_ijse_76.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.vihangielectronics_ijse_76.dto.OrderTableDto;
import lk.ijse.vihangielectronics_ijse_76.dto.OrderTableDto;
import lk.ijse.vihangielectronics_ijse_76.model.OrderTableModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class OrderManagementController implements Initializable {


    @FXML
    private TextField orderIdField;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField totalAmountField;

    @FXML
    private ComboBox<String> statusCombo;


    @FXML
    private TableView<OrderTableDto> tblOrder;

    @FXML
    private TableColumn<OrderTableDto, String> colOrderId;

    @FXML
    private TableColumn<OrderTableDto, String> colCustomerId;

    @FXML
    private TableColumn<OrderTableDto, LocalDate> colDate;

    @FXML
    private TableColumn<OrderTableDto, LocalTime> colTime;

    @FXML
    private TableColumn<OrderTableDto, Double> colTotal;

    @FXML
    private TableColumn<OrderTableDto, String> colStatus;

    private final OrderTableModel orderModel = new OrderTableModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        loadAllOrders();
        generateNewOrderId();
        loadStatus();
        tableSelectionListener();
    }


    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }


    private void loadAllOrders() {
        try {
            List<OrderTableDto> list = orderModel.getAllOrders();
            ObservableList<OrderTableDto> observableList = FXCollections.observableArrayList(list);
            tblOrder.setItems(observableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load orders").show();
            e.printStackTrace();
        }
    }


    private void generateNewOrderId() {
        try {
            orderIdField.setText(orderModel.getNextOrderId());
            orderIdField.setEditable(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadStatus() {
        statusCombo.setItems(FXCollections.observableArrayList(
                "Success", "Pending", "Fail"
        ));
        statusCombo.getSelectionModel().selectFirst();
    }


    @FXML
    void handleSaveOrder(ActionEvent event) {
        try {
            OrderTableDto dto = new OrderTableDto(
                    orderIdField.getText(),
                    customerIdField.getText(),
                    LocalDate.now(),
                    LocalTime.now(),
                    descriptionField.getText(),
                    Double.parseDouble(totalAmountField.getText()),
                    statusCombo.getValue()
            );

            if (orderModel.saveOrder(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Order Saved Successfully").show();
                loadAllOrders();
                clearFields();
                generateNewOrderId();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Order Save Failed").show();
            e.printStackTrace();
        }
    }


    @FXML
    void handleUpdateOrder(ActionEvent event) {
        try {
            OrderTableDto dto = new OrderTableDto(
                    orderIdField.getText(),
                    customerIdField.getText(),
                    LocalDate.now(),
                    LocalTime.now(),
                    descriptionField.getText(),
                    Double.parseDouble(totalAmountField.getText()),
                    statusCombo.getValue()
            );

            if (orderModel.updateOrder(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Order Updated").show();
                loadAllOrders();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Order Update Failed").show();
            e.printStackTrace();
        }
    }


    @FXML
    void handleDeleteOrder(ActionEvent event) {
        try {
            if (orderModel.deleteOrder(orderIdField.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Order Deleted").show();
                loadAllOrders();
                clearFields();
                generateNewOrderId();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Order Delete Failed").show();
            e.printStackTrace();
        }
    }


    @FXML
    void handleResetOrder(ActionEvent event) {
        clearFields();
        generateNewOrderId();
        tblOrder.getSelectionModel().clearSelection();
    }


    private void tableSelectionListener() {
        tblOrder.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                orderIdField.setText(selected.getOrderId());
                customerIdField.setText(selected.getCustomerId());
                descriptionField.setText(selected.getDescription());
                totalAmountField.setText(String.valueOf(selected.getTotalAmount()));
                statusCombo.setValue(selected.getStatus());
            }
        });
    }


    private void clearFields() {
        customerIdField.clear();
        descriptionField.clear();
        totalAmountField.clear();
        statusCombo.getSelectionModel().selectFirst();
    }
}

