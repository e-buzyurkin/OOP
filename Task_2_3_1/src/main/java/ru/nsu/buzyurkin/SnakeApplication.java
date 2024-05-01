package ru.nsu.buzyurkin;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.buzyurkin.controller.GameController;
import ru.nsu.buzyurkin.entity.GameModel;
import ru.nsu.buzyurkin.ui.SnakeUI;

public class SnakeApplication extends Application {
    private static final int FIELD_SIZE = 16;

    public static void main(String[] args) {
        launch(args);
    }

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
