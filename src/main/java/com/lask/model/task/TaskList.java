package com.lask.model.task;

import java.util.List;

/**
 * Represents a task list, a list of what need to be done.
 * It can be on a special topics, or juste be ordered.
 */
public interface TaskList {


    /**
     * getTaskList : return the task list of the object
     * @return List<Task>
     */
    List<Task> getTaskList();

    /**
     * getTask : get the ith element of the task list
     * @param i int, the index of the task
     * @return Task the gotten task
     */
    Task getTask(int i);

    /**
     * addTask : add the given task to the task list, if not contained in it.
     * @param task Task, the task to add
     */
    void addTask(Task task);

    /**
     * removeTask : remove the given task from the task list, if contained in it.
     * @param task Task, the task to remove
     */
    void removeTask(Task task);

}
