package ru.nsu.buzyurkin.controller;

import javafx.application.Platform;
import ru.nsu.buzyurkin.entity.GameModel;
import ru.nsu.buzyurkin.state.Direction;
import ru.nsu.buzyurkin.ui.SnakeUI;

/**
 * Controls the game flow and interaction between the game model and user interface.
 */
public class GameController {
    private static int DELAY = 200;

    private GameThread gameThread;
    private GameModel gameModel;
    private SnakeUI snakeUI;

    private Direction dir = Direction.UP;
    private boolean paused = false;

    /**
     * Constructs a GameController object with the specified game model and user interface.
     *
     * @param gameModel the game model
     * @param snakeUI   the user interface
     */
    public GameController(GameModel gameModel, SnakeUI snakeUI) {
        this.gameModel = gameModel;
        this.snakeUI = snakeUI;

        KeyboardHandler keyboardHandler = new KeyboardHandler(this);

        gameThread = new GameThread(this, DELAY);

        snakeUI.setKeyPressedHandler(keyboardHandler);
        snakeUI.setCloseHandler(event -> gameThread.interrupt());
    }

    /**
     * Sets the direction of the snake movement.
     *
     * @param dir the direction
     */
    public void setDir(Direction dir) {
        this.dir = dir;
    }

    /**
     * Pauses or resumes the game.
     */
    public void pauseGame() {
        if (!gameThread.isPaused()) {
            paused = !paused;
            snakeUI.togglePauseLabel();
        }
    }

    /**
     * Shows the help message.
     */
    public void showHelp() {
        snakeUI.showHelp();
    }

    /**
     * Handles the victory.
     */
    public void victory() {
        snakeUI.showVictoryLabel();
        gameThread.stopGame();
    }

    /**
     * Handles the defeat.
     */
    public void defeat() {
        snakeUI.showDefeatLabel(gameModel.getScore());
        gameThread.stopGame();
    }

    /**
     * Performs a single step of the game.
     */
    public void step() {
        try {
            if (!paused) {
                switch (gameModel.step(dir)) {
                    case WIN -> {
                        victory();
                    }
                    case LOSE -> {
                        defeat();
                    }
                    case PLAYING -> {
                        snakeUI.draw(gameModel);
                    }
                    default -> {
                    }
                }
            }
        } catch (Exception e) {
            Platform.runLater(snakeUI::showError);
            gameThread.stopGame();
        }
    }

    /**
     * Starts the game thread.
     */
    public void startGame() {
        gameThread.start();
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        snakeUI.hideLabels();
        gameModel.resetGame();
        gameThread.resumeGame();
    }
}
