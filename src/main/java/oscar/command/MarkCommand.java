package oscar.command;

import oscar.essential.Storage;
import oscar.essential.TaskList;
import oscar.exception.OscarException;

/**
 * Command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final String details;

    /**
     * Instantiates a mark command.
     *
     * @param details Task number to be marked.
     */
    public MarkCommand(String details) {
        this.details = details;
    }

    /**
     * Marks a task as done using the task number.
     *
     * @param tasks   ArrayList of tasks.
     * @param storage File loading and saving handler.
     * @return String output of mark command.
     * @throws OscarException Failure to validate task number.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws OscarException {
        int index = validateInt(details, tasks);
        String currentTask = tasks.mark(index);
        storage.save(tasks);
        return "Nice! Oscar has marked this task as done:\n" + currentTask + "\n";
    }
}
