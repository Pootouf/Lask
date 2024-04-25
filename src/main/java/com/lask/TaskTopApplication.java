package com.lask;

import com.lask.model.task.Task;
import com.lask.model.top.TopTask;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * Hello world!
 *
 */
public class TaskTopApplication
{
    public static void main( String[] args ) {
        if (args.length < 1) {
            System.out.println("No file provided");
            return;
        }
        File file = new File(args[0]);

        List<Task> tasks =  TopTask.getTop5TaskToDo(file);
        for(Task task : tasks) {
            displayTaskOnShell(task);
        }
    }


    /**
     * displayTaskOnShell : display on the shell a task and its attributes
     * @param task, the task to display
     */
    private static void displayTaskOnShell(Task task) {
        System.out.println(
                task.getDescription() + '\n'
                + (task.getEndDate().isBefore(LocalDate.now()) ? "est dépassée depuis le " : "se termine le " )
                + task.getEndDate() + '\n'
                + "D'une durée de " + task.getDuration() + '\n'
                + "Priorité : " + task.getPriority().name() + '\n'
                + "Finie à " + task.getCompletionPercentage() + "%"
                + '\n' + '\n'
        );
    }
}
