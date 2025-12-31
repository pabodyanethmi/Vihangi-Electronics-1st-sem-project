package lk.ijse.vihangielectronics_ijse_76.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.vihangielectronics_ijse_76.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    @FXML
    private AnchorPane mainContent;
    @FXML
    private AnchorPane customerContent;
    @FXML
    private AnchorPane mainUserView;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("User view is loaded");

        updateDateTime();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateDateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void clickDashBoardNav() throws IOException {
        Parent customerFXML = HelloApplication.loadFXML("dashboard.fxml");
        mainContent.getChildren().setAll(customerFXML);
    }

    @FXML
    private void clickCustomerNav() throws IOException {
        try {
            mainContent.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerManagement.fxml")));
            anchorPane.prefHeightProperty().bind(mainContent.heightProperty());
            anchorPane.prefWidthProperty().bind(mainContent.widthProperty());
            mainContent.getChildren().add(anchorPane);

        } catch (IOException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    @FXML
    private void clickSupplierNav() throws IOException {
        try {
            mainContent.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SupplierManagement.fxml"));
            anchorPane.prefHeightProperty().bind(mainContent.heightProperty());
            anchorPane.prefWidthProperty().bind(mainContent.widthProperty());
            mainContent.getChildren().add(anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void clickProductsNav() throws IOException {
        try {
            mainContent.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/ProductManagement.fxml"));
            anchorPane.prefHeightProperty().bind(mainContent.heightProperty());
            anchorPane.prefWidthProperty().bind(mainContent.widthProperty());
            mainContent.getChildren().add(anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void clickQuotationNav() throws IOException {
        try {
            mainContent.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/QuotationManagement.fxml"));
            anchorPane.prefHeightProperty().bind(mainContent.heightProperty());
            anchorPane.prefWidthProperty().bind(mainContent.widthProperty());
            mainContent.getChildren().add(anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void clickPaymentNav() throws IOException {
        try {
            mainContent.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/payment.fxml"));
            anchorPane.prefHeightProperty().bind(mainContent.heightProperty());
            anchorPane.prefWidthProperty().bind(mainContent.widthProperty());
            mainContent.getChildren().add(anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void clickReportNav() throws IOException {
        Parent customerFXML = HelloApplication.loadFXML("report.fxml");
        mainContent.getChildren().setAll(customerFXML);
    }

    @FXML
    private void clickLogOutNav() throws IOException {
        try {
            mainUserView.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            anchorPane.prefHeightProperty().bind(mainUserView.heightProperty());
            anchorPane.prefWidthProperty().bind(mainUserView.widthProperty());
            mainUserView.getChildren().add(anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDateTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(" yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        lblTime.setText(currentTime.format(timeFormatter));
        lblDate.setText(currentDate.format(dateFormatter));
    }
}