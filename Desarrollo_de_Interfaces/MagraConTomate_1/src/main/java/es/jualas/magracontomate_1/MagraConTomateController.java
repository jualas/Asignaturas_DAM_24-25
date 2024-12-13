package es.jualas.magracontomate_1;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

// Controlador principal de la aplicación MagraConTomate
public class MagraConTomateController implements SceneManagerAware {
    // Gestor de escenas para cambiar entre vistas
    private SceneManager sceneManager;

    // Botones de la interfaz definidos en el FXML
    @FXML
    private Button loginButton;
    @FXML
    private Button calculadoraButton;
    @FXML
    private Button btnPrimosGemelos;
    @FXML
    private Button closeButton;

    // Método para establecer el gestor de escenas
    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    // Método llamado cuando se muestra esta escena
    @Override
    public void onSceneDisplayed() {
        // Reinicializar el estado si es necesario
    }

    // Manejador del evento de clic en el botón de login
    @FXML
    private void onLoginButtonClick() {
        sceneManager.switchScene("login");
    }

    // Manejador del evento de clic en el botón de calculadora
    @FXML
    private void onCalculadoraButtonClick() {
        sceneManager.switchScene("calculadora");
    }

    // Manejador del evento de clic en el botón de todos los primos
    @FXML
    private void onTodoPrimosButtonClick() {
        sceneManager.switchScene("todoPrimos");
    }

    // Manejador del evento de clic en el botón de primos gemelos
    @FXML
    private void onPrimosGemelosButtonClick() {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.switchScene("primosGemelos");
    }

    // Manejador del evento de clic en el botón de cerrar
    @FXML
    private void onCloseButtonClick() {
        Platform.exit();
    }
}
