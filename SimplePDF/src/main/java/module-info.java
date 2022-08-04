module gok.simplepdf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires kernel;


    opens gok.simplepdf_GUI to javafx.fxml;
    exports gok.simplepdf_GUI;
}