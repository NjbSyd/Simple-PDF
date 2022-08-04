module gok.simplepdf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens gok.simplepdf_GUI to javafx.fxml;
    exports gok.simplepdf_GUI;
}