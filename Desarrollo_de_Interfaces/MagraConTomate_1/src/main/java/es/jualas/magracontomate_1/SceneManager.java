package es.jualas.magracontomate_1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    // Instancia única (singleton) de SceneManager
    private static SceneManager instance;
    // El escenario principal de la aplicación
    private Stage stage;
    // Mapa para almacenar escenas por nombre
    private Map<String, Scene> scenes = new HashMap<>();
    // Mapa para almacenar controladores por nombre de escena
    private Map<String, Object> controllers = new HashMap<>();
    // Mapa para almacenar tamaños de escena por nombre
    private Map<String, double[]> sceneSizes = new HashMap<>();

    // Constructor privado para el patrón singleton
    private SceneManager() {}

    // Método para obtener la instancia única
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    // Establece el escenario principal de la aplicación
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Añade una nueva escena al gestor
    public void addScene(String name, String fxmlPath, double width, double height) {
        try {
            // Carga el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            // Crea una nueva escena
            Scene scene = new Scene(root);
            // Almacena la escena, el controlador y el tamaño
            scenes.put(name, scene);
            controllers.put(name, loader.getController());
            sceneSizes.put(name, new double[]{width, height});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cambia a una escena diferente
    public void switchScene(String name) {
        Scene scene = scenes.get(name);
        if (scene != null && stage != null) {
            // Establece el tamaño del escenario y la escena
            double[] size = sceneSizes.get(name);
            stage.setWidth(size[0]);
            stage.setHeight(size[1]);
            stage.setScene(scene);
            // Notifica al controlador si implementa SceneManagerAware
            Object controller = controllers.get(name);
            if (controller instanceof SceneManagerAware) {
                ((SceneManagerAware) controller).onSceneDisplayed();
            }
        } else {
            System.err.println("Escena " + name + " no encontrada o el escenario es nulo");
        }
    }

    // Obtiene el controlador de una escena específica
    public Object getController(String name) {
        return controllers.get(name);
    }
}

