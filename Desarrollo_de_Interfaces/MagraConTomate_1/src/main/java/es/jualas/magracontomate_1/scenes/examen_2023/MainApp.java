package es.jualas.magracontomate_1.scenes.examen_2023;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage myStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PrimosGemelos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),765, 765);

        // Asegurar que el CSS esté vinculado
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

        myStage.setScene(scene);
        myStage.setTitle("Números Primos Gemelos");
        myStage.setResizable(false);
        myStage.setWidth(765);
        myStage.setHeight(765);
        myStage.show();
    }

}
