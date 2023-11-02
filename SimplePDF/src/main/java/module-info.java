module gok.simplepdf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires kernel;
    requires io;
    requires fontawesomefx;
    requires org.slf4j;

    opens gok.simplepdf_GUI to javafx.fxml;
    exports gok.simplepdf_GUI;
}