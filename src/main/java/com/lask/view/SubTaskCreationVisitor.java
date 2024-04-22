package com.lask.view;

import com.lask.model.TaskVisitor;
import com.lask.model.task.BasicTask;
import com.lask.model.task.BooleanTask;
import com.lask.model.task.ComplexTask;
import com.lask.model.task.Task;

public class SubTaskCreationVisitor implements TaskVisitor {

    private final Task children;

    public SubTaskCreationVisitor(Task children) {
        this.children = children;
    }

    public void visit(Task task) {
        task.accept(this);
    }

    @Override
    public void visitBasicTask(BasicTask basicTask) {

    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {

    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        complexTask.addSubTask(children);
    }
}
