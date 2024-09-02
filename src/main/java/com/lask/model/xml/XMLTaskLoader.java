package com.lask.model.xml;

import com.lask.model.TaskBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Load an XML Task File and use the builder to create the task list associated with
 */
public class XMLTaskLoader {
    private boolean descFlag = false;
    private boolean priorityFlag = false;
    private boolean endDateFlag = false;
    private boolean durationFlag = false;
    private boolean percentageCompletionFlag = false;
    private boolean isFinishedFlag = false;
    private final TaskBuilder taskBuilder;

    public XMLTaskLoader(TaskBuilder taskBuilder) {
        this.taskBuilder = taskBuilder;
    }

    /**
     * loadFile : parse the given file and return the associated builder containing the task list
     * @param inputFile the file to parse
     * @return the builder
     */
    public TaskBuilder loadFile(File inputFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            TaskHandler handler = new TaskHandler();
            saxParser.parse(inputFile, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskBuilder;
    }

    /**
     * Manage the parsing of an XML file containing a task list.
     */
    private class TaskHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("basicTask")) {
                taskBuilder.startBasicTask();
            } else if (qName.equalsIgnoreCase("complexTask")) {
                taskBuilder.startComplexTask();
            } else if (qName.equalsIgnoreCase("booleanTask")) {
                taskBuilder.startBooleanTask();
            } else if (qName.equalsIgnoreCase("desc")) {
                descFlag = true;
            } else if (qName.equalsIgnoreCase("priority")) {
                priorityFlag = true;
            } else if (qName.equalsIgnoreCase("endDate")) {
                endDateFlag = true;
            } else if (qName.equalsIgnoreCase("duration")) {
                durationFlag = true;
            } else if (qName.equalsIgnoreCase("percentageCompletion")) {
                percentageCompletionFlag = true;
            } else if (qName.equalsIgnoreCase("isFinished")) {
                isFinishedFlag = true;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (descFlag) {
                descFlag = false;
                taskBuilder.setDescription(new String(ch, start, length));
            } else if (priorityFlag) {
                priorityFlag = false;
                taskBuilder.setPriority(Integer.parseInt(new String(ch, start, length)));
            } else if (endDateFlag) {
                endDateFlag = false;
                LocalDate dateStr = LocalDate.parse(new String(ch, start, length));
                taskBuilder.setEndDate(dateStr);
            } else if (durationFlag) {
                durationFlag = false;
                taskBuilder.setDuration(Integer.parseInt(new String(ch, start, length)));
            } else if (percentageCompletionFlag) {
                percentageCompletionFlag = false;
                taskBuilder.setCompletionPercentage(Integer.parseInt(new String(ch, start, length)));
            } else if (isFinishedFlag) {
                isFinishedFlag = false;
                taskBuilder.setFinished(Boolean.parseBoolean(new String(ch, start, length)));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if (qName.equalsIgnoreCase("basicTask") || qName.equalsIgnoreCase("complexTask")
                    || qName.equalsIgnoreCase("booleanTask")) {
                taskBuilder.createTask();
            }
        }
    }
}
