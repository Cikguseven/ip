package oscar.ui;

import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.Timer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import oscar.Oscar;
import oscar.command.ExitCommand;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Oscar oscar;

    private final Image userImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/images/user.png")));
    private final Image oscarImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/images/oscar.png")));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setOscar(Oscar o) {
        oscar = o;
        dialogContainer.getChildren().addAll(
                DialogBox.getOscarDialog(oscar.greet(), oscarImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Oscar's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws InterruptedException {
        String input = userInput.getText();
        String response = oscar.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getOscarDialog(response, oscarImage)
        );
        userInput.clear();
        if (response.equals(ExitCommand.EXIT_MESSAGE)) {
            int delay = 3000; // delay of Oscar closing in milliseconds
            ActionListener taskPerformer = event -> Platform.exit();
            Timer timer = new Timer(delay, taskPerformer);
            timer.setRepeats(false);
            timer.start();
        }
    }
}
