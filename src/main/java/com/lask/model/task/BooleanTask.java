package com.lask.model.task;

import java.time.LocalDate;

/**
 * Represents a simple task with no progression state.
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
