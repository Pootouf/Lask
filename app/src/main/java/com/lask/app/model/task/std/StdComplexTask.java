package com.lask.app.model.task.std;

import com.lask.app.model.task.ComplexTask;
import com.lask.app.model.task.Task;

import java.util.Date;

/**
 * The implementation of a complex task.
 *
 * @inv
 * desc != null
 * priority != null
 */
public class StdComplexTask extends ComplexTask {

    private static final int MAX_PERCENTAGE = 100;

    private final String desc;

    private final Priority priority;


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
        for (Task task : this.getSubTasks()) {
            completedTasks += task.isFinished() ? 1 : 0;
        }
        return (completedTasks / this.getSubTasksNumber()) * MAX_PERCENTAGE;
    }

    @Override
    public Date getEndDate() {
        Date max = new Date();
        for(Task task : this.getSubTasks()) {
            if(max.before(task.getEndDate())) {
                max = task.getEndDate();
            }
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
}
