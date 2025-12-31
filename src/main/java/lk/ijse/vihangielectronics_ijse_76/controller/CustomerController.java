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
import lk.ijse.vihangielectronics_ijse_76.dto.CustomerDto;
import lk.ijse.vihangielectronics_ijse_76.model.CustomerModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private TextField customerIdField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;

    @FXML
    private TableView<CustomerDto> customerView;
    @FXML
    private TableColumn<CustomerDto, String> colCustomerId;
    @FXML
    private TableColumn<CustomerDto, String> colName;
    @FXML
    private TableColumn<CustomerDto, String> colContact;
    @FXML
    private TableColumn<CustomerDto, String> colEmail;
    @FXML
    private TableColumn<CustomerDto, String> colAddress;

    private final CustomerModel customerModel = new CustomerModel();

    private final String CUSTOMER_ID_REGEX = "^C\\d{3}$";
    private final String NAME_REGEX = "^[A-Za-z ]{3,}$";
    private final String CONTACT_REGEX = "^[0-9]{10}$";
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final String ADDRESS_REGEX = ".{3,}";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadCustomerTable();
        generateCustomerId();
    }

    @FXML
    private void handleCustomerSave() {
        try {
            CustomerDto dto = getCustomerFromFields();
            if (dto == null) return;

            if (customerModel.saveCustomer(dto)) {
                alert(Alert.AlertType.INFORMATION, "Customer saved successfully!");
                clearFields();
                generateCustomerId();
                loadCustomerTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert(Alert.AlertType.ERROR, "Save failed!");
        }
    }

    @FXML
    private void handleUpdateCustomer() {
        try {
            CustomerDto dto = getCustomerFromFields();
            if (dto == null) return;

            if (customerModel.updateCustomer(dto)) {
                alert(Alert.AlertType.INFORMATION, "Customer updated successfully!");
                clearFields();
                loadCustomerTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert(Alert.AlertType.ERROR, "Update failed!");
        }
    }

    @FXML
    private void handleDeleteCustomer() {
        try {
            String id = customerIdField.getText();

            if (!id.matches(CUSTOMER_ID_REGEX)) {
                alert(Alert.AlertType.ERROR, "Invalid Customer ID");
                return;
            }

            if (customerModel.deleteCustomer(id)) {
                alert(Alert.AlertType.INFORMATION, "Customer deleted!");
                clearFields();
                loadCustomerTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert(Alert.AlertType.ERROR, "Delete failed!");
        }
    }

    @FXML
    private void handleSearchCustomer(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = customerIdField.getText();

                CustomerDto dto = customerModel.searchCustomer(id);
                if (dto != null) {
                    firstNameField.setText(dto.getName());
                    contactField.setText(dto.getContact());
                    emailField.setText(dto.getEmail());
                    addressField.setText(dto.getAddress());
                } else {
                    alert(Alert.AlertType.ERROR, "Customer not found!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCustomerTable() {
        try {
            List<CustomerDto> list = customerModel.getAllCustomers();
            ObservableList<CustomerDto> obList = FXCollections.observableArrayList(list);
            customerView.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateCustomerId() {
        try {
            String lastId = customerModel.getLastCustomerId();
            if (lastId == null) {
                customerIdField.setText("C001");
            } else {
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                customerIdField.setText(String.format("C%03d", num));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CustomerDto getCustomerFromFields() {

        String id = customerIdField.getText();
        String name = firstNameField.getText();
        String contact = contactField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        if (!id.matches(CUSTOMER_ID_REGEX)) {
            alert(Alert.AlertType.ERROR, "Invalid Customer ID");
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

        return new CustomerDto(id, name, contact, email, address);
    }

    private void clearFields() {
        generateCustomerId();
        firstNameField.clear();
        contactField.clear();
        emailField.clear();
        addressField.clear();
    }

    private void alert(Alert.AlertType type, String msg) {
        new Alert(type, msg).show();
    }

    public void handleResetCustomer(ActionEvent actionEvent) {
        clearFields();
    }

    public void setData(MouseEvent mouseEvent) {

        CustomerDto selectedCustomer = customerView.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            customerIdField.setText(selectedCustomer.getCustomerId());
            firstNameField.setText(selectedCustomer.getName());
            contactField.setText(selectedCustomer.getContact());
            emailField.setText(selectedCustomer.getEmail());
            addressField.setText(selectedCustomer.getAddress());
        }
    }

}
