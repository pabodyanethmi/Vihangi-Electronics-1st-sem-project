/*package lk.ijse.vihangielectronics_ijse_76.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.vihangielectronics_ijse_76.dto.StockDto;
import lk.ijse.vihangielectronics_ijse_76.dto.StockDto;
import lk.ijse.vihangielectronics_ijse_76.model.StockModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockManagement implements Initializable {

    @FXML
    private TextField productIdField;

    @FXML
    private TextField brandField;

    @FXML
    private TextField qtyField;

    @FXML
    private TableView<StockDto> tableUser;

    @FXML
    private TableColumn<StockDto, String> productIdColumn;

    @FXML
    private TableColumn<StockDto, Integer> qtyColumn;

    @FXML
    private TableColumn<StockDto, Double> priceColumn;


    private final String PRODUCT_ID_REGEX = "^P\\d{3}$";
    private final String QTY_REGEX = "^[0-9]+$";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));


        loadAllStocks();
    }

    @FXML
    private void onActionSave(ActionEvent event) {
        String pId = productIdField.getText().trim();
        String qtyText = qtyField.getText().trim();


        if (!pId.matches(PRODUCT_ID_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Product ID (Example: P001)").show();
        } else if (!qtyText.matches(QTY_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity (Numbers only)").show();
        } else {
            try {
                int qty = Integer.parseInt(qtyText);
                StockDto stockDTO = new StockDto(0, pId, qty); // stockId is auto-increment

                boolean result = StockModel.saveStock(StockDto);
                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Stock saved successfully!").show();
                    onActionReset(event);
                    loadAllStocks();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save stock!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    private void onActionUpdate(ActionEvent event) {
        String pId = productIdField.getText().trim();
        String qtyText = qtyField.getText().trim();

        if (!pId.matches(PRODUCT_ID_REGEX) || !qtyText.matches(QTY_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Please check Product ID and Quantity").show();
            return;
        }

        try {
            int qty = Integer.parseInt(qtyText);
            StockDto stockDTO = new StockDto(0, pId, qty);

            boolean result = StockModel.updateStock(stockDTO);
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Stock updated successfully!").show();
                loadAllStocks();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionDelete(ActionEvent event) {
        String pId = productIdField.getText().trim();

        if (!pId.matches(PRODUCT_ID_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Product ID").show();
            return;
        }

        try {
            boolean result = StockModel.deleteStock(pId);
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Stock deleted successfully!").show();
                onActionReset(event);
                loadAllStocks();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionReset(ActionEvent event) {
        productIdField.clear();
        brandField.clear(); // Stock ID field
        qtyField.clear();
    }

    public void loadAllStocks() {
        try {
            ArrayList<StockDto> stockList = StockModel.getAllStocks();
            if (stockList != null) {
                ObservableList<StockDto> obList = FXCollections.observableArrayList(stockList);
                tableUser.setItems(obList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchStock(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String pId = productIdField.getText().trim();
            try {
                StockDto stockDTO = StockModel.searchStock(pId);
                if (stockDTO != null) {
                    brandField.setText(String.valueOf(stockDTO.getStockId()));
                    qtyField.setText(String.valueOf(stockDTO.getStockQuantity()));
                } else {
                    new Alert(Alert.AlertType.ERROR, "Product not found in Stock!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void setData(MouseEvent event) {
        StockDto selectedItem = tableUser.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            productIdField.setText(selectedItem.getProductId());
            brandField.setText(String.valueOf(selectedItem.getStockId()));
            qtyField.setText(String.valueOf(selectedItem.getStockQuantity()));
        }
    }
}*/