package oscar.essential;

import oscar.exception.OscarException;

import java.util.Scanner;

/**
 * Class to deal with user interaction.
 */
public class Ui {
    /**
     * Displays message to greet user.
     */
    public void greet() {
        System.out.println("Hello! This is Oscar, your friendly chatbot :)");
        System.out.println("What can Oscar do for you?\n");
    }

    /**
     * Print error message in OscarException.
     * @param e OscarException.
     */
    public void showError(OscarException e) {
        System.out.println(e.getMessage());
    }

    /**
     * Instantiates a scanner to take in user input.
     * @return User input.
     */
    public String readCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Print divider line in output.
     */
    public void showLine() {
        System.out.println("___________________________________\n");
    }


}