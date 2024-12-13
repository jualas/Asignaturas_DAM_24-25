package com.profesor.loteria;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label num1, num2, num3, num4, num5;
    @FXML
    private TextField inputBoleto;
    @FXML
    private Button generarBoleto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generarBoleto.setDisable(true); // Deshabilitar el botón inicialmente

        inputBoleto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputBoleto.setText(newValue.replaceAll("[^\\d]", ""));
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error de entrada");
                alert.setHeaderText(null);
                alert.setContentText("No se permite escribir letras.");
                alert.showAndWait();
            }
            if (inputBoleto.getText().length() > 5) {
                inputBoleto.setText(inputBoleto.getText().substring(0, 5));
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error de entrada");
                alert.setHeaderText(null);
                alert.setContentText("No se puede escribir más de 5 números.");
                alert.showAndWait();
            }
            generarBoleto.setDisable(inputBoleto.getText().length() != 5); // Habilitar o deshabilitar el botón
        });
    }

    @FXML
    private void handlerGenerarBoleto(ActionEvent event) {
        String numeroIngresado = inputBoleto.getText();
        if (numeroIngresado.length() == 5) {
            num1.setText(String.valueOf(numeroIngresado.charAt(0)));
            num2.setText(String.valueOf(numeroIngresado.charAt(1)));
            num3.setText(String.valueOf(numeroIngresado.charAt(2)));
            num4.setText(String.valueOf(numeroIngresado.charAt(3)));
            num5.setText(String.valueOf(numeroIngresado.charAt(4)));
        }
    }

    @FXML
    private void handlerCerrarAplicacion(ActionEvent event) {
        Platform.exit();
    }
}