module dam.alumno.filmoteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;



    opens es.jualas.filmoteca to javafx.fxml;
    exports es.jualas.filmoteca;
}