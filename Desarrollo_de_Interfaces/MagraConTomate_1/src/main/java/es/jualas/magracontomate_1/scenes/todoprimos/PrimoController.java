package es.jualas.magracontomate_1.scenes.todoprimos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import es.jualas.magracontomate_1.SceneManager;

public class PrimoController {

    @FXML
    private TextField inputField; // Campo de texto para la entrada del usuario

    @FXML
    private TilePane numberTilePane; // Contenedor para mostrar los números primos

    @FXML
    private Button generateButton; // Botón para generar números primos
    @FXML
    private Button btnMenu;

    @FXML
    private void onMenuButtonClick() {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.switchScene("main");
    }


    @FXML
    public void initialize() {
        // Desactivar el botón al inicio
        generateButton.setDisable(true);

        // Agregar un ChangeListener para habilitar/deshabilitar el botón según la entrada
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Permitir solo entrada numérica
                if (!newValue.matches("\\d*")) {
                    inputField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                // Limitar el valor entre 1 y 100
                if (!newValue.isEmpty()) {
                    int value = Integer.parseInt(newValue);
                    if (value < 1) {
                        inputField.setText("1");
                        value = 1;
                    } else if (value > 100) {
                        inputField.setText("100");
                        value = 100;
                    }
                    generateButton.setDisable(false);
                } else {
                    generateButton.setDisable(true);
                }
            } catch (NumberFormatException e) {
                generateButton.setDisable(true);
            }
        });
    }

    @FXML
    protected void onHelloButtonClick() {
        // Limpiar los números primos mostrados anteriormente
        numberTilePane.getChildren().clear();
        // Obtener el límite de números primos a generar
        int limit = Integer.parseInt(inputField.getText());
        // Generar y mostrar números primos hasta el límite
        for (int i = 1; i <= limit; i++) {
            if (isPrime(i)) {
                Label numberLabel = new Label(String.valueOf(i)); // Crear etiqueta para el número primo
                numberLabel.getStyleClass().add("number-label"); // Añadir estilo a la etiqueta
                numberTilePane.getChildren().add(numberLabel); // Añadir etiqueta al contenedor
            }
        }
    }

    // Método para verificar si un número es primo
    private boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    @FXML
    protected void onCloseButtonClick() {
        // Cerrar la aplicación
        System.exit(0);
    }
}