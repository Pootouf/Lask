package com.lask.model.xml;

import com.lask.model.TaskVisitor;
import com.lask.model.task.*;
import com.lask.model.task.std.Priority;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

/**
 * BasicSaveXMLTaskVisitor enable to save the selected task in an XML output with no verification
 */
public class BasicSaveXMLTaskVisitor implements TaskVisitor {

    private final OutputStream outputStream;

    public BasicSaveXMLTaskVisitor(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * visit: transform the taskList into a valid xml file
     * @param taskList the list to transform
     * @throws IOException if there is a problem to write in the outputStream
     */
    public void visit(TaskList taskList) throws IOException {
        outputStream.write("<tasks>".getBytes());
        for (Task task : taskList.getTaskList()) {
            task.accept(this);
        }
        outputStream.write("</tasks>".getBytes());
    }

    @Override
    public void visitBasicTask(BasicTask basicTask) {
        try {
            outputStream.write("<basicTask>".getBytes());
            writeDesc(basicTask.getDescription());
            writeEndDate(basicTask.getEndDate());
            writePriority(basicTask.getPriority());
            writeDuration(basicTask.getDuration());
            writePercentageCompletion(basicTask.getCompletionPercentage());
            outputStream.write("</basicTask>".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        try {
            outputStream.write("<booleanTask>".getBytes());
            writeDesc(booleanTask.getDescription());
            writeEndDate(booleanTask.getEndDate());
            writePriority(booleanTask.getPriority());
            writeDuration(booleanTask.getDuration());
            writeIsFinished(booleanTask.isFinished());
            outputStream.write("</booleanTask>".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        try {
            outputStream.write("<complexTask>".getBytes());
            writeDesc(complexTask.getDescription());
            writeEndDate(complexTask.getEndDate());
            writePriority(complexTask.getPriority());
            for(Task task : complexTask.getSubTasks()) {
                task.accept(this);
            }
            outputStream.write("</complexTask>".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writeEndDate : write in the stream the end date estimated for the task
     * @param date the estimated end date
     * @throws IOException when writing in the stream
     */
    private void writeEndDate(LocalDate date) throws IOException {
        outputStream.write("<endDate>".getBytes());
        outputStream.write(date.toString().getBytes());
        outputStream.write("</endDate>".getBytes());
    }

    /**
     * writeDesc: write in the stream the description of the task
     * @param desc the description of the task
     * @throws IOException when writing in the stream
     */
    private void writeDesc(String desc) throws IOException {
        outputStream.write("<desc>".getBytes());
        outputStream.write(desc.getBytes());
        outputStream.write("</desc>".getBytes());
    }

    /**
     * writePriority: write in the stream the priority of the task
     * @param priority the priority of the task
     * @throws IOException when writing in the stream
     */
    private void writePriority(Priority priority) throws IOException {
        outputStream.write("<priority>".getBytes());
        outputStream.write(priority.toString().getBytes());
        outputStream.write("</priority>".getBytes());
    }

    /**
     * writeDuration : write in the stream the duration of the task
     * @param duration the duration of the task
     * @throws IOException when writing in the stream
     */
    private void writeDuration(int duration) throws IOException {
        outputStream.write("<duration>".getBytes());
        outputStream.write(String.valueOf(duration).getBytes());
        outputStream.write("</duration>".getBytes());
    }

    /**
     * writePercentageCompletion : write in the stream the duration of the task
     * @param percentageCompletion the percentage completion of the task
     * @throws IOException when writing in the stream
     */
    private void writePercentageCompletion(int percentageCompletion) throws IOException {
        outputStream.write("<percentageCompletion>".getBytes());
        outputStream.write(String.valueOf(percentageCompletion).getBytes());
        outputStream.write("</percentageCompletion>".getBytes());
    }

    /**
     * writeIsFinished : write in the stream if the task is finished
     * @param isFinished indicate if the task is finished
     * @throws IOException when writing in the stream
     */
    private void writeIsFinished(boolean isFinished) throws IOException {
        outputStream.write("<isFinished>".getBytes());
        outputStream.write(String.valueOf(isFinished).getBytes());
        outputStream.write("</isFinished>".getBytes());
    }
}
