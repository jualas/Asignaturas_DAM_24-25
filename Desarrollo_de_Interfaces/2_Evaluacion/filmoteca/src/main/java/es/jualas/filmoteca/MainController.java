package es.jualas.filmoteca;

import javafx.collections.ObservableList;
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
    private TextField titleTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Slider ratingSlider;
    @FXML
    private TextField ratingTextField;
    @FXML
    private TextField posterUrlTextField;
    @FXML
    private ImageView posterImageView;


@FXML
public void initialize() {
    // Configurar las columnas en la tabla
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
    ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

    // Formatear la columna de rating para mostrar solo un decimal
    ratingColumn.setCellFactory(column -> new TableCell<Pelicula, Float>() {
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

    // Cargar datos
    peliculasTableView.setItems(DatosFilmoteca.getInstancia().getListaPeliculas());

    // Añadir listener para actualizar los campos cuando se seleccione una película
    peliculasTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            titleTextField.setText(newValue.getTitle());
            yearTextField.setText(String.valueOf(newValue.getYear()));
            descriptionTextArea.setText(newValue.getDescription());
            ratingSlider.setValue(newValue.getRating());
            posterUrlTextField.setText(newValue.getPoster());
            posterImageView.setImage(new Image(newValue.getPoster()));
            ratingTextField.setText(String.format("%.1f", newValue.getRating()));
        }
    });

    // Añadir listener para actualizar el campo de texto con el valor del slider
    ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        ratingTextField.setText(String.format("%.1f", newValue.floatValue()));
    });
}

    @FXML
    protected void onPosterUrlEntered() {
        String url = posterUrlTextField.getText();
        if (url != null && !url.isEmpty()) {
            Image image = new Image(url);
            posterImageView.setImage(image);
        }
    }

    @FXML
    protected void onAddMovieClick() {
        String title = titleTextField.getText();
        int year = Integer.parseInt(yearTextField.getText());
        String description = descriptionTextArea.getText();
        float rating = (float) ratingSlider.getValue();
        String poster = posterUrlTextField.getText();

        Pelicula pelicula = new Pelicula(0, title, year, description, rating, poster);
        DatosFilmoteca.getInstancia().getListaPeliculas().add(pelicula);
    }

    @FXML
    protected void onEditMovieClick() {
        Pelicula selectedMovie = peliculasTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            selectedMovie.setTitle(titleTextField.getText());
            selectedMovie.setYear(Integer.parseInt(yearTextField.getText()));
            selectedMovie.setDescription(descriptionTextArea.getText());
            selectedMovie.setRating((float) ratingSlider.getValue());
            selectedMovie.setPoster(posterUrlTextField.getText());
            peliculasTableView.refresh();
        }
    }

    @FXML
    protected void onDeleteMovieClick() {
        Pelicula selectedMovie = peliculasTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            DatosFilmoteca.getInstancia().getListaPeliculas().remove(selectedMovie);
        }
    }
}