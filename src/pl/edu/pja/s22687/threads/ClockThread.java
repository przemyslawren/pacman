package pl.edu.pja.s22687.threads;

public class ClockThread extends Thread {
    private volatile boolean running = true;
    private int seconds = 0;

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                seconds++;
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void shutdown() {
        running = false;
    }

    public int getSeconds() {
        return seconds;
    }
}
