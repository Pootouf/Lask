package com.lask.model;

import com.lask.model.task.*;
import com.lask.model.task.std.Priority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The implementation of the builder interface.
 * factory != null
 */
public class StdTaskBuilder implements TaskBuilder {

    private final AbstractTaskFactory factory;
    private final TaskList result;

    private final Stack<String> descriptionStack;
    private final Stack<Integer> priorityStack;
    private LocalDate endDate;
    private Integer duration;
    private Boolean finished;
    private Integer completionPercentage;

    private final Stack<State> states;
    private final Stack<List<Task>> childsStack;

    public StdTaskBuilder(AbstractTaskFactory factory) {
        result = factory.createTaskList();
        states = new Stack<>();
        descriptionStack = new Stack<>();
        priorityStack = new Stack<>();
        states.push(State.NONE);
        childsStack = new Stack<>();
        this.factory = factory;
    }

    /**
     * getTaskList : return the task list built
     * @return the TaskList
     */
    public TaskList getTaskList() {
        return result;
    }

    @Override
    public void startBasicTask() {
        if (states.lastElement() != State.NONE && states.lastElement() != State.COMPLEX_TASK) {
            throw new IllegalArgumentException("Can't create a basic task here");
        }
        states.push(State.BASIC_TASK);
    }

    @Override
    public void startBooleanTask() {
        if (states.lastElement() != State.NONE && states.lastElement() != State.COMPLEX_TASK) {
            throw new IllegalArgumentException("Can't create a boolean task here");
        }
        states.push(State.BOOLEAN_TASK);
    }

    @Override
    public void startComplexTask() {
        if (states.lastElement() != State.NONE && states.lastElement() != State.COMPLEX_TASK) {
            throw new IllegalArgumentException("Can't create a complex task here");
        }
        states.push(State.COMPLEX_TASK);
        childsStack.push(new ArrayList<>());
    }

    @Override
    public void setDescription(String desc) {
        if (states.lastElement() == State.NONE) {
            throw new IllegalArgumentException("Can't set the description of nothing");
        }
        this.descriptionStack.push(desc);
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        if (states.lastElement() == State.NONE) {
            throw new IllegalArgumentException("Can't set the end date of nothing");
        }
        this.endDate = endDate;
    }

    @Override
    public void setDuration(int duration) {
        if (states.lastElement() == State.NONE) {
            throw new IllegalArgumentException("Can't set the duration of nothing");
        }
        this.duration = duration;
    }

    @Override
    public void setPriority(int priority) {
        if (states.lastElement() == State.NONE) {
            throw new IllegalArgumentException("Can't set the priority of nothing");
        }
        this.priorityStack.push(priority);
    }

    @Override
    public void setFinished(boolean finished) {
        if (states.lastElement() != State.BOOLEAN_TASK) {
            throw new IllegalArgumentException("Only boolean task can have the finished attribute");
        }
        this.finished = finished;
    }

    @Override
    public void setCompletionPercentage(int percentage) {
        if (states.lastElement() != State.BASIC_TASK) {
            throw new IllegalArgumentException("Only basic task can have the completion percentage attribute");
        }
        this.completionPercentage = percentage;
    }

    @Override
    public void createTask() {
        switch (states.pop()) {
            case NONE -> throw new IllegalArgumentException("No task to create");
            case BASIC_TASK -> {
                if (descriptionStack.isEmpty() || priorityStack.isEmpty() || endDate == null
                        || duration == null || completionPercentage == null || finished != null) {
                    throw new IllegalArgumentException("Some fields are missing or wrong fields are filled for the basic task");
                }
                BasicTask task = this.factory.createBasicTask(descriptionStack.pop(), endDate,
                        Priority.getPriorityFromInt(priorityStack.pop()), duration, completionPercentage);
                manageTaskTreeStructure(task);
            }
            case BOOLEAN_TASK -> {
                if (descriptionStack.isEmpty() || priorityStack.isEmpty() || endDate == null
                        || duration == null || finished == null
                        || completionPercentage != null) {
                    throw new IllegalArgumentException("Some fields are missing or wrong fields are filled for the boolean task");
                }
                BooleanTask task = this.factory.createBooleanTask(descriptionStack.pop(), endDate,
                        Priority.getPriorityFromInt(priorityStack.pop()), duration, finished);
                manageTaskTreeStructure(task);
            }
            case COMPLEX_TASK -> {
                if (descriptionStack.isEmpty() || priorityStack.isEmpty() || endDate != null
                        || duration != null || finished != null || completionPercentage != null) {
                    throw new IllegalArgumentException("Some fields are missing or wrong fields are filled for the complex task");
                }
                ComplexTask task = this.factory.createComplexTask(descriptionStack.pop(),
                        Priority.getPriorityFromInt(priorityStack.pop()));
                for (Task child : childsStack.pop()) {
                    task.addSubTask(child);
                }
                manageTaskTreeStructure(task);
            }
        }
        endDate = null;
        duration = null;
        finished = null;
        completionPercentage = null;
    }

    /**
     * manageTaskTreeStructure: add the task at the good places in the task structure
     * @param currentTask Task
     */
    private void manageTaskTreeStructure(Task currentTask) {
        if (states.lastElement() == State.COMPLEX_TASK) {
            childsStack.lastElement().add(currentTask);
        } else if (states.lastElement() == State.NONE) {
            result.addTask(currentTask);
        } else {
            throw new IllegalArgumentException("Can't create a complex task inside the current task");
        }
    }

    /**
     * The enumeration of the possible states of the builder
     */
    private enum State {
        BASIC_TASK,
        BOOLEAN_TASK,
        COMPLEX_TASK,
        NONE,
    }

}
