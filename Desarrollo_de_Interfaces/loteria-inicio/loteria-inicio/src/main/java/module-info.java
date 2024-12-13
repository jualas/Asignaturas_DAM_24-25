module com.profesor.loteria {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.profesor.loteria to javafx.fxml;
    exports com.profesor.loteria;
}