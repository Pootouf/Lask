package com.lask.view;

import com.lask.model.task.Task;
import javafx.scene.control.cell.TextFieldTreeTableCell;

import java.util.function.UnaryOperator;

public class LimitedLengthTextFormatter extends TextFieldTreeTableCell<Task, String> {

    public final int MAX_LENGTH = 20;

    @Override
    public void updateItem(String item, boolean empty) {

        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            return;
        }

        if (!isEmpty()) {

            if (item.length() > MAX_LENGTH) {
                String s = getText().substring(0, MAX_LENGTH);
                setText(s);
            } else {
                setText(item);
            }

        }
    }
}