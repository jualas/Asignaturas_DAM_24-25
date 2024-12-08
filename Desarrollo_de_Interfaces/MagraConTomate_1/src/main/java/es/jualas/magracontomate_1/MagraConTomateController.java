package es.jualas.magracontomate_1;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MagraConTomateController implements SceneManagerAware {
    private SceneManager sceneManager;

    @FXML
    private Button loginButton;
    @FXML
    private Button calculadoraButton;
    @FXML
    private Button btnPrimosGemelos;
    @FXML
    private Button closeButton;

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void onSceneDisplayed() {
        // Reinicializar el estado si es necesario
    }

    @FXML
    private void onLoginButtonClick() {
        sceneManager.switchScene("login");
    }

    @FXML
    private void onCalculadoraButtonClick() {
        sceneManager.switchScene("calculadora");
    }

    @FXML
    private void onTodoPrimosButtonClick() {
        sceneManager.switchScene("todoPrimos");
    }

    @FXML
    private void onPrimosGemelosButtonClick() {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.switchScene("primosGemelos");
    }

    @FXML
    private void onCloseButtonClick() {
        Platform.exit();
    }


}
