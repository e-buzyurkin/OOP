package ru.nsu.buzyurkin.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.buzyurkin.state.Direction;

/**
 * Handles keyboard events for controlling the game.
 */
public class KeyboardHandler implements EventHandler<KeyEvent> {
    private GameController controller;

    /**
     * Constructs a KeyboardHandler object with the specified game controller.
     *
     * @param controller the game controller
     */
    public KeyboardHandler(GameController controller) {
        this.controller = controller;
    }

    /**
     * Handles keyboard events to control the game.
     *
     * @param event the keyboard event
     */
    @Override
    public void handle(KeyEvent event) {
        KeyCode key = event.getCode();

        switch (key) {
            case W -> {
                controller.setDir(Direction.UP);
            }
            case S -> {
                controller.setDir(Direction.DOWN);
            }
            case A -> {
                controller.setDir(Direction.LEFT);
            }
            case D -> {
                controller.setDir(Direction.RIGHT);
            }
            case P -> {
                controller.pauseGame();
            }
            case R -> {
                controller.resetGame();
            }
            case H -> {
                controller.showHelp();
            }
            default -> {
                break;
            }
        }
    }
}
