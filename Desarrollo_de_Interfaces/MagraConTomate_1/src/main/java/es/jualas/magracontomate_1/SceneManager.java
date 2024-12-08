package es.jualas.magracontomate_1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;
    private Map<String, Scene> scenes = new HashMap<>();
    private Map<String, Object> controllers = new HashMap<>();
    private Map<String, double[]> sceneSizes = new HashMap<>();

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addScene(String name, String fxmlPath, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scenes.put(name, scene);
            controllers.put(name, loader.getController());
            sceneSizes.put(name, new double[]{width, height});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchScene(String name) {
        Scene scene = scenes.get(name);
        if (scene != null && stage != null) {
            double[] size = sceneSizes.get(name);
            stage.setWidth(size[0]);
            stage.setHeight(size[1]);
            stage.setScene(scene);
            Object controller = controllers.get(name);
            if (controller instanceof SceneManagerAware) {
                ((SceneManagerAware) controller).onSceneDisplayed();
            }
        } else {
            System.err.println("Scene " + name + " not found or stage is null");
        }
    }

    public Object getController(String name) {
        return controllers.get(name);
    }
}