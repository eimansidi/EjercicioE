module com.example.eje {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.eje to javafx.fxml;
    exports com.example.eje;
    exports com.example.eje.model;
    opens com.example.eje.model to javafx.fxml;
}