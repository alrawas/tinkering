module com.example.javafxplayground {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.javafxplayground to javafx.fxml;
    exports com.example.javafxplayground;
}