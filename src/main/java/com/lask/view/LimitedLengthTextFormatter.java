package com.lask.view;

import com.lask.model.task.Task;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.converter.DefaultStringConverter;

public class LimitedLengthTextFormatter extends TextFieldTreeTableCell<Task, String> {

    public final int MAX_LENGTH = 20;

    public LimitedLengthTextFormatter() {
        this.setConverter(new DefaultStringConverter());
    }

    @Override
    public void updateItem(String item, boolean empty) {
        if (isEmpty() || item == null) {
            super.updateItem(item, empty);
        } else {
            if (item.length() > MAX_LENGTH) {
                item = getItem();
            }
            super.updateItem(item, empty);
        }
    }
}