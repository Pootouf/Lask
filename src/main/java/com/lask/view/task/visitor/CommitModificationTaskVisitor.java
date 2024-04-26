package com.lask.view.task.visitor;

import com.lask.model.TaskVisitor;
import com.lask.model.task.BasicTask;
import com.lask.model.task.BooleanTask;
import com.lask.model.task.ComplexTask;
import com.lask.model.task.Task;
import com.lask.model.task.std.Priority;

import java.time.LocalDate;

public class CommitModificationTaskVisitor implements TaskVisitor {
    public static final String PROPERTY_DESCRIPTION = "Description";
    public static final String PROPERTY_END_DATE = "End date";
    public static final String PROPERTY_PRIORITY = "Priority";
    public static final String PROPERTY_DURATION = "Duration";
    public static final String PROPERTY_FINISHED = "Finished";
    public static final String PROPERTY_COMPLETION_PERCENTAGE = "Completion Percentage";

    private final String taskPropertyName;
    private final Object taskPropertyValue;

    public CommitModificationTaskVisitor(String taskPropertyName, Object value) {
        this.taskPropertyName = taskPropertyName;
        this.taskPropertyValue = value;
    }

    public void visit(Task task) {
        task.accept(this);
    }

    @Override
    public void visitBasicTask(BasicTask basicTask) {
        switch (taskPropertyName) {
            case PROPERTY_DESCRIPTION -> basicTask.setDescription((String) taskPropertyValue);
            case PROPERTY_END_DATE -> basicTask.setEndDate((LocalDate) taskPropertyValue);
            case PROPERTY_PRIORITY -> basicTask.setPriority((Priority) taskPropertyValue);
            case PROPERTY_DURATION -> basicTask.setDuration((Integer) taskPropertyValue);
            case PROPERTY_COMPLETION_PERCENTAGE -> basicTask.setCompletionPercentage((Integer) taskPropertyValue);
        }
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        switch (taskPropertyName) {
            case PROPERTY_DESCRIPTION -> booleanTask.setDescription((String) taskPropertyValue);
            case PROPERTY_END_DATE -> booleanTask.setEndDate((LocalDate) taskPropertyValue);
            case PROPERTY_PRIORITY -> booleanTask.setPriority((Priority) taskPropertyValue);
            case PROPERTY_DURATION -> booleanTask.setDuration((Integer) taskPropertyValue);
            case PROPERTY_FINISHED -> booleanTask.setFinished((Boolean) taskPropertyValue);
        }
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        switch (taskPropertyName) {
            case PROPERTY_DESCRIPTION -> complexTask.setDescription((String) taskPropertyValue);
            case PROPERTY_PRIORITY -> complexTask.setPriority((Priority) taskPropertyValue);
        }
    }
}
