package com.lask.model;

import com.lask.model.task.*;
import com.lask.model.task.std.Priority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class StdTaskBuilder implements TaskBuilder {

    private final AbstractTaskFactory factory;
    private final List<Task> result;
    private String description;
    private Date endDate;
    private Integer duration;
    private Integer priority;
    private Boolean finished;
    private Integer completionPercentage;
    private final Stack<State> states;
    private final Stack<List<Task>> childsStack;

    public StdTaskBuilder(AbstractTaskFactory factory) {
        result = new ArrayList<>();
        states = new Stack<>();
        childsStack = new Stack<>();
        this.factory = factory;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(result);
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
        this.description = desc;
    }

    @Override
    public void setEndDate(Date endDate) {
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
        this.priority = priority;
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
                if (description == null || endDate == null || priority == null
                        || duration == null || completionPercentage == null || finished != null) {
                    throw new IllegalArgumentException("Some fields are missing or wrong fields are filled for the basic task");
                }
                BasicTask task = this.factory.createBasicTask(description, endDate,
                        Priority.getPriorityFromInt(priority), duration, completionPercentage);
                manageTaskTreeStructure(task);
            }
            case BOOLEAN_TASK -> {
                if (description == null || endDate == null || priority == null || duration == null || finished == null
                        || completionPercentage != null) {
                    throw new IllegalArgumentException("Some fields are missing or wrong fields are filled for the boolean task");
                }
                BooleanTask task = this.factory.createBooleanTask(description, endDate,
                        Priority.getPriorityFromInt(priority), duration, finished);
                manageTaskTreeStructure(task);
            }
            case COMPLEX_TASK -> {
                if (description == null || endDate != null || priority == null || duration != null
                        || finished != null || completionPercentage != null) {
                    throw new IllegalArgumentException("Some fields are missing or wrong fields are filled for the complex task");
                }
                ComplexTask task = this.factory.createComplexTask(description,
                        Priority.getPriorityFromInt(priority));
                for (Task child : childsStack.pop()) {
                    task.addSubTask(child);
                }
                manageTaskTreeStructure(task);
            }
        }
    }

    /**
     * manageTaskTreeStructure: add the task at the good places in the task structure
     * @param currentTask Task
     */
    private void manageTaskTreeStructure(Task currentTask) {
        if (states.lastElement() == State.COMPLEX_TASK) {
            childsStack.lastElement().add(currentTask);
        } else if (states.lastElement() == State.NONE) {
            result.add(currentTask);
        } else {
            throw new IllegalArgumentException("Can't create a complex task inside the current task");
        }
    }

    private enum State {
        BASIC_TASK,
        BOOLEAN_TASK,
        COMPLEX_TASK,
        NONE,
    }

}
