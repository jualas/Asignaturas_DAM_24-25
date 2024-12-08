package es.jualas.magracontomate_1;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RefritoMAgraConTomateApplication extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setStage(stage);

        final int SCENE_WIDTH = 600;
        final int SCENE_HEIGHT = 440;

        // Añadir todas las escenas
        sceneManager.addScene("main", "/es/jualas/magracontomate_1/magraConTomate-view.fxml",600, 440);
        sceneManager.addScene("login", "/es/jualas/magracontomate_1/scenes/login/registro-view.fxml",600, 440);
        sceneManager.addScene("calculadora", "/es/jualas/magracontomate_1/scenes/calculadoraFX/calculadora-view.fxml",600, 440);
        sceneManager.addScene("todoPrimos", "/es/jualas/magracontomate_1/scenes/todoprimos/primos_hello-view.fxml",600, 440);
        sceneManager.addScene("primosGemelos", "/es/jualas/magracontomate_1/scenes/examen_2023/PrimosGemelos-view.fxml",765, 765);


        // Configurar el stage
        stage.setTitle("Refrito de magra con tomate!!!");
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        
        // Cargar el icono de manera segura
        String iconPath = "images/logo.png";
        Image icon = new Image(getClass().getResourceAsStream(iconPath));
        if (icon != null && !icon.isError()) {
            stage.getIcons().add(icon);
        } else {
            System.err.println("No se pudo cargar el icono: " + iconPath);
        }

        // Iniciar con la escena principal y establecer el tamaño
        sceneManager.switchScene("main");
        stage.setWidth(SCENE_WIDTH);
        stage.setHeight(SCENE_HEIGHT);
        stage.show();

        // Intentar asignar el SceneManager al controlador principal
        try {
            MagraConTomateController controller = (MagraConTomateController) sceneManager.getController("main");
            if (controller != null) {
                controller.setSceneManager(sceneManager);
            } else {
                System.err.println("No se pudo obtener el controlador para la escena principal");
            }
        } catch (Exception e) {
            System.err.println("Error al asignar SceneManager al controlador: " + e.getMessage());
        }
        // Inicializar SceneManager para todas las escenas
        initializeControllers(sceneManager);
    }

    private void initializeControllers(SceneManager sceneManager) {
        String[] sceneNames = {"main", "login", "calculadora", "todoPrimos"};
        for (String sceneName : sceneNames) {
            try {
                Object controller = sceneManager.getController(sceneName);
                if (controller instanceof SceneManagerAware) {
                    ((SceneManagerAware) controller).setSceneManager(sceneManager);
                }
            } catch (Exception e) {
                System.err.println("Error al inicializar el controlador para la escena " + sceneName + ": " + e.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}