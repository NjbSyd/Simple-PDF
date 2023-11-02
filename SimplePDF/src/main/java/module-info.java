module gok.simplepdf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires kernel;
    requires io;
    requires fontawesomefx;
    requires org.slf4j;

    opens APP.UI to javafx.fxml;
    exports APP.UI;
}