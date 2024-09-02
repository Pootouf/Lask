module com.lask {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens com.lask to javafx.fxml;
    exports com.lask;
    exports com.lask.controller;
    opens com.lask.controller to javafx.fxml;
    opens com.lask.model.task.std;
}