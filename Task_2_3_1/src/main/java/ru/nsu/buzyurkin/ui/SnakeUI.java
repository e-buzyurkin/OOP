package ru.nsu.buzyurkin.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.nsu.buzyurkin.entity.GameField;
import ru.nsu.buzyurkin.entity.GameModel;
import ru.nsu.buzyurkin.state.CellState;

/**
 * Represents the user interface for the Snake game.
 */
public class SnakeUI {
    private static int CELL_SIZE = 40;

    private Scene mainScene;
    private Stage mainStage;

    private Label scoreLabel;
    private Label victoryLabel;
    private Label defeatLabel;
    private Label pauseLabel;
    private Label helpLabel;

    private final List<List<Rectangle>> cellRectangles = new ArrayList<>();

    /**
     * Constructs the SnakeUI object with the specified parameters.
     *
     * @param windowWidth  the width of the window
     * @param windowHeight the height of the window
     * @param windowTitle  the title of the window
     * @param primaryStage the primary stage of the JavaFX application
     * @param fieldCols    the number of columns in the game field
     * @param fieldRows    the number of rows in the game field
     */
    public SnakeUI(int windowWidth, int windowHeight, String windowTitle,
                   Stage primaryStage, int fieldCols, int fieldRows) {

        Pane root = new Pane();
        mainScene = new Scene(root, windowWidth, windowHeight);
        mainStage = primaryStage;

        GridPane gridPane = new GridPane();

        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        mainStage.setMinHeight(windowHeight + 40);
        mainStage.setMaxHeight(windowHeight + 40);

        mainStage.setMinWidth(windowWidth + 20);
        mainStage.setMaxWidth(windowWidth + 20);

        for (int i = 0; i < fieldRows; i++) {
            cellRectangles.add(new ArrayList<>());

            for (int j = 0; j < fieldCols; j++) {
                Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
                rectangle.setFill(Colors.BACKGROUND);

                gridPane.add(rectangle, j, i);
                cellRectangles.get(i).add(rectangle);
            }
        }

        root.getChildren().add(gridPane);

        scoreLabel = new Label("Score: ");
        root.getChildren().add(scoreLabel);

        victoryLabel = new Label("VICTORY");
        victoryLabel.setFont(Font.font("Calibri", 60));
        victoryLabel.setVisible(false);
        victoryLabel.setAlignment(Pos.CENTER);
        victoryLabel.setPadding(new Insets(160));
        root.getChildren().add(victoryLabel);

        defeatLabel = new Label("DEFEAT");
        defeatLabel.setFont(Font.font("Calibri", 60));
        defeatLabel.setVisible(false);
        defeatLabel.setAlignment(Pos.CENTER);
        defeatLabel.setPadding(new Insets(160));
        root.getChildren().add(defeatLabel);

        pauseLabel = new Label("PAUSE");
        pauseLabel.setFont(Font.font("Calibri", 60));
        pauseLabel.setVisible(false);
        pauseLabel.setAlignment(Pos.CENTER);
        pauseLabel.setPadding(new Insets(160));
        root.getChildren().add(pauseLabel);

        helpLabel = new Label("Press H for help");
        helpLabel.setPadding(new Insets(0, 0, 0, CELL_SIZE * fieldCols - 75));
        root.getChildren().add(helpLabel);

        primaryStage.setTitle(windowTitle);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Sets the event handler for key pressed events.
     *
     * @param handler the event handler for key pressed events
     */
    public void setKeyPressedHandler(EventHandler<? super KeyEvent> handler) {
        mainScene.setOnKeyPressed(handler);
    }

    /**
     * Sets the close handler for the main stage.
     *
     * @param handler the close handler for the main stage
     */
    public void setCloseHandler(EventHandler<WindowEvent> handler) {
        mainStage.setOnCloseRequest(handler);
    }

    /**
     * Shows the victory label.
     */
    public void showVictoryLabel() {
        victoryLabel.setVisible(true);
    }

    /**
     * Shows the defeat label with the specified score.
     *
     * @param score the score to display in the defeat label
     */
    public void showDefeatLabel(int score) {
        String result = "DEFEAT\nScore: " + score;

        Platform.runLater(() -> defeatLabel.setText(result));
        defeatLabel.setVisible(true);
    }

    /**
     * Hides all the labels.
     */
    public void hideLabels() {
        defeatLabel.setVisible(false);
        victoryLabel.setVisible(false);
    }

    /**
     * Toggles the visibility of the pause label.
     */
    public void togglePauseLabel() {
        pauseLabel.setVisible(!pauseLabel.isVisible());
    }

    /**
     * Shows a help message in an alert dialog.
     */
    public void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setContentText("WASD - move\nP - pause\nR - restart\nH - help");
        alert.showAndWait().ifPresent(rs -> {});
    }

    /**
     * Shows an error message in an alert dialog.
     */
    public void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText("An error has occured\nPlease restart the application");
        alert.showAndWait().ifPresent(rs -> {});
    }

    /**
     * Draws the current state of the game field.
     *
     * @param gameModel the game model containing the current state of the game
     */
    public void draw(GameModel gameModel) {
        GameField field = gameModel.getField();

        for (int i = 0; i < field.getRowsCount(); i++) {
            for (int j = 0; j < field.getColsCount(); j++) {
                CellState state = field.getCell(j, i);
                Rectangle cell = getCellRectangle(j, i);

                switch (state) {
                    case EMPTY -> {
                        cell.setFill(Colors.BACKGROUND);
                    }
                    case APPLE -> {
                        cell.setFill(Colors.FOOD);
                    }
                    case SNAKE_HEAD -> {
                        cell.setFill(Colors.SNAKE_HEAD);
                    }
                    case SNAKE_TAIL -> {
                        cell.setFill(Colors.SNAKE_TAIL);
                    }
                    case WALL -> {
                        cell.setFill(Colors.WALL);
                    }
                    default -> {
                    }
                }
            }

            String scoreString = "Score: " + gameModel.getScore();
            Platform.runLater(() -> scoreLabel.setText(scoreString));
        }
    }

    private Rectangle getCellRectangle(int x, int y) {
        return cellRectangles.get(y).get(x);
    }
}