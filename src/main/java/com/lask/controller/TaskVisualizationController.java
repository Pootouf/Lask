package com.lask.controller;

import com.lask.model.AbstractTaskFactory;
import com.lask.model.StdTaskFactory;
import com.lask.model.task.Task;
import com.lask.model.task.TaskList;
import com.lask.model.task.std.Priority;
import com.lask.model.xml.BasicSaveXMLTaskVisitor;
import com.lask.view.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

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

        deleteButton.disableProperty().bind(
                Bindings.isEmpty(
                        treeView.getSelectionModel().getSelectedItems()
                )
        );

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

    private void createTreeViewColumns() {
        this.createTreeViewTaskColumnDescription();
        this.createTreeViewTaskColumnDuration();
        this.createTreeViewTaskColumnCompletionPercentage();
        this.createTreeViewTaskColumnEndDateFormat();
        this.createTreeViewTaskColumnEnumPriorityFormat();
    }

    private void createTreeViewTaskColumnDescription() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Description");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        column.setCellFactory(e -> new LimitedLengthTextFormatter()
        );
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_DESCRIPTION)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_DESCRIPTION, event));
        treeView.getColumns().add(column);
    }

    private void createTreeViewTaskColumnEndDateFormat() {
        TreeTableColumn<Task, LocalDate> column = new TreeTableColumn<>("Date de fin");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("endDate"));
        column.setCellFactory(col -> new DateEditingCell());
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_END_DATE)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_END_DATE, event));
        treeView.getColumns().add(column);
    }

    private void createTreeViewTaskColumnDuration() {
        TreeTableColumn<Task, Integer> column = new TreeTableColumn<>("Durée");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("duration"));
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_DURATION)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_DURATION, event));
        treeView.getColumns().add(column);
    }

    private void createTreeViewTaskColumnCompletionPercentage() {
        TreeTableColumn<Task, Integer> column = new TreeTableColumn<>("Pourcentage de complétion");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("completionPercentage"));
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_COMPLETION_PERCENTAGE)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_COMPLETION_PERCENTAGE, event));
        treeView.getColumns().add(column);
    }

    private void createTreeViewTaskColumnEnumPriorityFormat() {
        TreeTableColumn<Task, Priority> column = new TreeTableColumn<>("Priorité");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("priority"));
        column.setCellFactory(col -> {
            ComboBoxTreeTableCell<Task, Priority> tc = new ComboBoxTreeTableCell<>(
                    new StringConverter<>() {
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
                            return switch (s) {
                                case "URGENT" -> Priority.URGENT;
                                case "NORMAL" -> Priority.NORMAL;
                                case "SECONDAIRE" -> Priority.SECONDARY;
                                default -> throw new IllegalArgumentException("Not a valid string to receive");
                            };
                        }
                    }, FXCollections.observableArrayList(Priority.values())
            );
            tc.setComboBoxEditable(true);
            return tc;
        });
        column.setOnEditCommit(value -> value.getRowValue().getValue().setPriority(value.getNewValue()));
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_PRIORITY)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_PRIORITY, event));
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
            taskList.addTask(newTask);
            treeView.getRoot().getChildren().add(newItem);
        }
        treeView.refresh();
    }

    private void cancelEditIfTaskPropertyNotEditable(String property, TreeTableColumn.CellEditEvent<Task, ?> event) {
        DisabledPropertyTaskVisitor visitor = new DisabledPropertyTaskVisitor(property);
        visitor.visit(event.getRowValue().getValue());
        if (visitor.isDisabled()) {
            event.consume();
            treeView.refresh();
        }
    }

    private void commitValueInTask(Task task, Object newValue, String propertyName) {
        CommitModificationTaskVisitor visitor = new CommitModificationTaskVisitor(propertyName, newValue);
        visitor.visit(task);
        treeView.refresh();
    }
}
