package pl.edu.pja.s22687.threads;

import pl.edu.pja.s22687.model.GameModel;

import javax.swing.*;

public class GhostAnimationThread extends Thread {
    private final GameModel model;
    private final JTable table;
    private volatile boolean running = true;
    private boolean useLeftIcon = true;

    public GhostAnimationThread(GameModel model, JTable table) {
        this.model = model;
        this.table = table;
    }

    @Override
    public void run() {
        while (running) {
            model.toggleGhostIcon();
            model.fireTableDataChanged(); // Powiadom renderer o zmianie
            try {
                Thread.sleep(1000); // Czekaj 1 sekundę
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void shutdown() {
        running = false; // Metoda do zakończenia wątku
    }
}
