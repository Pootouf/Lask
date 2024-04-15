package com.lask.app.model.task;

import java.util.Date;

public interface TaskBuilder {

    /**
     * startBasicTask: create a basic task
     */
    void startBasicTask();

    /**
     * startBooleanTask: create a boolean task
     */
    void startBooleanTask();

    /**
     * startComplexTask: create a boolean task
     */
    void startComplexTask();

    /**
     * setDescription: set the description of the task
     * @param desc
     */
    void setDescription(String desc);

    /**
     * setEndDate: set the end date of the task
     * @param endDate
     */
    void setEndDate(Date endDate);

    /**
     * setDuration: set the duration of the task
     * @param duration
     */
    void setDuration(int duration);

    /**
     * setPriority: set the priority of the task
     * @param priority
     */
    void setPriority(int priority);

    /**
     * setFinished: set if the task is finished or not
     * @param finished
     */
    void setFinished(boolean finished);

    /**
     * setCompletionPercentage: set the completion percentage of the task
     * @param percentage
     */
    void setCompletionPercentage(int percentage);

    /**
     * createTask: create the currentTask
     */
    void createTask();

}
