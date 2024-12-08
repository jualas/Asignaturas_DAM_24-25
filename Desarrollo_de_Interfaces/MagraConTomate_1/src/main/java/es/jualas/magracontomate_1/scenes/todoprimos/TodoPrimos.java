package es.jualas.magracontomate_1.scenes.todoprimos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TodoPrimos extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(TodoPrimos.class.getResource("/es/jualas/todoprimos/primos_hello-view.fxml"));
        // Crear una nueva escena con el contenido del archivo FXML
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        // Establecer el título de la ventana
        stage.setTitle("Calcúla los números primos");
        // Establecer la escena en el escenario
        stage.setScene(scene);
        // Evitar que la ventana se pueda redimensionar
        stage.setResizable(false);
        // Mostrar la ventana
        stage.show();
    }
}