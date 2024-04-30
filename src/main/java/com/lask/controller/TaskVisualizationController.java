package com.lask.controller;

import com.lask.TaskEditApplication;
import com.lask.controller.util.TaskFileManagement;
import com.lask.model.AbstractTaskFactory;
import com.lask.model.StdTaskFactory;
import com.lask.model.task.Task;
import com.lask.model.task.TaskList;
import com.lask.model.task.std.Priority;
import com.lask.model.xml.BasicSaveXMLTaskVisitor;
import com.lask.view.TreeTableViewTaskInitializer;
import com.lask.view.task.visitor.CommitModificationTaskVisitor;
import com.lask.view.task.visitor.SubTaskDeletionVisitor;
import com.lask.view.task.visitor.SubTaskCreationVisitor;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * The controller of the editing and visualization of tasks.
 * Manage the task list for new file and opened files
 */
public class TaskVisualizationController implements Initializable {

    @FXML
    private Button finishButton;

    @FXML
    private VBox rootVBox;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button deleteButton;

    @FXML
    private TreeTableView<Task> treeView;

    private TaskList taskList;

    private final AbstractTaskFactory taskFactory;

    public TaskVisualizationController() {
        taskFactory = new StdTaskFactory();
        taskList = this.taskFactory.createTaskList();
    }

    /**
     * initialize : initialize the menu at the screen loading, create tree items, menu bar
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Menu menu = new Menu("Menu");
        MenuItem item1 = new MenuItem("Quitter l'application");
        MenuItem item2 = new MenuItem("Revenir à l'écran d'accueil");
        menu.getItems().add(item1);
        menu.getItems().add(item2);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        TreeTableViewTaskInitializer initializer = new TreeTableViewTaskInitializer(treeView);
        initializer.createTreeViewColumns();

        TreeItem<Task> root = new TreeItem<>(
                taskFactory.createBasicTask("root", null, null, 0, 0)
        );
        root.setExpanded(true);
        treeView.setRoot(root);
        treeView.setContextMenu(createContextMenu());
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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
        finishButton.disableProperty().bind(
                Bindings.isEmpty(
                        treeView.getSelectionModel().getSelectedItems()
                )
        );

    }

    /**
     * finishSelectedTask : set the completion percentage to 100% and finish the task
     * @param actionEvent the triggering event
     */
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

    /**
     * selectDirectoryToSaveFile : open file explorer to enable the user to
     * choose where and how to save the file
     * @param actionEvent the triggering event
     * @throws IOException when an error occurred while opening the file
     */
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

    /**
     * deleteTask : delete the selected task from the task list and the tree view
     * @param actionEvent, the triggering event
     */
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

    /**
     * returnToHomeScreen : open the home screen again and quit the editing menu
     * @param actionEvent, the triggering event
     * @throws IOException in case of wrong file opening
     */
    public void returnToHomeScreen(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskEditApplication.class.getResource("homepage.fxml"));
        Parent newRoot = fxmlLoader.load();
        rootVBox.getScene().setRoot(newRoot);
    }

    /**
     * openNewFile : reset the task list and clear the tree view
     * @param actionEvent, the triggering event
     */
    public void openNewFile(ActionEvent actionEvent) {
        taskList.getTaskList().clear();
        treeView.getRoot().getChildren().clear();
    }

    /**
     * openFile : open the file explorer for the user to choose task file and open it in editor
     * @param actionEvent the triggering event
     * @throws IOException when opening the file if an error occurred
     */
    public void openFile(ActionEvent actionEvent) throws IOException {
        taskList = TaskFileManagement.getTaskListFromFileChooser(rootVBox.getScene().getWindow());
        Parent newRoot = TaskFileManagement.getLoadedTaskFile(taskList);
        rootVBox.getScene().setRoot(newRoot);
    }

    /**
     * exit : terminate the application and return 0 code
     * @param actionEvent the triggering event
     */
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * openUserManual : open on browser the user manual of the application
     * @param actionEvent the triggering event
     */
    public void openUserManual(ActionEvent actionEvent) {
        if (Desktop.isDesktopSupported()) {
            Runnable runnable = () -> {
                try {
                    File myFile = new File("src/main/resources/com/lask/doc/manuel_utilisateur.pdf");
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // no application registered for PDFs
                }
            };
            new Thread(runnable).start();
        }
    }

    /**
     * setTaskList: set the task list associated with the controller
     * @param taskList the new task list
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * createContextMenu : create the context menu of the tree table view to allow the user to add
     *                      basic, complex or boolean task
     * @return the context menu
     */
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

    /**
     * manageNewTaskPlacement : place the task in the tree structure of the treeTableView and
     *                          synchronize it with the task list
     * @param newTask the task to add in tree structure
     */
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