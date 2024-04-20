package com.lask.model;

import com.lask.model.AbstractTaskFactory;
import com.lask.model.task.BasicTask;
import com.lask.model.task.BooleanTask;
import com.lask.model.task.ComplexTask;
import com.lask.model.task.std.Priority;
import com.lask.model.task.std.StdBasicTask;
import com.lask.model.task.std.StdBooleanTask;
import com.lask.model.task.std.StdComplexTask;

import java.time.LocalDate;
import java.util.Date;

public class StdTaskFactory implements AbstractTaskFactory {
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
