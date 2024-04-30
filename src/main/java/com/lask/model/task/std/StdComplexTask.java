package com.lask.model.task.std;

import com.lask.model.TaskVisitor;
import com.lask.model.task.ComplexTask;
import com.lask.model.task.Task;

import java.time.LocalDate;

/**
 * The implementation of a complex task.
 *
 * <p>
 * desc != null
 * priority != null
 * The duration is a sum of the children duration
 * The completion percentage is a average of the completion percentage
 *      of the sub tasks weighted by their duration.
 * The end date of the complex task is the maximum of its children end date,
 *      or the actual date if there are no children.
 * </p>
 */
public class StdComplexTask extends ComplexTask {

    private String desc;

    private Priority priority;


    public StdComplexTask(String desc, Priority priority) {
        if(desc == null || priority == null) {
            throw new IllegalArgumentException("Not valid arguments for complex task");
        }
        this.desc = desc;
        this.priority = priority;
    }

    @Override
    public int getCompletionPercentage() {
        int completedTasks = 0;
        if (this.getSubTasksNumber() == 0) {
            return 100;
        }
        for (Task task : this.getSubTasks()) {
            completedTasks += task.isFinished() ? 1 : 0;
        }
        return (completedTasks / this.getSubTasksNumber()) * MAX_PERCENTAGE;
    }

    @Override
    public LocalDate getEndDate() {
        LocalDate max = LocalDate.MIN;
        for(Task task : this.getSubTasks()) {
            if(max.isBefore(task.getEndDate())) {
                max = task.getEndDate();
            }
        }
        if (max.isEqual(LocalDate.MIN)) {
            max = LocalDate.now();
        }
        return max;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int getDuration() {
        int sum = 0;
        for(Task task : this.getSubTasks()) {
            sum += task.getDuration();
        }
        return sum;
    }

    @Override
    public boolean isFinished() {
        return this.getCompletionPercentage() == MAX_PERCENTAGE;
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
        taskVisitor.visitComplexTask(this);
    }
}
