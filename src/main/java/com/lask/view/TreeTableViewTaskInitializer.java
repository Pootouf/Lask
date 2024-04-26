package com.lask.view;

import com.lask.model.task.Task;
import com.lask.model.task.std.Priority;
import com.lask.view.task.visitor.CommitModificationTaskVisitor;
import com.lask.view.task.visitor.DisabledPropertyTaskVisitor;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;

/**
 * TreeTableViewTaskInitializer : initialize a tree table view with columns to correspond to the properties of a task
 */
public class TreeTableViewTaskInitializer {

    private final TreeTableView<Task> treeTableView;

    public TreeTableViewTaskInitializer() {
        treeTableView = new TreeTableView<>();
    }

    public TreeTableViewTaskInitializer(TreeTableView<Task> treeTableView) {
        this.treeTableView = treeTableView;
    }

    public void createTreeViewColumns() {
        this.createTreeViewTaskColumnDescription();
        this.createTreeViewTaskColumnDuration();
        this.createTreeViewTaskColumnCompletionPercentage();
        this.createTreeViewTaskColumnEndDateFormat();
        this.createTreeViewTaskColumnEnumPriorityFormat();
    }

    /**
     * createTreeViewTaskColumnDescription: add the column description into the tree table
     */
    private void createTreeViewTaskColumnDescription() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Description");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        column.setCellFactory(e -> createEventFilterToEditCell(new LimitedLengthTextFormatter()));
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_DESCRIPTION)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_DESCRIPTION, event));
        treeTableView.getColumns().add(column);
    }

    /**
     * createTreeViewTaskColumnEndDateFormat: add the column end date into the tree table
     */
    private void createTreeViewTaskColumnEndDateFormat() {
        TreeTableColumn<Task, LocalDate> column = new TreeTableColumn<>("Date de fin");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("endDate"));
        column.setCellFactory(col -> createEventFilterToEditCell(new DateEditingCell()));
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_END_DATE)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_END_DATE, event));
        treeTableView.getColumns().add(column);
    }

    /**
     * createTreeViewTaskColumnDuration: add the column duration into the tree table
     */
    private void createTreeViewTaskColumnDuration() {
        TreeTableColumn<Task, Integer> column = new TreeTableColumn<>("Durée");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("duration"));
        column.setCellFactory(col -> createEventFilterToEditCell(new TextFieldTreeTableCell<>(new IntegerStringConverter())));
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_DURATION)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_DURATION, event));
        treeTableView.getColumns().add(column);
    }

    /**
     * createTreeViewTaskColumnCompletionPercentage: add the column completion percentage into the tree table
     */
    private void createTreeViewTaskColumnCompletionPercentage() {
        TreeTableColumn<Task, Integer> column = new TreeTableColumn<>("Pourcentage de complétion");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("completionPercentage"));
        column.setCellFactory(col -> createEventFilterToEditCell(new TextFieldTreeTableCell<>(new IntegerStringConverter())));
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_COMPLETION_PERCENTAGE)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_COMPLETION_PERCENTAGE, event));
        treeTableView.getColumns().add(column);
    }

    /**
     * createTreeViewTaskColumnEnumPriorityFormat: add the column priority into the tree table
     */
    private void createTreeViewTaskColumnEnumPriorityFormat() {
        TreeTableColumn<Task, Priority> column = new TreeTableColumn<>("Priorité");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("priority"));
        column.setCellFactory(col -> createEventFilterToEditCell(new ComboBoxPriorityTreeTableCell()));
        column.setOnEditCommit(value -> value.getRowValue().getValue().setPriority(value.getNewValue()));
        column.setOnEditCommit(value -> commitValueInTask(
                value.getRowValue().getValue(), value.getNewValue(), CommitModificationTaskVisitor.PROPERTY_PRIORITY)
        );
        column.setOnEditStart((event) -> cancelEditIfTaskPropertyNotEditable(DisabledPropertyTaskVisitor.PROPERTY_PRIORITY, event));
        treeTableView.getColumns().add(column);
    }

    /**
     * createEventFilterToEditCell: make the double click on a cell edit the cell
     *                              this method is used to fix a JavaFX bug (a node with children can no longer be edited,
     *                              because double click will expand the node)
     * @param cell the cell that will be editable
     * @return the cell
     * @param <S> the type of the row of the cell
     * @param <T> the type of the column of the cell
     */
    private <S, T> TreeTableCell<S, T> createEventFilterToEditCell(TreeTableCell<S, T> cell) {
        cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
                cell.startEdit();
                event.consume();
            }
        });
        return cell;
    }

    /**
     * cancelEditIfTaskPropertyNotEditable: if the property of the task associated with the event is not editable,
     *                                      cancel edition and consume the event
     * @param property the property to lookup
     * @param event the edition event
     */
    private void cancelEditIfTaskPropertyNotEditable(String property, TreeTableColumn.CellEditEvent<Task, ?> event) {
        DisabledPropertyTaskVisitor visitor = new DisabledPropertyTaskVisitor(property);
        visitor.visit(event.getRowValue().getValue());
        if (visitor.isDisabled()) {
            event.consume();
            treeTableView.refresh();
        }
    }

    /**
     * commitValueInTask: modify the selected property of the selected task with the new value
     * @param task the task to modify
     * @param newValue the new value
     * @param propertyName the property to modify
     */
    private void commitValueInTask(Task task, Object newValue, String propertyName) {
        CommitModificationTaskVisitor visitor = new CommitModificationTaskVisitor(propertyName, newValue);
        visitor.visit(task);
        treeTableView.refresh();
    }

}
