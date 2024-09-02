package com.lask.controller.util;

import com.lask.TaskEditApplication;
import com.lask.controller.TaskVisualizationController;
import com.lask.model.AbstractTaskFactory;
import com.lask.model.StdTaskBuilder;
import com.lask.model.StdTaskFactory;
import com.lask.model.task.Task;
import com.lask.model.task.TaskList;
import com.lask.model.xml.XMLTaskLoader;
import com.lask.view.task.visitor.TreeItemTaskVisitor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeTableView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

/**
 * Manage the file opening and processing with JavaFX.
 */
public class TaskFileManagement {

    /**
     * getTaskListFromFileChooser: open the explorer and process the given file to transform it into task list
     * @param window the window of the application used to open the file chooser
     * @return the task list
     */
    public static TaskList getTaskListFromFileChooser(Window window) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Charger quel fichier ?");

        File selectedFile = chooser.showOpenDialog(window);
        if (selectedFile == null || !selectedFile.isFile()) {
            return null;
        }
        AbstractTaskFactory factory = new StdTaskFactory();
        StdTaskBuilder builder = new StdTaskBuilder(factory);
        XMLTaskLoader loader = new XMLTaskLoader(builder);
        loader.loadFile(selectedFile);

        return builder.getTaskList();
    }

    /**
     * getTaskEditViewFromTaskList : return the editing view of the selected task list
     * @param taskList the task list to edit
     * @return the editing view of the selected task list
     * @throws IOException if the file cannot be loaded
     */
    public static Parent getTaskEditViewFromTaskList(TaskList taskList) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskEditApplication.class.getResource("task-visualization.fxml"));
        Parent newRoot = fxmlLoader.load();
        ((TaskVisualizationController) fxmlLoader.getController()).setTaskList(taskList);
        TreeTableView<Task> tree = (TreeTableView<Task>) newRoot.lookup("#treeView");
        TreeItemTaskVisitor visitor = new TreeItemTaskVisitor(tree.getRoot());
        visitor.visit(taskList);
        return newRoot;
    }
}
