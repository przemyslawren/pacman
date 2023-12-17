package pl.edu.pja.s22687.view;

import javax.swing.*;

public class HighScoresFrame extends JFrame {
    public HighScoresFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("High Scores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/pl/edu/pja/s22687/resources/ghost_logo.png").getImage());
        setVisible(true);
    }
}
