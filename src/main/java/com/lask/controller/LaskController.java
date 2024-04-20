package com.lask.controller;

import com.lask.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LaskController {

    public VBox root;

    @FXML
    public void createNewTaskList(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("task-visualization.fxml"));
        Parent newRoot = fxmlLoader.load();
        root.getScene().setRoot(newRoot);
    }

    @FXML
    public void loadNewTaskList(ActionEvent actionEvent) {
    }
}