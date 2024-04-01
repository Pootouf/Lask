package com.lask.app.model.task.std;

import com.lask.app.model.task.AbstractTaskFactory;
import com.lask.app.model.task.BasicTask;
import com.lask.app.model.task.BooleanTask;
import com.lask.app.model.task.ComplexTask;

import java.util.Date;

public class StdTaskFactory implements AbstractTaskFactory {
    @Override
    public BasicTask createBasicTask(String description, Date endDate, Priority priority, int duration) {
        return new StdBasicTask(description, endDate, priority, duration);
    }

    @Override
    public BooleanTask createBooleanTask(String description, Date endDate, Priority priority, int duration) {
        return new StdBooleanTask(description, endDate, priority, duration);
    }

    @Override
    public ComplexTask createComplexTask(String description, Priority priority) {
        return new StdComplexTask(description, priority);
    }
}
