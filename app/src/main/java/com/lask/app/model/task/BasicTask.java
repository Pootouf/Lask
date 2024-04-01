package com.lask.app.model.task;

public interface BasicTask extends ProgressiveTask {

    /**
     * setCompletionPercentage : set the percentage of completion of this task
     */
    void setCompletionPercentage(int percentage);

}
