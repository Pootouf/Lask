package com.lask.controller;

import com.lask.model.AbstractTaskFactory;
import com.lask.model.StdTaskFactory;
import com.lask.model.task.Task;
import com.lask.model.task.TaskList;
import com.lask.model.task.std.Priority;
import com.lask.model.xml.BasicSaveXMLTaskVisitor;
import com.lask.view.*;
import com.lask.view.task.visitor.CommitModificationTaskVisitor;
import com.lask.view.task.visitor.SubTaskCreationVisitor;
import com.lask.view.task.visitor.SubTaskDeletionVisitor;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TaskVisualizationController implements Initializable {

    @FXML
    private Button deleteButton;

    @FXML
    private TreeTableView<Task> treeView;

    private final TaskList taskList;

    private final AbstractTaskFactory taskFactory;

    public TaskVisualizationController() {
        taskFactory = new StdTaskFactory();
        taskList = this.taskFactory.createTaskList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeTableViewTaskInitializer initializer = new TreeTableViewTaskInitializer(treeView);
        initializer.createTreeViewColumns();

        TreeItem<Task> root = new TreeItem<>(
                taskFactory.createBasicTask("root", null, null, 0, 0)
        );
        root.setExpanded(true);
        treeView.setRoot(root);
        treeView.setContextMenu(createContextMenu());

        treeView.setRowFactory(tableView2 -> {
            final TreeTableRow<Task> row = new TreeTableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                if (!event.isPrimaryButtonDown() || event.getClickCount() >= 2) {
                    return;
                }
                final int index = row.getIndex();
                if (treeView.getSelectionModel().isSelected(index)) {
                    treeView.getSelectionModel().clearSelection();
                    event.consume();
                }
            });
            return row;
        });

        deleteButton.disableProperty().bind(
                Bindings.isEmpty(
                        treeView.getSelectionModel().getSelectedItems()
                )
        );

    }

    public void finishSelectedTask(ActionEvent actionEvent) {
        TreeItem<Task> selectedTask = treeView.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            return;
        }
        CommitModificationTaskVisitor visitor = new CommitModificationTaskVisitor(
                CommitModificationTaskVisitor.PROPERTY_FINISHED, true
        );
        visitor.visit(selectedTask.getValue());
        treeView.refresh();
    }

    public void selectDirectoryToSaveFile(ActionEvent actionEvent) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Où sauvegarder le fichier ?");

        File selectedFile = chooser.showSaveDialog(treeView.getScene().getWindow());
        if (selectedFile == null || !selectedFile.createNewFile()) {
            return;
        }
        BasicSaveXMLTaskVisitor saver = new BasicSaveXMLTaskVisitor(new FileOutputStream(selectedFile));
        saver.visit(taskList);
    }

    public void deleteTask(ActionEvent actionEvent) {
        TreeItem<Task> treeItem = treeView.getSelectionModel().getSelectedItem();
        TreeItem<Task> parent = treeItem.getParent();
        Task selectedItem = treeItem.getValue();
        if (parent == treeView.getRoot()) {
            taskList.removeTask(selectedItem);
        } else {
            Task parentTask = parent.getValue();
            SubTaskDeletionVisitor visitor = new SubTaskDeletionVisitor(selectedItem);
            parentTask.accept(visitor);
        }
        parent.getChildren().remove(treeItem);
    }


    private ContextMenu createContextMenu() {
        ContextMenu addMenu = new ContextMenu();

        MenuItem addMenuItem = new MenuItem("Add basic task");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction((ActionEvent t) -> {
            Task newTask = taskFactory.createBasicTask("Change moi !", LocalDate.now(), Priority.NORMAL, 1, 0);
            manageNewTaskPlacement(newTask);
        });

        addMenuItem = new MenuItem("Add boolean task");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction((ActionEvent t) -> {
            Task newTask = taskFactory.createBooleanTask("Change moi !", LocalDate.now(), Priority.NORMAL, 1, false);
            manageNewTaskPlacement(newTask);
        });

        addMenuItem = new MenuItem("Add complex task");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction((ActionEvent t) -> {
            Task newTask = taskFactory.createComplexTask("Change moi !", Priority.NORMAL);
            manageNewTaskPlacement(newTask);
        });

        return addMenu;
    }

    private void manageNewTaskPlacement(Task newTask) {
        TreeItem<Task> newItem = new TreeItem<>(newTask);
        TreeItem<Task> selectedTask = treeView.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            if (!selectedTask.getValue().isLeaf()) {
                return;
            }
            selectedTask.getChildren().add(newItem);
            new SubTaskCreationVisitor(newTask).visit(selectedTask.getValue());
        } else {
            taskList.addTask(newTask);
            treeView.getRoot().getChildren().add(newItem);
        }
        treeView.refresh();
    }
}
