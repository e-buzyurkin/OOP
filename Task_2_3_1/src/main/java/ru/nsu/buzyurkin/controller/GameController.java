package ru.nsu.buzyurkin.controller;

import javafx.application.Platform;
import ru.nsu.buzyurkin.entity.GameModel;
import ru.nsu.buzyurkin.state.Direction;
import ru.nsu.buzyurkin.ui.SnakeUI;

public class GameController {
    private static int DELAY = 200;

    private GameThread gameThread;
    private GameModel gameModel;
    private SnakeUI snakeUI;

    private Direction dir = Direction.UP;
    private boolean paused = false;

    public GameController(GameModel gameModel, SnakeUI snakeUI) {
        this.gameModel = gameModel;
        this.snakeUI = snakeUI;

        KeyboardHandler keyboardHandler = new KeyboardHandler(this);

        gameThread = new GameThread(this, DELAY);

        snakeUI.setKeyPressedHandler(keyboardHandler);
        snakeUI.setCloseHandler(event -> gameThread.interrupt());
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void pauseGame() {
        if (!gameThread.isPaused()) {
            paused = true;
        }
    }

    public void showHelp() {
        snakeUI.showHelp();
    }

    public void victory() {
        snakeUI.showVictoryLabel();
        gameThread.stopGame();
    }

    public void defeat() {
        snakeUI.showDefeatLabel(gameModel.getScore());
        gameThread.stopGame();
    }

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
                }
            }
        } catch (Exception e) {
            Platform.runLater(snakeUI::showError);
            gameThread.stopGame();
        }
    }

    public void startGame() {
        gameThread.start();
    }

    public void resetGame() {
        snakeUI.hideLabels();
        gameModel.resetGame();
    }
}
