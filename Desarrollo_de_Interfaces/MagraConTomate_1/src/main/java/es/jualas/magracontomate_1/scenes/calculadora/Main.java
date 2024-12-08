package es.jualas.magracontomate_1.scenes.calculadora;

import es.jualas.magracontomate_1.scenes.calculadora.Calculadora;
import es.jualas.magracontomate_1.scenes.calculadora.ManejadorBoton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends VBox {
    private TextField pantalla;  // Campo de texto para mostrar la expresión y el resultado
    private Calculadora calculadora; // Instancia de la calculadora

    public Main() {

            // Crear el campo de texto que mostrará la expresión y el resultado
            pantalla = new TextField();
            pantalla.setEditable(false);  // No permitir que el usuario escriba directamente
            pantalla.setFocusTraversable(false);  // Desactivar el foco en el campo de texto
            pantalla.setMinHeight(25);  // Altura mínima del campo de texto
            pantalla.setMaxHeight(25);  // Altura máxima del campo de texto
            pantalla.setMinWidth(215);  // Anchura mínima del campo de texto
            pantalla.setMaxWidth(215);  // Anchura máxima del campo de texto
            pantalla.getStyleClass().add("text-field");  // Añadir clase CSS

            // Crear la instancia de la calculadora
            calculadora = new Calculadora(pantalla);

            // Crear un AnchorPane para los botones
            AnchorPane anchorPaneBotones = new AnchorPane();

            // Crear y añadir botones manualmente
            Button boton0 = new Button("0");
            boton0.getStyleClass().add("button");
            boton0.setOnAction(new ManejadorBoton("0", calculadora));
            AnchorPane.setTopAnchor(boton0, 180.0);
            AnchorPane.setLeftAnchor(boton0, 5.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton0);

            Button boton1 = new Button("1");
            boton1.getStyleClass().add("button");
            boton1.setOnAction(new ManejadorBoton("1", calculadora));
            AnchorPane.setTopAnchor(boton1, 125.0);
            AnchorPane.setLeftAnchor(boton1, 5.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton1);

            Button boton2 = new Button("2");
            boton2.getStyleClass().add("button");
            boton2.setOnAction(new ManejadorBoton("2", calculadora));
            AnchorPane.setTopAnchor(boton2, 125.0);
            AnchorPane.setLeftAnchor(boton2, 60.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton2);

            Button boton3 = new Button("3");
            boton3.getStyleClass().add("button");
            boton3.setOnAction(new ManejadorBoton("3", calculadora));
            AnchorPane.setTopAnchor(boton3, 125.0);
            AnchorPane.setLeftAnchor(boton3, 115.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton3);

            Button boton4 = new Button("4");
            boton4.getStyleClass().add("button");
            boton4.setOnAction(new ManejadorBoton("4", calculadora));
            AnchorPane.setTopAnchor(boton4, 70.0);
            AnchorPane.setLeftAnchor(boton4, 5.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton4);

            Button boton5 = new Button("5");
            boton5.getStyleClass().add("button");
            boton5.setOnAction(new ManejadorBoton("5", calculadora));
            AnchorPane.setTopAnchor(boton5, 70.0);
            AnchorPane.setLeftAnchor(boton5, 60.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton5);

            Button boton6 = new Button("6");
            boton6.getStyleClass().add("button");
            boton6.setOnAction(new ManejadorBoton("6", calculadora));
            AnchorPane.setTopAnchor(boton6, 70.0);
            AnchorPane.setLeftAnchor(boton6, 115.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton6);

            Button boton7 = new Button("7");
            boton7.getStyleClass().add("button");
            boton7.setOnAction(new ManejadorBoton("7", calculadora));
            AnchorPane.setTopAnchor(boton7, 15.0);
            AnchorPane.setLeftAnchor(boton7, 5.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton7);

            Button boton8 = new Button("8");
            boton8.getStyleClass().add("button");
            boton8.setOnAction(new ManejadorBoton("8", calculadora));
            AnchorPane.setTopAnchor(boton8, 15.0);
            AnchorPane.setLeftAnchor(boton8, 60.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton8);

            Button boton9 = new Button("9");
            boton9.getStyleClass().add("button");
            boton9.setOnAction(new ManejadorBoton("9", calculadora));
            AnchorPane.setTopAnchor(boton9, 15.0);
            AnchorPane.setLeftAnchor(boton9, 115.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(boton9);

            Button botonDiv = new Button("/");
            botonDiv.getStyleClass().add("button");
            botonDiv.setOnAction(new ManejadorBoton("/", calculadora));
            AnchorPane.setTopAnchor(botonDiv, 15.0);
            AnchorPane.setLeftAnchor(botonDiv, 170.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(botonDiv);

            Button botonMul = new Button("*");
            botonMul.getStyleClass().add("button");
            botonMul.setOnAction(new ManejadorBoton("*", calculadora));
            AnchorPane.setTopAnchor(botonMul, 70.0);
            AnchorPane.setLeftAnchor(botonMul, 170.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(botonMul);

            Button botonSub = new Button("-");
            botonSub.getStyleClass().add("button");
            botonSub.setOnAction(new ManejadorBoton("-", calculadora));
            AnchorPane.setTopAnchor(botonSub, 125.0);
            AnchorPane.setLeftAnchor(botonSub, 170.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(botonSub);

            Button botonAdd = new Button("+");
            botonAdd.getStyleClass().add("button");
            botonAdd.setOnAction(new ManejadorBoton("+", calculadora));
            AnchorPane.setTopAnchor(botonAdd, 180.0);
            AnchorPane.setLeftAnchor(botonAdd, 170.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(botonAdd);

            Button botonEq = new Button("=");
            botonEq.getStyleClass().add("button");
            botonEq.setOnAction(new ManejadorBoton("=", calculadora));
            AnchorPane.setTopAnchor(botonEq, 180.0);
            AnchorPane.setLeftAnchor(botonEq, 115.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(botonEq);

            Button botonC = new Button("C");
            botonC.getStyleClass().add("button");
            botonC.setOnAction(new ManejadorBoton("C", calculadora));
            AnchorPane.setTopAnchor(botonC, 180.0);
            AnchorPane.setLeftAnchor(botonC, 60.0);  // Ajustar ancla izquierda
            anchorPaneBotones.getChildren().add(botonC);

            // Crear un AnchorPane para organizar la pantalla y el AnchorPane de botones
            AnchorPane root = new AnchorPane();
            AnchorPane.setTopAnchor(pantalla, 10.0); // Reducir espacio superior
            AnchorPane.setLeftAnchor(pantalla, 10.0); // Ajustar ancla izquierda para alinear con el primer botón
            AnchorPane.setTopAnchor(anchorPaneBotones, 30.0); // Desplazar hacia arriba
            AnchorPane.setLeftAnchor(anchorPaneBotones, 5.0); // Desplazar hacia la izquierda
            AnchorPane.setRightAnchor(anchorPaneBotones, 10.0); // Ajustar ancla derecha para centrar
            AnchorPane.setBottomAnchor(anchorPaneBotones, 10.0); // Ajustar ancla inferior para centrar
            root.getChildren().addAll(pantalla, anchorPaneBotones);

        // Añadir el root al VBox
        this.getChildren().add(root);

        // Aplicar estilos
        this.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());

        // Establecer el tamaño preferido
        this.setPrefSize(250, 310);
    }

    private void crearBotones(AnchorPane anchorPaneBotones) {
        String[] botones = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "C", "=", "+"};
        double[] topAnchors = {15, 15, 15, 15, 70, 70, 70, 70, 125, 125, 125, 125, 180, 180, 180, 180};
        double[] leftAnchors = {5, 60, 115, 170, 5, 60, 115, 170, 5, 60, 115, 170, 5, 60, 115, 170};

        for (int i = 0; i < botones.length; i++) {
            Button boton = new Button(botones[i]);
            boton.getStyleClass().add("button");
            boton.setOnAction(new ManejadorBoton(botones[i], calculadora));
            AnchorPane.setTopAnchor(boton, topAnchors[i]);
            AnchorPane.setLeftAnchor(boton, leftAnchors[i]);
            anchorPaneBotones.getChildren().add(boton);
        }
    }
}