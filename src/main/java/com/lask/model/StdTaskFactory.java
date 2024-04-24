package com.lask.model;

import com.lask.model.task.BasicTask;
import com.lask.model.task.BooleanTask;
import com.lask.model.task.ComplexTask;
import com.lask.model.task.TaskList;
import com.lask.model.task.std.*;

import java.time.LocalDate;

public class StdTaskFactory implements AbstractTaskFactory {
    @Override
    public TaskList createTaskList() {
        return new StdTaskList();
    }

    @Override
    public BasicTask createBasicTask(String description, LocalDate endDate, Priority priority, int duration, int percentage) {
        return new StdBasicTask(description, endDate, priority, duration, percentage);
    }

    @Override
    public BooleanTask createBooleanTask(String description, LocalDate endDate, Priority priority, int duration, boolean finished) {
        return new StdBooleanTask(description, endDate, priority, duration, finished);
    }

    @Override
    public ComplexTask createComplexTask(String description, Priority priority) {
        return new StdComplexTask(description, priority);
    }
}
