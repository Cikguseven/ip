package oscar.essential;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import oscar.exception.OscarException;
import oscar.task.Task;

/**
 * Contains ArrayList of tasks that Oscar can interact with.
 */
public class TaskList {
    private ArrayList<Task> taskList;

    /**
     * Uses an empty task list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Uses the saved task list.
     *
     * @param stream Object input stream to be read.
     * @throws OscarException Unable to load object input stream.
     */
    public TaskList(ObjectInputStream stream) throws OscarException {
        assert stream != null;
        this.taskList = load(stream);
    }

    /**
     * Loads the task list from the object input stream.
     * Solution adapted by <a href="https://howtodoinjava.com/java/collections/arraylist/
     * serialize-deserialize-arraylist/">...</a>
     *
     * @param stream Deserialized save file stream.
     * @return Saved task list in an ArrayList.
     * @throws OscarException Unable to handle object input stream.
     */
    private ArrayList<Task> load(ObjectInputStream stream) throws OscarException {
        try {
            @SuppressWarnings("unchecked")
            ArrayList<Task> tempList = (ArrayList<Task>) stream.readObject();
            taskList = tempList;
            return taskList;
        } catch (IOException e) {
            throw new OscarException("Sorry! There is an error loading the saved task list.\n");
        } catch (ClassNotFoundException e) {
            throw new OscarException("Sorry! Class cannot be found.\n");
        }
    }

    /**
     * Saves the current task list by serializing it.
     *
     * @param stream Object output stream of saved file.
     * @throws OscarException Input or output error.
     */
    public void save(ObjectOutputStream stream) throws OscarException {
        try {
            stream.writeObject(taskList);
        } catch (IOException e) {
            throw new OscarException("Sorry! There is an issue with your input or output.\n");
        }
    }

    /**
     * Obtains the number of tasks in the task list.
     *
     * @return Count of tasks.
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Lists stored tasks in chronological order of addition.
     *
     * @return Tasks in task list.
     */
    public String list() {
        StringBuilder result = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 1; i <= taskList.size(); i++) {
            Task currentTask = taskList.get(i - 1);
            result.append(i).append(".").append(currentTask).append("\n");
        }
        return result.append("\n").toString();
    }

    /**
     * Displays the number of tasks stored in the task list.
     *
     * @return Message that informs users of number of tasks in task list.
     */
    public String listCount() {
        int listSize = getSize();
        if (listSize == 0) {
            return "You have no tasks in the list. Add some now!\n";
        } else if (listSize == 1) {
            return "You have 1 task in the list.\n";
        } else {
            return "You now have " + listSize + " tasks in the list.\n";
        }
    }

    /**
     * Marks a task as done.
     *
     * @param index Task number.
     * @return Description of task.
     */
    public String mark(int index) {
        Task currentTask = taskList.get(index);
        currentTask.markAsDone();
        return currentTask.toString();
    }

    /**
     * Marks a task as not done.
     *
     * @param index Task number.
     * @return Description of task.
     */
    public String unmark(int index) {
        Task currentTask = taskList.get(index);
        currentTask.markAsNotDone();
        return currentTask.toString();
    }

    /**
     * Deletes a task.
     *
     * @param index Task number.
     * @return Description of task.
     */
    public String delete(int index) {
        Task currentTask = taskList.remove(index);
        return currentTask.toString();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task Task to be added.
     */
    public void add(Task task) {
        taskList.add(task);
    }

    /**
     * Lists all tasks in the task list containing the keyword.
     *
     * @param keyword String to match.
     * @return List of tasks containing keyword.
     */
    public String find(String keyword) {
        StringBuilder result = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 1; i <= taskList.size(); i++) {
            Task currentTask = taskList.get(i - 1);
            if (currentTask.getDescription().contains(keyword)) {
                result.append(i).append(".").append(currentTask);
            }
        }
        return result.append("\n").toString();
    }
}
