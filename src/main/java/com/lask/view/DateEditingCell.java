package com.lask.view;

import com.lask.model.task.Task;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TreeTableCell;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateEditingCell extends TreeTableCell<Task, LocalDate> {

    private DatePicker datePicker;
    private String pattern = "dd/MM/yyyy";

    public DateEditingCell() {
    }

    @Override
    public void startEdit() {
        super.startEdit();
        createDatePicker();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        setText(dateTimeFormatter.format(getDate()));
        setGraphic(datePicker);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        setText(dateTimeFormatter.format(getDate()));
        setGraphic(null);
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                setText(dateTimeFormatter.format(getDate()));
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker(getDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            commitEdit(LocalDate.from(datePicker.getValue()));
        });
        datePicker.setConverter(new StringConverter<>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null) {
                    return dateFormatter.format(localDate);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    private LocalDate getDate() {
        return getItem() == null ? LocalDate.now() : getItem();
    }
}