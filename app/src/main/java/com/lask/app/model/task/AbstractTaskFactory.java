package com.lask.app.model.task;

import com.lask.app.model.task.std.Priority;

import java.util.Date;

/**
 * Represents the abstraction of creation and instantiation of tasks by client.
 */
public interface AbstractTaskFactory {

    /**
     * createBasicTask : instantiation of a basic task
     * @param description String
     * @param endDate Date
     * @param priority Priority
     * @param duration int
     * @param percentage int
     * @return BasicTask
     */
    BasicTask createBasicTask(String description, Date endDate, Priority priority, int duration, int percentage);

    /**
     * createBooleanTask : instantiation of a boolean task
     * @param description String
     * @param endDate Date
     * @param priority Priority
     * @param duration int
     * @param finished boolean
     * @return BooleanTask
     */
    BooleanTask createBooleanTask(String description, Date endDate, Priority priority, int duration, boolean finished);

    /**
     * createComplexTask : instantiation of a complex task
     * @param description String
     * @param priority Priority
     * @return ComplexTask
     */
    ComplexTask createComplexTask(String description, Priority priority);


}
