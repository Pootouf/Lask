package com.lask.app.model.task.std;

import com.lask.app.model.task.AbstractTaskFactory;
import com.lask.app.model.task.ComplexTask;
import com.lask.app.model.task.SimpleTask;

import java.util.Date;

public class StdTaskFactory implements AbstractTaskFactory {
    @Override
    public SimpleTask createSimpleTask(String description, Date endDate, Priority priority, int duration) {
        return new StdSimpleTask(description, endDate, priority, duration);
    }

    @Override
    public ComplexTask createComplexTask(String description, Priority priority) {
        return new StdComplexTask(description, priority);
    }
}
