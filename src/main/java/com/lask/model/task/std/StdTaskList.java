package com.lask.model.task.std;

import com.lask.model.task.Task;
import com.lask.model.task.TaskList;

import java.util.ArrayList;
import java.util.List;

public class StdTaskList implements TaskList {

    private final List<Task> tasks;

    public StdTaskList() {
        tasks = new ArrayList<>();
    }

    @Override
    public List<Task> getTaskList() {
        return tasks;
    }

    @Override
    public Task getTask(int i) {
        return tasks.get(i);
    }

    @Override
    public void addTask(Task task) {
        if(tasks.contains(task)) {
            throw new IllegalArgumentException("Task already added in this list");
        }
        tasks.add(task);
    }

    @Override
    public void removeTask(Task task) {
        if(!tasks.contains(task)) {
            throw new IllegalArgumentException("Task must already be in this list");
        }
        tasks.remove(task);
    }
}
