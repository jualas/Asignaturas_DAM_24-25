package es.jualas.primer_registro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carga el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/jualas/primer_registro/registro-view.fxml"));
        // Crea una nueva escena con el contenido del archivo FXML y dimensiones específicas
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        // Carga y aplica la hoja de estilos CSS
        String css = getClass().getResource("/es/jualas/primer_registro/registro.css").toExternalForm();
        scene.getStylesheets().add(css);
        // Establece el título de la ventana
        stage.setTitle("LOGIN");
        // Establece la escena en el escenario
        stage.setScene(scene);
        // Impide que la ventana sea redimensionable
        stage.setResizable(false);
        // Muestra la ventana
        stage.show();
    }

    public static void main(String[] args) {
        // Lanza la aplicación JavaFX
        launch();
    }
}