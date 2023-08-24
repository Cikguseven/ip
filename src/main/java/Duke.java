import java.util.ArrayList;
import java.util.Scanner;

/**
 * Duke chatbot named Oscar that can respond to user input.
 */
public class Duke {
    static ArrayList<Task> taskList = new ArrayList<>();

    enum Commands {
        BYE,
        LIST,
        MARK,
        UNMARK,
        DELETE,
        TODO,
        DEADLINE,
        EVENT
    }

    /**
     * Display message to greet user.
     */
    private static void greet() {
        System.out.println("Hello! This is Oscar, your friendly chatbot :)");
        System.out.println("What can Oscar do for you?\n");
    }

    /**
     * Display message when terminating Duke.
     */
    private static void bye() {
        System.out.println("Goodbye for now. " +
                "Oscar hopes to see you again soon!\n");
    }

    /**
     * List stored tasks in chronological order of addition.
     */
    private static void list() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i <= taskList.size(); i++) {
            Task currentTask = taskList.get(i - 1);
            System.out.println(i + "." + currentTask.toString());
        }
        System.out.println();
    }

    /**
     * Displays the number of tasks stored in Oscar.
     */
    private static void listCount() {
        int listSize = taskList.size();
        if (listSize == 0) {
            System.out.println("You have no tasks in the list. Add some now!\n");
        } else if (listSize == 1) {
            System.out.println("You have 1 task in the list.\n");
        } else {
            System.out.println("You now have " + listSize + " tasks in the list.\n");
        }
    }

    /**
     * Mark a task as done using the task number.
     * @param index Number of task to be marked as done.
     * @throws DukeException Failure of task number validation.
     */
    private static void mark(String index) throws DukeException {
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(index) - 1;
        } catch(NumberFormatException e){
            throw new DukeException("Sorry! " +
                    "Please enter the number of the task to be marked as done.\n");
        }
        if (taskIndex < 0) {
            throw new DukeException("Sorry! " +
                    "Task numbers must be natural numbers.\n");

        } else if (taskIndex >= taskList.size()) {
            throw new DukeException("Sorry! " +
                    "Task number is too large.\n");
        }
        Task currentTask = taskList.get(taskIndex);
        currentTask.markAsDone();
        System.out.println("Nice! Oscar has marked this task as done:\n");
        System.out.println(currentTask + "\n");
    }

    /**
     * Mark a task as not done using the task number.
     * @param index Number of task to be marked as not done.
     * @throws DukeException Failure of task number validation.
     */
    private static void unmark(String index) throws DukeException {
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(index) - 1;
        } catch(NumberFormatException e){
            throw new DukeException("Sorry! Please enter the order " +
                    "of the task to be marked as not done.\n");
        }
        if (taskIndex < 0) {
            throw new DukeException("Sorry! " +
                    "Task numbers must be natural numbers.\n");

        } else if (taskIndex >= taskList.size()) {
            throw new DukeException("Sorry! " +
                    "Task number is too large.\n");
        }
        Task currentTask = taskList.get(taskIndex);
        currentTask.markAsNotDone();
        System.out.println("Oscar has marked this task as not done yet:\n");
        System.out.println(currentTask + "\n");
    }

    /**
     * Delete a task using the task number.
     * @param index Number of task to be deleted.
     * @throws DukeException Failure of task number validation.
     */
    private static void delete(String index) throws DukeException {
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(index) - 1;
        } catch(NumberFormatException e){
            throw new DukeException("Sorry! Please enter the order " +
                    "of the task to be deleted.\n");
        }
        if (taskIndex < 0) {
            throw new DukeException("Sorry! " +
                    "Task numbers must be natural numbers.\n");

        } else if (taskIndex >= taskList.size()) {
            throw new DukeException("Sorry! " +
                    "Task number is too large.\n");
        }
        Task removedTask = taskList.remove(taskIndex);
        System.out.println("Oscar has removed this task:\n");
        System.out.println(removedTask + "\n");
        listCount();
    }

    /**
     * Create a new todo task and save it to the collection.
     * @param description Details of todo task.
     * @throws DukeException Todo missing description.
     */
    public static void todo(String description) throws DukeException {
        if (description.length() == 0) {
            throw new DukeException("Sorry! " +
                    "The description of a todo task cannot be empty.\n");
        }
        Task newTodo = new Todo(description);
        taskList.add(newTodo);
        System.out.println("Oscar has added:\n" + newTodo + "\n");
        listCount();
    }

    /**
     * Create a new deadline task and save it to the collection.
     * @param details Information about the details and deadline of task.
     * @throws DukeException Deadline missing details.
     */
    public static void deadline(String details) throws DukeException {
        if (!details.contains(" /by ")) {
            throw new DukeException("Sorry! " +
                    "The deadline task is not formatted correctly.\n");
        }
        String[] split = details.split(" /by ", 2);
        String description = split[0];
        if (description.length() == 0) {
            throw new DukeException("Sorry! " +
                    "The description of a deadline task cannot be empty.\n");
        }
        String deadline = split[1];
        if (deadline.length() == 0) {
            throw new DukeException("Sorry! " +
                    "The deadline of a deadline task cannot be empty.\n");
        }
        Task newDeadline = new Deadline(description, deadline);
        taskList.add(newDeadline);
        System.out.println("Oscar has added:\n" + newDeadline + "\n");
        listCount();
    }

    /**
     * Create a new event task and save it to the collection.
     * @param details Information about the details, as well as start and end
     *                date/time of task.
     * @throws DukeException Event missing details.
     */
    public static void event(String details) throws DukeException {
        if (!details.contains(" /from ") || !details.contains(" /to ")) {
            throw new DukeException("Sorry! " +
                    "The event task is not formatted correctly.\n");
        }
        String[] split = details.split(" /from | /to ");
        String description = split[0];
        if (description.length() == 0) {
            throw new DukeException("Sorry! " +
                    "The description of an event task cannot be empty.\n");
        }
        String start = split[1];
        if (start.length() == 0) {
            throw new DukeException("Sorry! " +
                    "The start date/time of an event task cannot be empty.\n");
        }
        String end = split[2];
        if (end.length() == 0) {
            throw new DukeException("Sorry! " +
                    "The end date/time of an event task cannot be empty.\n");
        }
        Task newEvent = new Event(description, start, end);
        taskList.add(newEvent);
        System.out.println("Oscar has added:\n" + newEvent + "\n");
        listCount();
    }

    /**
     * Programme flow to run Oscar
     * @throws DukeException Handling unknown commands.
     */
    public static void main(String[] args) throws DukeException {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        greet();

        while (running) {
            // Obtain command and details entered by user to determine the next
            // course of action
            try {
                String userInput = scanner.nextLine();
                String[] split = userInput.split(" ", 2);
                String command = split[0];
                Commands c;
                try {
                    c = Commands.valueOf(split[0].toUpperCase());
                    String details = userInput.length() > command.length() ? split[1] : "";
                    switch (c) {
                        // Exit programme if user enters "bye" command
                        case BYE:
                            bye();
                            running = false;
                            break;

                        // Display text stored by user in chronological order if
                        // user enters "list" command
                        case LIST:
                            list();
                            break;

                        // Mark task as done if user enters "mark" command
                        case MARK:
                            mark(details);
                            break;

                        // Mark task as not done if user enters "unmark" command
                        case UNMARK:
                            unmark(details);
                            break;

                        // Delete a task if user enters "delete" command
                        case DELETE:
                            delete(details);
                            break;

                        // Create a new todo task if user enters "todo" command
                        case TODO:
                            todo(details);
                            break;

                        // Create a new deadline task if user enters "deadline" command
                        case DEADLINE:
                            deadline(details);
                            break;

                        // Create a new event task if user enters "event" command
                        case EVENT:
                            event(details);
                            break;
                    }
                } catch (IllegalArgumentException e) {
                        // Default response for unknown commands
                        throw new DukeException("Sorry! Oscar does not recognise this command\n");
                }
            } catch (DukeException e){
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
