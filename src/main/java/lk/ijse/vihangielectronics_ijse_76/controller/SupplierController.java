package lk.ijse.vihangielectronics_ijse_76.controller;

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
import lk.ijse.vihangielectronics_ijse_76.dto.SupplierDto;
import lk.ijse.vihangielectronics_ijse_76.model.SupplierModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private TextField supplierIdField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;

    @FXML
    private TableView<SupplierDto> supplierView;
    @FXML
    private TableColumn<SupplierDto, String> colSupplierId;
    @FXML
    private TableColumn<SupplierDto, String> colName;
    @FXML
    private TableColumn<SupplierDto, String> colContact;
    @FXML
    private TableColumn<SupplierDto, String> colEmail;
    @FXML
    private TableColumn<SupplierDto, String> colAddress;

    private final SupplierModel supplierModel = new SupplierModel();

    private final String SUPPLIER_ID_REGEX = "^S\\d{3}$";
    private final String NAME_REGEX = "^[A-Za-z ]{3,}$";
    private final String CONTACT_REGEX = "^[0-9]{10}$";
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final String ADDRESS_REGEX = ".{3,}";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadSupplierTable();
        generateSupplierId();
    }

    // ================= SAVE =================
    @FXML
    private void handleSupplierSave() {
        try {
            SupplierDto dto = getSupplierFromFields();
            if (dto == null) return;

            if (supplierModel.saveSupplier(dto)) {
                alert(Alert.AlertType.INFORMATION, "Supplier saved successfully!");
                clearFields();
                generateSupplierId();
                loadSupplierTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert(Alert.AlertType.ERROR, "Save failed!");
        }
    }

    // ================= UPDATE =================
    @FXML
    private void handleUpdateSupplier() {
        try {
            SupplierDto dto = getSupplierFromFields();
            if (dto == null) return;

            if (supplierModel.updateSupplier(dto)) {
                alert(Alert.AlertType.INFORMATION, "Supplier updated successfully!");
                clearFields();
                loadSupplierTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert(Alert.AlertType.ERROR, "Update failed!");
        }
    }

    // ================= DELETE =================
    @FXML
    private void handleDeleteSupplier() {
        try {
            String id = supplierIdField.getText();

            if (!id.matches(SUPPLIER_ID_REGEX)) {
                alert(Alert.AlertType.ERROR, "Invalid Supplier ID");
                return;
            }

            if (supplierModel.deleteSupplier(id)) {
                alert(Alert.AlertType.INFORMATION, "Supplier deleted!");
                clearFields();
                loadSupplierTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert(Alert.AlertType.ERROR, "Delete failed!");
        }
    }

    // ================= SEARCH =================
    @FXML
    private void handleSearchSupplier(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = supplierIdField.getText();

                SupplierDto dto = supplierModel.searchSupplier(id);
                if (dto != null) {
                    firstNameField.setText(dto.getName());
                    contactField.setText(dto.getContact());
                    emailField.setText(dto.getEmail());
                    addressField.setText(dto.getAddress());
                } else {
                    alert(Alert.AlertType.ERROR, "Supplier not found!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ================= TABLE LOAD =================
    private void loadSupplierTable() {
        try {
            List<SupplierDto> list = supplierModel.getAllSuppliers();
            ObservableList<SupplierDto> obList = FXCollections.observableArrayList(list);
            supplierView.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= ID GENERATION =================
    private void generateSupplierId() {
        try {
            String lastId = supplierModel.getLastSupplierId();
            if (lastId == null) {
                supplierIdField.setText("S001");
            } else {
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                supplierIdField.setText(String.format("S%03d", num));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= GET DTO =================
    private SupplierDto getSupplierFromFields() {

        String id = supplierIdField.getText();
        String name = firstNameField.getText();
        String contact = contactField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        if (!id.matches(SUPPLIER_ID_REGEX)) {
            alert(Alert.AlertType.ERROR, "Invalid Supplier ID");
            return null;
        }
        if (!name.matches(NAME_REGEX)) {
            alert(Alert.AlertType.ERROR, "Invalid Name");
            return null;
        }
        if (!contact.matches(CONTACT_REGEX)) {
            alert(Alert.AlertType.ERROR, "Invalid Contact");
            return null;
        }
        if (!email.matches(EMAIL_REGEX)) {
            alert(Alert.AlertType.ERROR, "Invalid Email");
            return null;
        }
        if (!address.matches(ADDRESS_REGEX)) {
            alert(Alert.AlertType.ERROR, "Invalid Address");
            return null;
        }

        return new SupplierDto(id, name, contact, email, address);
    }

    // ================= TABLE CLICK =================
    public void setData(MouseEvent event) {
        SupplierDto dto = supplierView.getSelectionModel().getSelectedItem();
        if (dto != null) {
            supplierIdField.setText(dto.getSupplierId());
            firstNameField.setText(dto.getName());
            contactField.setText(dto.getContact());
            emailField.setText(dto.getEmail());
            addressField.setText(dto.getAddress());
        }
    }

    // ================= UTILS =================
    private void clearFields() {
        generateSupplierId();
        firstNameField.clear();
        contactField.clear();
        emailField.clear();
        addressField.clear();
    }

    private void alert(Alert.AlertType type, String msg) {
        new Alert(type, msg).show();
    }

    public void handleResetSupplier(ActionEvent actionEvent) {
        clearFields();
    }
}
