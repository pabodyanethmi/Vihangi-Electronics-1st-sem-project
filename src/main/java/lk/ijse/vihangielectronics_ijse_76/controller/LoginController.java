package lk.ijse.vihangielectronics_ijse_76.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import lk.ijse.vihangielectronics_ijse_76.HelloApplication;


public class LoginController {
    public AnchorPane subContentField;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void login() throws IOException {

        String realUsername = "Pabodya";
        String realPassword = "2002";

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals(realUsername) && password.equals(realPassword)) {

            System.out.println("Logged-in successfully!");

            singIn();

        } else {

            System.out.println("Invalid username or password!");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login message");
            alert.setHeaderText("Invalid username or password!");
            alert.show();

        }
    }

    private void singIn() {
        try {
            subContentField.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));
            anchorPane.prefHeightProperty().bind(subContentField.heightProperty());
            anchorPane.prefWidthProperty().bind(subContentField.widthProperty());
            subContentField.getChildren().add(anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}