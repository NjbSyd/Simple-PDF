module gok.simplepdf {
    requires javafx.controls;
    requires javafx.fxml;


    opens gok.simplepdf to javafx.fxml;
    exports gok.simplepdf;
}