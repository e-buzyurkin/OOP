package ru.nsu.buzyurkin.controller;

public class GameThread extends Thread {
    private GameController controller;
    private int delay;
    private boolean isRunning = true;

    public GameThread(GameController controller, int delay) {
        this.controller = controller;
        this.delay = delay;
    }

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

    public void stopGame() {
        isRunning = false;
    }

    public void resumeGame() {
        isRunning = true;
    }

    public boolean isPaused() {
        return !isRunning;
    }
}
