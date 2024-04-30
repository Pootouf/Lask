package com.lask.view;

import com.lask.model.task.Task;
import com.lask.model.task.std.Priority;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.util.StringConverter;

/**
 * Extension of the ComboBoxTreeTableCell with Task and Priority parameterized types
 * Use a custom string converter to show the priority to the user
 */
public class ComboBoxPriorityTreeTableCell extends ComboBoxTreeTableCell<Task, Priority> {

    public ComboBoxPriorityTreeTableCell() {
        super(new StringConverter<>() {
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
        }, FXCollections.observableArrayList(Priority.values()));
    }

}
