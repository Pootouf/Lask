package com.lask.model.xml;

import com.lask.model.TaskVisitor;
import com.lask.model.task.BasicTask;
import com.lask.model.task.BooleanTask;
import com.lask.model.task.ComplexTask;
import com.lask.model.task.Task;
import com.lask.model.task.std.Priority;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.Date;
import java.util.Stack;

/**
 * SaveXMLTaskVisitor enable to save the selected task in an XML file.
 */
public class SaveXMLTaskVisitor implements TaskVisitor {
    private final Document doc;
    private final Stack<Element> elements;

    public SaveXMLTaskVisitor(Document doc) {
        this.doc = doc;
        Element root = doc.createElement("tasks");
        doc.appendChild(root);
        elements = new Stack<>();
        elements.push(root);
    }

    @Override
    public void visitBasicTask(BasicTask basicTask) {
        try {
            Element element = doc.createElement("basicTask");
            writeDesc(basicTask.getDescription(), element);
            writeEndDate(basicTask.getEndDate(), element);
            writePriority(basicTask.getPriority(), element);
            writeDuration(basicTask.getDuration(), element);
            writePercentageCompletion(basicTask.getCompletionPercentage(), element);
            elements.peek().appendChild(element);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        try {
            Element element = doc.createElement("booleanTask");
            writeDesc(booleanTask.getDescription(), element);
            writeEndDate(booleanTask.getEndDate(), element);
            writePriority(booleanTask.getPriority(), element);
            writeDuration(booleanTask.getDuration(), element);
            writeIsFinished(booleanTask.isFinished(), element);
            elements.peek().appendChild(element);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        try {
            Element element = doc.createElement("complexTask");
            writeDesc(complexTask.getDescription(), element);
            writeEndDate(complexTask.getEndDate(), element);
            writePriority(complexTask.getPriority(), element);
            elements.push(element);
            for(Task task : complexTask.getSubTasks()) {
                task.accept(this);
            }
            elements.pop();
            elements.peek().appendChild(element);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writeEndDate : write in the stream the end date estimated for the task
     * @param date the estimated end date
     * @param element the parent element of the XML tree
     * @throws IOException when writing in the stream
     */
    private void writeEndDate(Date date, Element element) throws IOException {
        Element endDate = doc.createElement("endDate");
        endDate.setTextContent(date.toString());
        element.appendChild(endDate);
    }

    /**
     * writeDesc: write in the stream the description of the task
     * @param desc the description of the task
     * @param element the parent element of the XML tree
     * @throws IOException when writing in the stream
     */
    private void writeDesc(String desc, Element element) throws IOException {
        Element description = doc.createElement("desc");
        description.setTextContent(desc);
        element.appendChild(description);
    }

    /**
     * writePriority: write in the stream the priority of the task
     * @param priority the priority of the task
     * @param element the parent element of the XML tree
     * @throws IOException when writing in the stream
     */
    private void writePriority(Priority priority, Element element) throws IOException {
        Element prio = doc.createElement("priority");
        prio.setTextContent(priority.toString());
        element.appendChild(prio);
    }

    /**
     * writeDuration : write in the stream the duration of the task
     * @param duration the duration of the task
     * @param element the parent element of the XML tree
     * @throws IOException when writing in the stream
     */
    private void writeDuration(int duration, Element element) throws IOException {
        Element dur = doc.createElement("duration");
        dur.setTextContent(String.valueOf(duration));
        element.appendChild(dur);
    }

    /**
     * writePercentageCompletion : write in the stream the duration of the task
     * @param percentageCompletion the percentage completion of the task
     * @param element the parent element of the XML tree
     * @throws IOException when writing in the stream
     */
    private void writePercentageCompletion(int percentageCompletion, Element element) throws IOException {
        Element pC = doc.createElement("percentageCompletion");
        pC.setTextContent(String.valueOf(percentageCompletion));
        element.appendChild(pC);
    }

    /**
     * writeIsFinished : write in the stream if the task is finished
     * @param isFinished indicate if the task is finished
     * @param element the parent element of the XML tree
     * @throws IOException when writing in the stream
     */
    private void writeIsFinished(boolean isFinished, Element element) throws IOException {
        Element finished = doc.createElement("isFinished");
        finished.setTextContent(String.valueOf(isFinished));
        element.appendChild(finished);
    }
}
