package com.lask.controller;

import com.lask.TaskEditApplication;
import com.lask.controller.util.TaskFileManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * The controller of the home screen of the application.
 * Manage the actions of the home screen of the application
 */
public class LaskController {

    public VBox root;

    /**
     * createNewTaskList : open the editing menu with a blank task list
     * @param actionEvent the triggering event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void createNewTaskList(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskEditApplication.class.getResource("task-visualization.fxml"));
        Parent newRoot = fxmlLoader.load();
        root.getScene().setRoot(newRoot);
    }

    /**
     * loadNewTaskList : open the file explorer and then the editing menu with given task list
     * @param actionEvent the triggering event
     * @throws IOException if the file cannot be loaded
     */
    @FXML
    public void loadNewTaskList(ActionEvent actionEvent) throws IOException {
        Parent newRoot = TaskFileManagement.getLoadedTaskFile(TaskFileManagement.getTaskListFromFileChooser(root.getScene().getWindow()));
        root.getScene().setRoot(newRoot);
    }
}