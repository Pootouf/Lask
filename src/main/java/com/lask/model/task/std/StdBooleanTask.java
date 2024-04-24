package com.lask.model.task.std;

import com.lask.model.TaskVisitor;
import com.lask.model.task.BooleanTask;

import java.time.LocalDate;


public class StdBooleanTask implements BooleanTask {

    private String desc;

    private final LocalDate endDate;

    private final Priority priority;

    private final int duration;

    private boolean isFinished;

    public StdBooleanTask(String desc, LocalDate endDate, Priority priority, int duration, boolean isFinished) {
        this.desc = desc;
        this.endDate = endDate;
        this.priority = priority;
        this.duration = duration;
        this.isFinished = isFinished;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public LocalDate getEndDate() {
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
    public void setDescription(String description) {
        this.desc = description;
    }

    @Override
    public void accept(TaskVisitor taskVisitor) {
        taskVisitor.visitBooleanTask(this);
    }
}
