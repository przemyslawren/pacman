package pl.edu.pja.s22687;

import pl.edu.pja.s22687.model.GameModel;
import pl.edu.pja.s22687.view.GameFrame;

public class GhostUpdateThread extends Thread {
    private final GameModel model;
    private final GameFrame frame;
    private volatile boolean running = true;

    public GhostUpdateThread(GameModel model, GameFrame frame) {
        this.model = model;
        this.frame = frame;
    }

    @Override
    public void run() {
        while (running) {
            if (frame.isActive()) {
                model.moveGhosts();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void shutdown() {
        running = false;
        interrupt();
    }
}
