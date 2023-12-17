package pl.edu.pja.s22687.threads;

import javax.swing.*;

public class ClockThread extends Thread {
    private volatile boolean running = true;
    private final JLabel timeLabel;
    private int seconds = 0;

    public ClockThread(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                seconds++;
                SwingUtilities.invokeLater(() -> timeLabel.setText(formatTime(seconds)));
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void shutdown() {
        running = false;
    }

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public int getSeconds() {
        return seconds;
    }
}
