package com.lask.model.task;

import java.util.List;

/**
 * Represents a complex task with a progression state and sub-tasks.
 */
public abstract class ComplexTask implements Task {

    private List<Task> subTasks;

    /**
     * getSubTasks : return the sub-tasks associated to the complex task
     * @return List<Task>
     */
    public List<Task> getSubTasks() {
        return subTasks;
    }

    /**
     * getSubTask : return a specific task from the sub-tasks list
     * @param i int, the position in the list
     * @return Task
     */
    public Task getSubTask(int i) {
        assert subTasks.get(i) != null;
        return subTasks.get(i);
    }

    /**
     * getSubTasksNumber : return the number of sub-tasks for this task
     * @return int
     */
    public int getSubTasksNumber() {
        return subTasks.size();
    }

    /**
     * addSubTask : add a new sub-task in this complex task
     * @param task Task, the task to add
     */
    public void addSubTask(Task task) {
        subTasks.add(task);
    }

    /**
     * removeSubTask : remove the specified task from the sub-tasks list
     * @param task Task, the task to remove
     */
    void removeSubTask(Task task) {
        assert subTasks.contains(task);
        subTasks.remove(task);
    }
}
