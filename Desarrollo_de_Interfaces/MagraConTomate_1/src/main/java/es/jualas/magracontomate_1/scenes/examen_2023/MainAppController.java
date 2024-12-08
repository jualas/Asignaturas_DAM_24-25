package es.jualas.magracontomate_1.scenes.examen_2023;

import es.jualas.magracontomate_1.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {

    @FXML
    private TilePane PanelCentral;

    @FXML
    private Button salirButton;

    @FXML
    private void onActionSalir(ActionEvent event) {
        Stage stage = (Stage) salirButton.getScene().getWindow();
        stage.close(); // Cierra la aplicación
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PanelCentral.setHgap(15);
        PanelCentral.setVgap(15);
        PanelCentral.setPrefColumns(10); // Asegura que haya 10 columnas

        // Crear un array para almacenar los números primos
        boolean[] esPrimo = new boolean[101];
        for (int i = 2; i <= 100; i++) {
            esPrimo[i] = esPrimo(i);
        }

        for (int i = 1; i <= 100; i++) {
            int numero = i; // Variable temporal
            Button button = new Button(String.valueOf(numero));
            button.setPrefSize(60, 50); // Tamaño de botones
            button.getStyleClass().add("button"); // Clase para estilo general

            // Asignar estilo a los botones de números primos gemelos
            if ((esPrimo[numero] && numero + 2 <= 100 && esPrimo[numero + 2]) ||
                    (esPrimo[numero] && numero - 2 >= 1 && esPrimo[numero - 2])) {
                button.getStyleClass().add("primo-gemelo");
                System.out.println("Asignado primo-gemelo a: " + numero);
            }

            // Asignar acción al botón
            button.setOnAction(event -> mostrarAlerta(numero));

            // Añadir botón al panel
            PanelCentral.getChildren().add(button);
        }
    }


    // Método para verificar si un número es primo
    private boolean esPrimo(int numero) {
        if (numero <= 1) return false;
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }

    // Método para verificar si dos números son primos gemelos
    private boolean sonPrimosGemelos(int num1, int num2) {
        return esPrimo(num1) && esPrimo(num2) && Math.abs(num1 - num2) == 2;
    }

    // Método para asignar la clase CSS a los botones de números primos gemelos
    private void asignarEstiloPrimosGemelos(Button button, int numero) {
        if ((esPrimo(numero) && esPrimo(numero + 2)) || (esPrimo(numero) && esPrimo(numero - 2))) {
            button.getStyleClass().add("primo-gemelo");
        }
    }

    // Método para mostrar una alerta al pulsar un botón
    private void mostrarAlerta(int numero) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Verifica si el número es parte de un par de primos gemelos
        if ((esPrimo(numero) && esPrimo(numero + 2)) || (esPrimo(numero) && esPrimo(numero - 2))) {
            alert.setContentText(numero + " es un número primo gemelo.");
        } else {
            alert.setContentText(numero + " es un número normal.");
        }

        alert.show();
    }

    public void onMenuButtonClick(ActionEvent actionEvent) {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.switchScene("main");
    }
}
