package com.lask.model.task.std;

import com.lask.model.TaskVisitor;
import com.lask.model.task.BasicTask;

import java.time.LocalDate;

/**
 * The implementation of the basic task interface.
 *  desc != null
 *  priority != null
 *  endDate != null
 */
public class StdBasicTask implements BasicTask {
    private String desc;

    private LocalDate endDate;

    private Priority priority;

    private int duration;

    private int percentageCompletion;

    public StdBasicTask(String desc, LocalDate endDate, Priority priority, int duration, int percentageCompletion) {
        this.desc = desc;
        this.endDate = endDate;
        this.priority = priority;
        this.duration = duration;
        this.percentageCompletion = percentageCompletion;
    }

    @Override
    public boolean isLeaf() {
        return false;
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
        return percentageCompletion == MAX_PERCENTAGE;
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
    public int getCompletionPercentage() {
        return percentageCompletion;
    }

    @Override
    public void setCompletionPercentage(int percentage) {
        this.percentageCompletion = percentage;
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
    public void accept(TaskVisitor taskVisitor) {
        taskVisitor.visitBasicTask(this);
    }

}
