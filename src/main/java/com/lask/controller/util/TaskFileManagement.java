package com.lask.controller.util;

import com.lask.TaskEditApplication;
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

public class TaskFileManagement {

    public static Parent getLoadedTaskFile(Window window) throws IOException {
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

        FXMLLoader fxmlLoader = new FXMLLoader(TaskEditApplication.class.getResource("task-visualization.fxml"));
        Parent newRoot = fxmlLoader.load();
        TreeTableView<Task> tree = (TreeTableView<Task>) newRoot.lookup("#treeView");

        TaskList list = builder.getTaskList();
        TreeItemTaskVisitor visitor = new TreeItemTaskVisitor(tree.getRoot());
        visitor.visit(list);

        return newRoot;
    }
}
