import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Manages all the tasks.
 */

public class TaskList {
    final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d/M/yyyy HHmm");
    private List<Task> taskList;
    private Ui ui;

    public TaskList(Ui ui) {
        this.taskList = new ArrayList<>();
        this.ui = ui;
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }

    public int getSize() {
        return taskList.size();
    }

    /**
     * Add task into the list if information is given as a Task.
     * @param task A task object
     */
    public void addTask(Task task) {
        taskList.add(task);
    }

    /**
     * Add task into the list if information is given as a string of commands.
     * @param command Information to be used
     * @throws DukeException throws duke exception
     */
    public String addTask(String command) throws DukeException{
        ui.horizontalLine();
        List<String> commandList = new ArrayList<>(Arrays.asList(command.split(" ")));
        String stringHolder = (commandList.remove(0));
        List<String> listHolder = new ArrayList<>(commandList);
        if (stringHolder.startsWith("todo")) {
            stringHolder = (String.join(" ", commandList));
            if (!stringHolder.isEmpty()) {
                Task taskHolder = new ToDo(stringHolder, false);
                taskList.add(taskHolder);
                String holder = "Got it. I've added this task:\n" + "  [T][\u2718] " + stringHolder + "\n" + "Now you have " + taskList.size() + " tasks in the list.";
                return holder;
            } else {
                throw new DukeException("The description of a todo cannot be empty.");
            }
        } else if (stringHolder.startsWith("deadline")) {
            stringHolder = commandList.remove(0);
            listHolder.remove(0);
            for (String i : listHolder) {
                if (i.equals("/by")) {
                    commandList.remove(0);
                    break;
                } else {
                    stringHolder = stringHolder + " " + commandList.remove(0);
                }
            }

            String date = commandList.remove(0);
            listHolder.clear();
            listHolder.addAll(commandList);
            for (String i : listHolder) {
                date = date + " " + commandList.remove(0);
            }

            Date dateHolder;
            try {
                dateHolder = DATE_FORMAT.parse(date);
            } catch (ParseException e) {
                throw new DukeException("Please enter date in this format: d/m/y HHmm");
            }

            Task taskHolder = new Deadline(stringHolder, dateHolder, false);
            taskList.add(taskHolder);

            String holder = "Got it. I've added this task:\n" + "  [D][\u2718] " + stringHolder + " (by: " + date + ")" + "Now you have " + taskList.size() + " tasks in the list.";
            return holder;
        } else {
            stringHolder = commandList.remove(0);
            listHolder.remove(0);
            for (String i : listHolder) {
                if (i.equals("/at")) {
                    commandList.remove(0);
                    break;
                } else {
                    stringHolder = stringHolder + " " + commandList.remove(0);
                }
            }

            String date = commandList.remove(0);
            listHolder.clear();
            listHolder.addAll(commandList);
            for (String i : listHolder) {
                date = date + " " + commandList.remove(0);
            }

            Date dateHolder;
            try {
                dateHolder = DATE_FORMAT.parse(date);
            } catch (ParseException e) {
                throw new DukeException("Please enter date in this format: 2/12/2019 1800");
            }

            Task taskHolder = new Event(stringHolder, dateHolder, false);
            taskList.add(taskHolder);

            String holder = "Got it. I've added this task:\n" + "  [E][\u2718] " + stringHolder + " (at: " + date + ")" + "Now you have " + taskList.size() + " tasks in the list.";
            return holder;
        }
    }

    /**
     * Changes the status of the task from undone to done
     * @param index Index of the task
     */
    public String doneTask(int index) {
        index = index - 1;
        taskList.get(index).changeStatusTrue();
        String holder = ui.horizontalLine() + "\nNice! I've marked this task as done:\n" + "  [" + taskList.get(index).getType() + "]" + "[" + taskList.get(index).getStatusIcon() + "] " + taskList.get(index).getDescription() + taskList.get(index).getDate() + "\n" + ui.horizontalLine() + "\n";
        return holder;
    }

    /**
     * Deletes the task from the list
     * @param index Index of the task
     */
    public String deleteTask(int index) {
        index = index - 1;
        Task taskHolder = taskList.remove(index);
        String holder = ui.horizontalLine() + "\nNoted. I've removed this task:\n" + "  [" + taskHolder.getType() + "]" + "[" + taskHolder.getStatusIcon() + "] " + taskHolder.getDescription() + taskHolder.getDate() + "\n" + ui.horizontalLine() + "\n";
        return holder;
    }

    public List findTask(TaskList taskList, String findString) {
        List<Task> findList = new ArrayList<>();
        for (int i = 0; i < taskList.getSize(); i ++) {
            if (taskList.getTask(i).getDescription().contains(findString)) {
                findList.add(taskList.getTask(i));
            }
        }

        return findList;
    }

}
