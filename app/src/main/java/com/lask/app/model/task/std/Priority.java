package com.lask.app.model.task.std;

/**
 * Represents the priority level of a task
 */
public enum Priority {
    URGENT(0),
    NORMAL(1),
    SECONDARY(2);

    private final int priority;

    Priority(int priority) {

        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
