package oscar.command;

import oscar.essential.Storage;
import oscar.essential.TaskList;

/**
 * Command to list tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Instantiates a list command.
     */
    public ListCommand() {
    }

    /**
     * Lists stored tasks in chronological order of addition.
     *
     * @param tasks   ArrayList of tasks.
     * @param storage File loading and saving handler.
     * @return
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        return tasks.list();
    }
}
