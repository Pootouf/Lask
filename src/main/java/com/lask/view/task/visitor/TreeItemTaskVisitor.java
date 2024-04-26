package com.lask.view.task.visitor;

import com.lask.model.TaskVisitor;
import com.lask.model.task.*;
import javafx.scene.control.TreeItem;

import java.util.Stack;

public class TreeItemTaskVisitor implements TaskVisitor {

    private final Stack<TreeItem<Task>> stack = new Stack<>();

    public TreeItemTaskVisitor(TreeItem<Task> root) {
        stack.push(root);
    }

    public void visit(TaskList taskList) {
        for (Task task : taskList.getTaskList()) {
            task.accept(this);
        }
    }

    @Override
    public void visitBasicTask(BasicTask basicTask) {
        stack.peek().getChildren().add(new TreeItem<>(basicTask));
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        stack.peek().getChildren().add(new TreeItem<>(booleanTask));
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        TreeItem<Task> item = new TreeItem<>(complexTask);
        stack.peek().getChildren().add(item);
        stack.push(item);
        for (Task task : complexTask.getSubTasks()) {
            task.accept(this);
        }
        stack.pop();
    }
}
