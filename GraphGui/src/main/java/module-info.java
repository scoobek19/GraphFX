module app.graphgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.graphgui to javafx.fxml;
    exports app.graphgui;
}