package es.jualas.magracontomate_1.scenes.calculadoraFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Clase principal que extiende de Application para crear una aplicación JavaFX
public class CalculadoraApplication extends Application {

    // Método sobrescrito que se ejecuta al iniciar la aplicación
    @Override
    public void start(Stage stage) throws IOException {
        // Carga el archivo FXML que define la interfaz de usuario
        FXMLLoader fxmlLoader = new FXMLLoader(CalculadoraApplication.class.getResource("Calculadora-view.fxml"));
        // Crea una nueva escena con el contenido cargado del FXML y dimensiones específicas
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        // Establece el título de la ventana
        stage.setTitle("Calculadora");
        // Asigna la escena al escenario (ventana principal)
        stage.setScene(scene);
        // Muestra la ventana
        stage.show();
    }

    // Método principal que lanza la aplicación JavaFX
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        // Este método se llama automáticamente cuando la aplicación se está cerrando.
        // Es útil para realizar tareas de limpieza o finalización, como:
        // - Guardar el estado de la aplicación
        // - Cerrar conexiones de base de datos
        // - Detener hilos en ejecución
        // - Liberar recursos que no se liberan automáticamente
        // En futuras expansiones de la aplicación, este sería el lugar ideal para implementar
        // cualquier lógica necesaria antes del cierre completo de la aplicación.
        System.out.println("Aplicación de calculadora cerrándose.");
    }
}
}