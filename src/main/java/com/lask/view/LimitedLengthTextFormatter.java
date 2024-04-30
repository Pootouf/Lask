package com.lask.view;

import com.lask.model.task.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.StringConverter;

/**
 * Extension of the TextFieldTreeTableCell with Task and String parameterized types
 * Use a custom string converter to limit the length of the string
 */
public class LimitedLengthTextFormatter extends TextFieldTreeTableCell<Task, String> {

    public final int MAX_LENGTH = 20;

    public LimitedLengthTextFormatter() {
        this.setConverter(new StringConverter<>() {
            @Override
            public String toString(String object) {
                if (object == null) {
                    return "";
                }
                if (object.length() > MAX_LENGTH) {
                    return object.substring(0, MAX_LENGTH);
                }
                return object;
            }

            @Override
            public String fromString(String string) {
                if (string == null) {
                    return "";
                }
                if (string.length() > MAX_LENGTH) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Le texte est trop long");
                    errorAlert.setContentText("La limite de caractère est de 20");
                    errorAlert.showAndWait();
                    return string.substring(0, MAX_LENGTH);
                }
                return string;
            }
        });
    }
}