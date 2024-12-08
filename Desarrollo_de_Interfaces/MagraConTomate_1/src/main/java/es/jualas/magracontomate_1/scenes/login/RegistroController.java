package es.jualas.magracontomate_1.scenes.login;

import es.jualas.magracontomate_1.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistroController {

    // Campo de texto para el nombre de usuario
    @FXML
    private TextField usuarioField;

    // Campo de texto para la contraseña oculta
    @FXML
    private PasswordField contrasenaField;

    // Campo de texto para la contraseña visible
    @FXML
    private TextField contasenaSinCiField;

    // Botón de login
    @FXML
    private Button btnLogin;

    // Imagen del ojo para mostrar/ocultar la contraseña
    @FXML
    private ImageView eyeClose;


    // Imagen para volver al menú principal
    @FXML
    private ImageView backButton;

    // Texto para mostrar mensajes de error o éxito
    @FXML
    private Text messageText;

    // Lista de usuarios predefinidos
    private List<Usuario> usuarios = new ArrayList<>();

    // Imágenes del ojo cerrado y abierto
    private Image eyeCloseImage;
    private Image eyeOpenImage;
    
    // Imagen para volver al menú principal
    private SceneManager sceneManager;

    // Método de inicialización
    public void initialize() {
        // Datos de usuarios predefinidos
        String[][] datosUsuarios = {
            {"Juan", "Pérez", "juan.perez@example.com", "password123"},
            {"María", "González", "maria.gonzalez@example.com", "password123"},
            {"José", "Rodríguez", "jose.rodriguez@example.com", "password123"},
            {"Ana", "Martínez", "ana.martinez@example.com", "password123"},
            {"Luis", "García", "luis.garcia@example.com", "password123"}
        };

        // Agregar usuarios a la lista
        for (String[] datos : datosUsuarios) {
            usuarios.add(new Usuario(datos[0], datos[1], datos[2], datos[3]));
        }

        // Imprimir usuarios en la consola
        usuarios.forEach(System.out::println);

        // Configurar evento para el campo de contraseña
        contrasenaField.setOnAction(this::handlerInputPassword);

        // Configurar evento para el botón de login
        btnLogin.setOnAction(event -> handlerLogin());

        // Listener para validar el campo de usuario
        usuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 3 || newValue.length() > 13) {
                usuarioField.getStyleClass().add("campoTextoRojo");
            } else {
                usuarioField.getStyleClass().removeAll("campoTextoRojo");
            }
        });

        // Sincronizar los campos de contraseña
        contrasenaField.textProperty().bindBidirectional(contasenaSinCiField.textProperty());

        // Cargar imágenes del ojo
        eyeCloseImage = new Image(getClass().getResourceAsStream("/es/jualas/magracontomate_1/scenes/login/images/eye_close.png"));
        eyeOpenImage = new Image(getClass().getResourceAsStream("/es/jualas/magracontomate_1/scenes/login/images/eye_open.png"));
        eyeClose.setImage(eyeCloseImage);
        
        // Configurar evento para volver al menú principal
        // Inicializar SceneManager
        sceneManager = SceneManager.getInstance();

        // Configurar evento para el botón de retorno
        backButton.setOnMouseClicked(this::handleBackButtonClick);

    }

    // Método para manejar el evento de login
    @FXML
    private void handlerLogin() {
        // Obtener valores de los campos de usuario y contraseña
        String nombreUsuario = usuarioField.getText();
        String contrasena = contrasenaField.getText();

        // Limpiar estilos anteriores
        messageText.getStyleClass().removeAll("loginErroneo", "loginCorrecto");

        // Verificar si los campos están vacíos
        if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
            messageText.setText("Por favor, complete todos los campos.");
            messageText.getStyleClass().add("loginErroneo");
        } else {
            // Buscar usuario en la lista de usuarios
            Usuario usuarioEncontrado = usuarios.stream()
                .filter(u -> u.checkNombre(nombreUsuario) && u.checkPassword(contrasena))
                .findFirst()
                .orElse(null);

            // Mostrar mensaje de éxito o error
            if (usuarioEncontrado != null) {
                messageText.setText("Login exitoso! Bienvenido, " + usuarioEncontrado.getNombre());
                messageText.getStyleClass().add("loginCorrecto");
                System.out.println("Usuario autenticado: " + usuarioEncontrado);
            } else {
                messageText.setText("Usuario o contraseña incorrectos.");
                messageText.getStyleClass().add("loginErroneo");
                System.out.println("Intento de login fallido para el usuario: " + nombreUsuario);
            }
        }
    }

    // Método para manejar el evento de entrada de contraseña
    @FXML
    private void handlerInputPassword(ActionEvent event) {
        // Disparar el evento de clic del botón de login
        this.btnLogin.fire();
    }

    // Método para mostrar/ocultar la contraseña
    @FXML
    private void mostrarContrseña(MouseEvent event) {
        if (contrasenaField.isVisible()) {
            contrasenaField.setVisible(false);
            contasenaSinCiField.setVisible(true);
            eyeClose.setImage(eyeOpenImage);
        } else {
            contrasenaField.setVisible(true);
            contasenaSinCiField.setVisible(false);
            eyeClose.setImage(eyeCloseImage);
        }
    }
    
   @FXML
private void handleBackButtonClick(MouseEvent event) {
    try {
        sceneManager.switchScene("main");
    } catch (Exception e) {
        System.err.println("Error al cambiar a la escena del menú principal: " + e.getMessage());
        // Opcionalmente, puedes mostrar un diálogo de error al usuario
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No se pudo volver al menú principal");
        alert.setContentText("Ocurrió un error inesperado: " + e.getMessage());
        alert.showAndWait();
    }
}
    
}