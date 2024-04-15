module com.lask {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens com.lask to javafx.fxml;
    exports com.lask;
}