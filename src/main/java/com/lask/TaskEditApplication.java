package com.lask;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The application to edit to do lists and open them.
 */
public class TaskEditApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskEditApplication.class.getResource("homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
        stage.setTitle("Lask");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}