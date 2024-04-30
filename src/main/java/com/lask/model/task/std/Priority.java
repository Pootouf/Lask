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

    /**
     * getPriority : the value of the priority
     * @return the priority value
     */
    public int getPriority() {
        return priority;
    }

    /**
     * toString : return the string value of the priority
     * @return the string
     */
    @Override
    public String toString() {
        return String.valueOf(priority);
    }

    /**
     * getPriorityFromInt: return the associated priority with the value
     * @param value the priority value
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
