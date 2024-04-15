package com.lask.model;

import com.lask.model.task.BasicTask;
import com.lask.model.task.BooleanTask;
import com.lask.model.task.ComplexTask;

public interface TaskVisitor {

    /**
     * visitBasicTask : allow implementors to visit a basic task
     * @param basicTask the visited task
     */
    void visitBasicTask(BasicTask basicTask);

    /**
     * visitBooleanTask : allow implementors to visit a boolean task
     * @param booleanTask the visited task
     */
    void visitBooleanTask(BooleanTask booleanTask);

    /**
     * visitComplexTask : allow implementors to visit a complex task
     * @param complexTask the visited task
     */
    void visitComplexTask(ComplexTask complexTask);
}
