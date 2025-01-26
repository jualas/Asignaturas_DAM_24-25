package es.jualas.filmoteca;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

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
            }
        });
    }

    @FXML
    protected void onAddMovieClick() {
        // Añadir una nueva película a la lista
        String title = titleLabel.getText();
        int year = Integer.parseInt(yearLabel.getText());
        String description = descriptionTextArea.getText();
        float rating = Float.parseFloat(ratingLabel.getText());
        String poster = ""; // Poster URL is not being set from the UI

        Pelicula pelicula = new Pelicula(0, title, year, description, rating, poster);
        DatosFilmoteca.getInstancia();
        DatosFilmoteca.getListaPeliculas().add(pelicula);
    }

    @FXML
    protected void onEditMovieClick() {
        // Editar la película seleccionada en la lista
        Pelicula selectedMovie = peliculasTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            selectedMovie.setTitle(titleLabel.getText());
            selectedMovie.setYear(Integer.parseInt(yearLabel.getText()));
            selectedMovie.setDescription(descriptionTextArea.getText());
            selectedMovie.setRating(Float.parseFloat(ratingLabel.getText()));
            // Poster URL is not being set from the UI
            peliculasTableView.refresh();
        }
    }

    @FXML
    protected void onDeleteMovieClick() {
        // Eliminar la película seleccionada de la lista
        Pelicula selectedMovie = peliculasTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            DatosFilmoteca.getInstancia();
            DatosFilmoteca.getListaPeliculas().remove(selectedMovie);
        }
    }
}