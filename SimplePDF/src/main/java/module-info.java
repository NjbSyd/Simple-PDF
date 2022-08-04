module gok.simplepdf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires kernel;
    requires io;
    requires layout;
    requires fr.opensagres.poi.xwpf.converter.pdf;
    requires org.apache.poi.ooxml;
    requires commons;
    requires fr.opensagres.poi.xwpf.converter.core;
    requires forms;

    opens gok.simplepdf_GUI to javafx.fxml;
    exports gok.simplepdf_GUI;
}