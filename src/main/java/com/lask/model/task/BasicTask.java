package com.lask.model.task;

import java.time.LocalDate;

/**
 * Represents a basic task.
 * A basic task is a progressive task, so it has completion percentage property editable.
 * Likewise, the end date and duration are also editable.
 */
public interface BasicTask extends Task {

    /**
     * setCompletionPercentage : set the percentage of completion of this task
     */
    void setCompletionPercentage(int percentage);

    /**
     * setEndDate : set the end date of the task
     * @param date
     */
    void setEndDate(LocalDate date);

    /**
     * setDuration : set the duration of the task
     * @param duration
     */
    void setDuration(Integer duration);
}
