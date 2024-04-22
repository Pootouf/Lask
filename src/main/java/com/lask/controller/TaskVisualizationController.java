package com.lask.controller;

import com.lask.model.AbstractTaskFactory;
import com.lask.model.StdTaskFactory;
import com.lask.model.task.Task;
import com.lask.model.task.std.Priority;
import com.lask.view.DateEditingCell;
import com.lask.view.SubTaskCreationVisitor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TaskVisualizationController implements Initializable {

    @FXML
    private TreeTableView<Task> treeView;

    private final AbstractTaskFactory taskFactory;

    public TaskVisualizationController() {
        taskFactory = new StdTaskFactory();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTreeViewColumns();
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
    }

    private void createTreeViewColumns() {
        this.createTreeViewTaskColumn("Description", "description");
        this.createTreeViewTaskColumnIntegerFormat("Durée", "duration");
        this.createTreeViewTaskColumnIntegerFormat("Pourcentage de complétion", "completionPercentage");
        this.createTreeViewTaskColumnDateFormat("Date de fin", "endDate");
        this.createTreeViewTaskColumnEnumPriorityFormat("Priorité", "priority");
    }

    private void createTreeViewTaskColumn(String columnName, String propertyName) {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>(columnName);
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>(propertyName));
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        treeView.getColumns().add(column);
    }

    private void createTreeViewTaskColumnDateFormat(String columnName, String propertyName) {
        TreeTableColumn<Task, LocalDate> column = new TreeTableColumn<>(columnName);
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>(propertyName));
        column.setCellFactory(col -> new DateEditingCell());
        treeView.getColumns().add(column);
    }

    private void createTreeViewTaskColumnIntegerFormat(String columnName, String propertyName) {
        TreeTableColumn<Task, Integer> column = new TreeTableColumn<>(columnName);
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>(propertyName));
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
        treeView.getColumns().add(column);
    }

    private void createTreeViewTaskColumnEnumPriorityFormat(String columnName, String propertyName) {
        TreeTableColumn<Task, Priority> column = new TreeTableColumn<>(columnName);
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>(propertyName));
        column.setCellFactory(col -> {
            ComboBoxTreeTableCell<Task, Priority> tc = new ComboBoxTreeTableCell<Task, Priority>(
                    new StringConverter<Priority>() {
                        @Override
                        public String toString(Priority priority) {
                            return switch (priority) {
                                case NORMAL -> "NORMAL";
                                case SECONDARY -> "SECONDAIRE";
                                case URGENT -> "URGENT";
                            };
                        }

                        @Override
                        public Priority fromString(String s) {
                            switch (s) {
                                case "URGENT" : return Priority.URGENT;
                                case "NORMAL" : return Priority.NORMAL;
                                case "SECONDAIRE" : return Priority.SECONDARY;
                            };
                            throw new IllegalArgumentException("Not a valid string to receive");
                        }
                    }, FXCollections.observableArrayList(Priority.values())
            );
            tc.setComboBoxEditable(true);
            return tc;
        });
        treeView.getColumns().add(column);
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
            treeView.getRoot().getChildren().add(newItem);
        }
    }


}
