package com.lask.app.model.task;

public interface ProgressiveTask extends Task{

    /**
     * getCompletionPercentage : return the percentage of completion for this task
     * @return int
     */
    int getCompletionPercentage();

}
