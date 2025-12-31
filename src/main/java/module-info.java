module lk.ijse.vihangielectronics_ijse_76 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;
   // requires lk.ijse.vihangielectronics_ijse_76;


    opens lk.ijse.vihangielectronics_ijse_76.controller to javafx.fxml;
    exports lk.ijse.vihangielectronics_ijse_76;
    exports lk.ijse.vihangielectronics_ijse_76.controller;
    exports lk.ijse.vihangielectronics_ijse_76.dto;
}