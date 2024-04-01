package com.lask.app.model.task.std;

import com.lask.app.model.task.BasicTask;

import java.util.Date;

public class StdBasicTask implements BasicTask {
    private final String desc;

    private final Date endDate;

    private final Priority priority;

    private final int duration;

    private int percentageCompletion;

    public StdBasicTask(String desc, Date endDate, Priority priority, int duration, int percentageCompletion) {
        this.desc = desc;
        this.endDate = endDate;
        this.priority = priority;
        this.duration = duration;
        this.percentageCompletion = percentageCompletion;
    }


    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public boolean isFinished() {
        return percentageCompletion == MAX_PERCENTAGE;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public int getCompletionPercentage() {
        return percentageCompletion;
    }

    @Override
    public void setCompletionPercentage(int percentage) {
        this.percentageCompletion = percentage;
    }


}
