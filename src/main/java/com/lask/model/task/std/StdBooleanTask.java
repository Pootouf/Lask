package com.lask.model.task.std;

import com.lask.model.TaskVisitor;
import com.lask.model.task.BooleanTask;

import java.time.LocalDate;

/**
 * The implementation of the boolean task interface.
 * <p>
 * desc != null
 * priority != null
 * endDate != null
 * The percentage completion is derived from finished property.
 * If finished is true => percentage completion = 100
 * Else => percentage completion = 0
 * </p>
 */
public class StdBooleanTask implements BooleanTask {

    private String desc;

    private LocalDate endDate;

    private Priority priority;

    private int duration;

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
    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }

    @Override
    public void setDuration(Integer duration) {
        this.duration = duration;
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
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void accept(TaskVisitor taskVisitor) {
        taskVisitor.visitBooleanTask(this);
    }
}
