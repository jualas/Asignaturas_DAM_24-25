package es.jualas.filmoteca;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private TableView<Pelicula> peliculasTableView;
    @FXML
    private TableColumn<Pelicula, Integer> idColumn;
    @FXML
    private TableColumn<Pelicula, String> titleColumn;
    @FXML
    private TableColumn<Pelicula, Integer> yearColumn;
    @FXML
    private TableColumn<Pelicula, Float> ratingColumn;
    @FXML
    private ImageView posterImageView;
    @FXML
    private TextField posterUrlTextField;

    @FXML
    private Label titleLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label ratingLabel;

@FXML
public void initialize() {
    // Configurar las columnas en la tabla con las propiedades de la clase Pelicula
    idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
    titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
    ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
    // Formatear la columna de rating para mostrar solo un decimal
    ratingColumn.setCellFactory(column -> new TableCell<>() {
        @Override
        protected void updateItem(Float item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(String.format("%.1f", item));
            }
        }
    });

    // Cargar datos en la tabla desde la lista de películas
    DatosFilmoteca.getInstancia();
    peliculasTableView.setItems(DatosFilmoteca.getListaPeliculas());

    // Añadir listener para actualizar los campos cuando se seleccione una película
    peliculasTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            titleLabel.setText(newValue.getTitle());
            yearLabel.setText(String.valueOf(newValue.getYear()));
            descriptionTextArea.setText(newValue.getDescription());
            ratingLabel.setText(String.format("%.1f", newValue.getRating()));
            posterImageView.setImage(new Image(newValue.getPoster()));
            posterUrlTextField.setText(newValue.getPoster()); // Add this line
        }
    });
}

    @FXML
    protected void onEditMovieClick() {
        Pelicula selectedMovie = peliculasTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            openEditWindow(selectedMovie);
        } else {
            // Mostrar un mensaje de error si no se selecciona ninguna película
            showAlert("A sí NO", "CHACHO quieres estar en lo que estas, selecciona primero para poder editar.");
        }
    }

    @FXML
    protected void onNewMovieClick() {
        Pelicula nuevaPelicula = new Pelicula();
        openEditWindow(nuevaPelicula);
    }

    private void openEditWindow(Pelicula pelicula) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/jualas/filmoteca/edit-view.fxml"));
            Parent root = loader.load();

            EditController editController = loader.getController();
            editController.setPelicula(pelicula);

            Stage stage = new Stage();
            stage.setTitle(pelicula.getId() == 0 ? "Nueva Película" : "Editar Película");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Actualizar la tabla de películas después de cerrar la ventana de edición
            peliculasTableView.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la ventana de edición: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void onDeleteMovieClick() {
        // Obtener la película seleccionada
        Pelicula selectedMovie = peliculasTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            // Mostrar una alerta de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación");
            alert.setHeaderText(null);
            alert.setContentText("Toda decisión que se adopta genera consecuencias, ¿MAESTRO!!!! está usted seguro de eliminar esta Película?");

            // Esperar la respuesta del usuario
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Eliminar la película si el usuario confirma
                    DatosFilmoteca.getInstancia();
                    DatosFilmoteca.getListaPeliculas().remove(selectedMovie);
                }
            });
        } else {
            // Mostrar un mensaje de advertencia si no se selecciona ninguna película
            showAlert("Advertencia", "CHACHO!!!! vamos a estar a lo que estamos " +
                    "" +
                    " selecciona una película anda MÁQUINA.");
        }
    }

@FXML
public void onCloseAppClick(ActionEvent actionEvent) {
    // Mostrar una alerta de confirmación
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmación de cierre");
    alert.setHeaderText(null);
    alert.setContentText("Toda decisión que se adopta genera consecuencias, ¿CAPITAN!!!! estas seguro de querer cerrar la aplicación?");

    // Esperar la respuesta del usuario
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            // Guardar los datos antes de cerrar la aplicación
            DatosFilmoteca.getInstancia().guardarDatos();
            // Cerrar la aplicación
            ((Stage) peliculasTableView.getScene().getWindow()).close();
        }
    });
}
}