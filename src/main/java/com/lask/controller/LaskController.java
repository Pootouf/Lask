package com.lask.controller;

import com.lask.TaskEditApplication;
import com.lask.controller.util.TaskFileManagement;
import com.lask.model.AbstractTaskFactory;
import com.lask.model.StdTaskBuilder;
import com.lask.model.StdTaskFactory;
import com.lask.model.task.Task;
import com.lask.model.task.TaskList;
import com.lask.model.xml.XMLTaskLoader;
import com.lask.view.task.visitor.TreeItemTaskVisitor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class LaskController {

    public VBox root;

    @FXML
    public void createNewTaskList(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskEditApplication.class.getResource("task-visualization.fxml"));
        Parent newRoot = fxmlLoader.load();
        root.getScene().setRoot(newRoot);
    }

    @FXML
    public void loadNewTaskList(ActionEvent actionEvent) throws IOException {
        Parent newRoot = TaskFileManagement.getLoadedTaskFile(root.getScene().getWindow());
        if (newRoot == null) {
            return;
        }
        root.getScene().setRoot(newRoot);
    }
}