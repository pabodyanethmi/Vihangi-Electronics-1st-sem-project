package lk.ijse.vihangielectronics_ijse_76.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.vihangielectronics_ijse_76.dto.ProductDto;
import lk.ijse.vihangielectronics_ijse_76.model.ProductModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductManagement implements Initializable {
    @FXML
    private TableColumn colProductId;

    @FXML
    private TableColumn colProductName;

    @FXML
    private TableColumn colQty;

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private TableView<ProductDto> productView;

    @FXML
    private TableColumn colProductPrice;

    @FXML
    private TextField qtyField;

    @FXML
    private TextField unitPriceField;


    private final String PRODUCT_ID_REGEX = "^P\\d{3}$";
    private final String PRODUCT_NAME_REGEX = "^[A-Za-z]{3,}$";
    private final String PRODUCT_QTY_REGEX = "^[1-9][0-9]*$";
    private final String UNIT_PRICE_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";


    private final ProductModel productModel = new ProductModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));

        clearFields();
        loadProductTable();
        generateProductId();

    }
    @FXML
    void handleDeleteProduct() {
        String productId = productIdField.getText().trim();

        if (!productId.matches(PRODUCT_ID_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Product ID").show();
        } else {

            try {
                boolean result = productModel.deleteProduct(productId);

                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully!").show();
                    clearFields();
                    loadProductTable();
                    generateProductId();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }


            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        }
    }

    @FXML
    void handleProductSave() {
        String  pid = productIdField.getText().trim();
        String  name = productNameField.getText().trim();
        String qty = qtyField.getText().trim();
        String unitPrice = unitPriceField.getText();


        if (!qty.matches(PRODUCT_QTY_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid product Qty").show();
        } else if (! pid.matches(PRODUCT_ID_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid product ID").show();
        } else if (!name.matches(PRODUCT_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Product Name").show();
        } else if (!unitPrice.matches(UNIT_PRICE_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Product Price").show();
        }else{

            try {

                ProductDto productDto = new ProductDto();
                productDto.setProductId(pid);
                productDto.setName(name);
                productDto.setQuantity(Integer.parseInt(qty));
                productDto.setPricePerUnit(Double.parseDouble(unitPrice));


                boolean result = ProductModel.saveProduct(productDto);
                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "Product saved successfully!").show();
                    clearFields();
                    generateProductId();
                    loadProductTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();

            }
        }
    }

    @FXML
    void handleProductUpdate() {
        try {

            String pid = productIdField.getText().trim();
            String name = productNameField.getText().trim();
            String qty = qtyField.getText().trim();
            String unitPrice = unitPriceField.getText();


            if (!qty.matches(PRODUCT_QTY_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid product Qty").show();
            } else if (!pid.matches(PRODUCT_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid product ID").show();
            } else if (!name.matches(PRODUCT_NAME_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Product Name").show();
            } else if (!unitPrice.matches(UNIT_PRICE_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Product Price").show();
            } else {

                ProductDto productDto = new ProductDto(pid, name, qty, unitPrice);

                boolean result = productModel.updateproduct(productDto);

                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "product updated successfully!").show();
                    clearFields();
                    loadProductTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    @FXML
    void handleResetProduct() {

        clearFields();
    }
    private void clearFields() {
        productIdField.setText("");
        productNameField.setText("");
        qtyField.setText("");
        unitPriceField.setText("");


    }
    public void loadProductTable() {
        try {
            List<ProductDto> productDtoList = productModel.getProducts();

            if (productDtoList != null) {
                ObservableList<ProductDto> obList = FXCollections.observableArrayList(productDtoList);
                productView.setItems(obList);
                productView.refresh();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSearchproduct(KeyEvent event) {
        try {

            if (event.getCode() == KeyCode.ENTER) {

                String id = productIdField.getText();

                if (!id.matches(PRODUCT_ID_REGEX)) {
                    new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
                } else {

                    ProductDto productDto = productModel.searchProducts(id);

                    if (productDto != null) {
                        productIdField.setText(productDto.getProductId());
                        productNameField.setText(productDto.getName());
                        qtyField.setText(String.valueOf(productDto.getQuantity()));
                        unitPriceField.setText(String.valueOf(productDto.getPricePerUnit()));

                    } else {
                        new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }

    }
    private void generateProductId() {
        try {
            String nextId = productModel.generateNextProductId();
            productIdField.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate Product ID").show();
        }
    }

}