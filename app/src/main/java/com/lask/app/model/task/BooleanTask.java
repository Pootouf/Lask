package com.lask.app.model.task;

/**
 * Represents a simple task with no progression state.
 */
public interface BooleanTask extends Task {

    /**
     * setFinished : change the state of the task
     * @param finished boolean, the new state
     */
    void setFinished(boolean finished);
}
