package com.lask.model.top;

import com.lask.model.AbstractTaskFactory;
import com.lask.model.StdTaskBuilder;
import com.lask.model.StdTaskFactory;
import com.lask.model.task.Task;
import com.lask.model.task.TaskList;
import com.lask.model.xml.XMLTaskLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the functions regarding the top tasks CLI
 */
public class TopTask {

    /**
     * getTop5TaskToDo : return the list of the 5 first task to do, depending on the end date of them
     * @param file the given file containing the task
     * @return the 5 top task to do
     */
    public static List<Task> getTop5TaskToDo(File file) {
        AbstractTaskFactory factory = new StdTaskFactory();
        StdTaskBuilder builder = new StdTaskBuilder(factory);
        XMLTaskLoader loader = new XMLTaskLoader(builder);

        loader.loadFile(file);

        TaskList tasks = builder.getTaskList();

        List<Task> unfinishedTasks = new ArrayList<>(
                tasks.getTaskList().stream().filter(task -> !task.isFinished()).toList()
        );

        unfinishedTasks.sort(
              (Task task1, Task task2) -> {
                  if (task1.getEndDate() == task2.getEndDate()) return 0;
                  return task1.getEndDate().isBefore(task2.getEndDate()) ? -1 : 1;
              }
        );
        if (unfinishedTasks.size() <= 5) {
            return unfinishedTasks;
        }
        return unfinishedTasks.subList(0, 5);
    }
}
