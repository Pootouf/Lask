package com.lask.app.model.task.std;

import com.lask.app.model.TaskVisitor;
import com.lask.app.model.task.BooleanTask;

import java.util.Date;

public class StdBooleanTask implements BooleanTask {

    private final String desc;

    private final Date endDate;

    private final Priority priority;

    private final int duration;

    private boolean isFinished;

    public StdBooleanTask(String desc, Date endDate, Priority priority, int duration, boolean isFinished) {
        this.desc = desc;
        this.endDate = endDate;
        this.priority = priority;
        this.duration = duration;
        this.isFinished = isFinished;
    }

    @Override
    public void setFinished(boolean finished) {
        isFinished = finished;
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
        return isFinished;
    }

    @Override
    public int getCompletionPercentage() {
        return isFinished ? 100 : 0;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public void accept(TaskVisitor taskVisitor) {
        taskVisitor.visitBooleanTask(this);
    }
}
