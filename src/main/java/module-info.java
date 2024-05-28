module com.coursework {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.coursework to javafx.fxml;
    exports com.coursework;
}
