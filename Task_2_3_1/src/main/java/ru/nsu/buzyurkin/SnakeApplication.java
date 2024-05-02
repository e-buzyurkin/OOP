package ru.nsu.buzyurkin;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.buzyurkin.controller.GameController;
import ru.nsu.buzyurkin.entity.GameModel;
import ru.nsu.buzyurkin.ui.SnakeUI;

/**
 * Entry point of the Snake game application.
 */
public class SnakeApplication extends Application {
    private static final int FIELD_SIZE = 16;

    /**
     * Main method of the Snake game application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        SnakeUI ui = new SnakeUI(
                630,
                640,
                "Snake Game",
                primaryStage,
                FIELD_SIZE, FIELD_SIZE
        );
        GameModel model = new GameModel(FIELD_SIZE, FIELD_SIZE);
        GameController controller = new GameController(model, ui);

        controller.startGame();
    }
}
