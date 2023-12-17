package pl.edu.pja.s22687.threads;

import pl.edu.pja.s22687.model.GameModel;

public class PacmanAnimationThread extends Thread {
    private final GameModel model;
    private volatile boolean running = true;

    public PacmanAnimationThread(GameModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        while (running) {
            model.togglePacmanMouth();
            model.fireTableDataChanged();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}
