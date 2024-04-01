package com.lask.app.model.task;

import com.lask.app.model.task.std.Priority;

import java.util.Date;

/**
 * Represents a task, something that need to be done. It can either be complex or simple.
 */
public interface Task {

    /**
     * getEndDate : return the estimated deadline for this task
     * @return Date
     */
    Date getEndDate();

    /**
     * getPriority : return the priority level for this task
     * @return Priority
     */
    Priority getPriority();

    /**
     * getDuration : return the estimated duration for completing this task
     * @return int
     */
    int getDuration();

    /**
     * isFinished : indicates if the task is finished or not
     * @return boolean
     */
    boolean isFinished();

    /**
     * getDescription : return the description of the task
     * @return String
     */
    String getDescription();
}
