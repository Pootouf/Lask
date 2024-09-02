package com.lask.model.task;

import com.lask.model.TaskVisitor;
import com.lask.model.task.std.Priority;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Represents a task, something that need to be done. It can either be complex, basic or boolean.
 */
public interface Task {

    int MAX_PERCENTAGE = 100;

    /**
     * getEndDate : return the estimated deadline for this task
     * @return Date
     */
    LocalDate getEndDate();

    /**
     * getPriority : return the priority level for this task
     * @return Priority
     */
    Priority getPriority();

    /**
     * getDuration : return the estimated duration for completing this task
     * @return int
     */
    int getDuration();

    /**
     * isFinished : indicates if the task is finished or not
     * @return boolean
     */
    boolean isFinished();

    /**
     * getCompletionPercentage : return the percentage of completion for this task
     * @return int
     */
    int getCompletionPercentage();


    /**
     * getDescription : return the description of the task
     * @return String
     */
    String getDescription();

    /**
     * isLeaf: indicate if this task is a leaf (if it can have sub-tasks)
     * @return bool
     */
    boolean isLeaf();

    /**
     * setDescription: set the description of the task
     * @param description the description of the task to set
     */
    void setDescription(String description);
    
    /**
     * setPriority: set the task's priority (enum value)
     * @param priority the priority of the task
     */
    void setPriority(Priority priority);

    /**
     * accept : call the appropriate method of the visitor
     * @param taskVisitor the visitor
     */
    void accept(TaskVisitor taskVisitor);
}
