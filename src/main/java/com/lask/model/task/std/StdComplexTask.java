package com.lask.model.task.std;

import com.lask.model.TaskVisitor;
import com.lask.model.task.ComplexTask;
import com.lask.model.task.Task;

import java.time.LocalDate;

/**
 * The implementation of a complex task.
 *
 * @inv
 * desc != null
 * priority != null
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
        int sumPercentageMultipliedByDuration = 0;
        int sumDuration = 0;
        if (this.getSubTasksNumber() == 0) {
            return 100;
        }
        for (Task task : this.getSubTasks()) {
            sumPercentageMultipliedByDuration += task.getCompletionPercentage() * task.getDuration();
            sumDuration += task.getDuration();
        }
        return sumPercentageMultipliedByDuration / sumDuration;
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
