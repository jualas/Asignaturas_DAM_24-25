module es.jualas.magracontomate_1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens es.jualas.magracontomate_1 to javafx.fxml;
    opens es.jualas.magracontomate_1.scenes.login to javafx.fxml;
    opens es.jualas.magracontomate_1.scenes.calculadoraFX to javafx.fxml;
    opens es.jualas.magracontomate_1.scenes.todoprimos to javafx.fxml;
    opens es.jualas.magracontomate_1.scenes.examen_2023 to javafx.fxml;

    exports es.jualas.magracontomate_1;
    exports es.jualas.magracontomate_1.scenes.login;
    exports es.jualas.magracontomate_1.scenes.calculadoraFX;
    exports es.jualas.magracontomate_1.scenes.todoprimos;
    exports es.jualas.magracontomate_1.scenes.examen_2023;
}