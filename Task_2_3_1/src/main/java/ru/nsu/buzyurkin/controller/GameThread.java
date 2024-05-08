package ru.nsu.buzyurkin.controller;

/**
 * Represents a thread controlling the game loop.
 */
public class GameThread extends Thread {
    private GameController controller;
    private int delay;
    private boolean isRunning = true;

    /**
     * Constructs a GameThread object with the specified game controller and delay.
     *
     * @param controller the game controller
     * @param delay      the delay between each step of the game loop
     */
    public GameThread(GameController controller, int delay) {
        this.controller = controller;
        this.delay = delay;
    }

    /**
     * Runs the game loop until interrupted.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                return;
            }

            if (isRunning) {
                controller.step();
            }
        }
    }

    /**
     * Stops the game loop.
     */
    public void stopGame() {
        isRunning = false;
    }

    /**
     * Resumes the game loop.
     */
    public void resumeGame() {
        isRunning = true;
    }

    /**
     * Checks if the game loop is paused.
     *
     * @return true if paused, false otherwise
     */
    public boolean isPaused() {
        return !isRunning;
    }
}
