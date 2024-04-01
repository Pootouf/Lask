package com.lask.app.model.task;

import com.lask.app.model.task.std.Priority;

import java.util.Date;

/**
 * Represents the abstraction of creation and instantiation of tasks by client.
 */
public interface AbstractTaskFactory {

    /**
     * createSimpleTask : instantiation of a simple task
     * @param description String
     * @param endDate Date
     * @param priority Priority
     * @param duration int
     * @return SimpleTask
     */
    SimpleTask createSimpleTask(String description, Date endDate, Priority priority, int duration);

    /**
     * createComplexTask : instantiation of a complex task
     * @param description String
     * @param priority Priority
     * @return ComplexTask
     */
    ComplexTask createComplexTask(String description, Priority priority);


}
