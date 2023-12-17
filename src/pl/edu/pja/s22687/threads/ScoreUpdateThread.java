package pl.edu.pja.s22687.threads;

import pl.edu.pja.s22687.model.GameModel;
import pl.edu.pja.s22687.view.GameFrame;

import javax.swing.*;

public class ScoreUpdateThread extends Thread {
    private final GameModel model;
    private final GameFrame frame;
    private volatile boolean running = true;

    public ScoreUpdateThread(GameModel model, GameFrame frame) {
        this.model = model;
        this.frame = frame;
    }

    @Override
    public void run() {
        while (running) {
            if (frame.isActive()) {
                int currentScore = model.getScore();
                SwingUtilities.invokeLater(() -> frame.updateScore(currentScore));
                System.out.println("Score: " + currentScore);
            }
            try {
                Thread.sleep(200);
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
