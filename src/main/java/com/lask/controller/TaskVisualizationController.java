package com.lask.controller;

import com.lask.model.task.Task;
import com.lask.model.task.std.Priority;
import com.lask.model.task.std.StdBasicTask;
import com.lask.view.DateEditingCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class TaskVisualizationController implements Initializable {

    @FXML
    private TreeTableView<Task> treeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTreeViewColumns();
        TreeItem<Task> root = new TreeItem<>(new StdBasicTask("eee", LocalDate.EPOCH, Priority.NORMAL, 1, 15));
        root.setExpanded(true);
        treeView.setRoot(root);
    }

    private void createTreeViewColumns() {
        this.createTreeViewTaskColumn("Description", "description");
        this.createTreeViewTaskColumnIntegerFormat("Durée", "duration");
        this.createTreeViewTaskColumnIntegerFormat("Pourcentage de complétion", "completionPercentage");
        this.createTreeViewTaskColumnDateFormat("Date de fin", "endDate");
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
}
