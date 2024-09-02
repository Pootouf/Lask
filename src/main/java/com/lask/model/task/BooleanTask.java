package com.lask.model.task;

import java.time.LocalDate;

/**
 * Represents a simple task with no progression state.
 * A boolean task is not a progressive task, so it doesn't have completion percentage property editable.
 * But it can set the finished property, that will also set completion percentage to 0 or 100.
 * Likewise, the end date and duration are also editable.
 */
public interface BooleanTask extends Task {

    /**
     * setFinished : change the state of the task
     * @param finished boolean, the new state
     */
    void setFinished(boolean finished);

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
