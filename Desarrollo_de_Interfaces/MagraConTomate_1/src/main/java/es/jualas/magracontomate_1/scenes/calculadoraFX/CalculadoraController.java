package es.jualas.magracontomate_1.scenes.calculadoraFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import es.jualas.magracontomate_1.SceneManager;

public class CalculadoraController {

    @FXML
    private TextField display;

    private double num1 = 0;
    private String operator = "";
    private boolean start = true;

    @FXML
    private void processDigit(javafx.event.ActionEvent event) {
        if (start) {
            display.setText("");
            start = false;
        }
        String value = ((Button) event.getSource()).getText();
        display.setText(display.getText() + value);
    }

    @FXML
    private void processOperator(javafx.event.ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        if (!value.equals("=")) {
            if (!operator.isEmpty())
                return;
            operator = value;
            num1 = Double.parseDouble(display.getText());
            display.setText("");
        }
    }

    @FXML
    private void processEquals() {
        double num2 = Double.parseDouble(display.getText());
        switch (operator) {
            case "+":
                display.setText(String.valueOf(num1 + num2));
                break;
            case "-":
                display.setText(String.valueOf(num1 - num2));
                break;
            case "*":
                display.setText(String.valueOf(num1 * num2));
                break;
            case "/":
                if (num2 == 0) {
                    display.setText("Error");
                } else {
                    display.setText(String.valueOf(num1 / num2));
                }
                break;
        }
        operator = "";
        start = true;
    }

public void onMenuButtonClick() {
    try {
        SceneManager.getInstance().switchScene("main");
    } catch (IllegalArgumentException e) {
        System.err.println("Error al cambiar de escena: " + e.getMessage());
        // Aquí puedes añadir código para mostrar un mensaje de error al usuario si lo deseas
    }
}

}