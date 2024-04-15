package com.lask.model.task.std;

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

    @Override
    public String toString() {
        return String.valueOf(priority);
    }

    /**
     * getPriorityFromInt: return the associated priority with the value
     * @param value
     * @return the priority
     */
    public static Priority getPriorityFromInt(int value) {
        return switch (value) {
            case 0 -> URGENT;
            case 1 -> NORMAL;
            case 2 -> SECONDARY;
            default -> throw new IllegalArgumentException("No priority for this value");
        };
    }
}
