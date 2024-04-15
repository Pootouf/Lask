package com.lask.app.model;

import com.lask.app.model.AbstractTaskFactory;
import com.lask.app.model.task.BasicTask;
import com.lask.app.model.task.BooleanTask;
import com.lask.app.model.task.ComplexTask;
import com.lask.app.model.task.std.Priority;
import com.lask.app.model.task.std.StdBasicTask;
import com.lask.app.model.task.std.StdBooleanTask;
import com.lask.app.model.task.std.StdComplexTask;

import java.util.Date;

public class StdTaskFactory implements AbstractTaskFactory {
    @Override
    public BasicTask createBasicTask(String description, Date endDate, Priority priority, int duration, int percentage) {
        return new StdBasicTask(description, endDate, priority, duration, percentage);
    }

    @Override
    public BooleanTask createBooleanTask(String description, Date endDate, Priority priority, int duration, boolean finished) {
        return new StdBooleanTask(description, endDate, priority, duration, finished);
    }

    @Override
    public ComplexTask createComplexTask(String description, Priority priority) {
        return new StdComplexTask(description, priority);
    }
}
