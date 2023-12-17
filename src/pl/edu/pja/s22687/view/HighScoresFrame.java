package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.HighScore;
import pl.edu.pja.s22687.HighScoresManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class HighScoresFrame extends JFrame {
    private JList<HighScore> highScoreList;
    private DefaultListModel<HighScore> highScoreModel;

    public HighScoresFrame() {
        List<HighScore> highScores = HighScoresManager.loadHighScores();
        JList<HighScore> highScoreList = new JList<>(new Vector<>(highScores));
        loadHighScores();
        add(new JScrollPane(highScoreList), BorderLayout.CENTER);
        initializeUI();
    }

    private void loadHighScores() {
        List<HighScore> highScores = HighScoresManager.loadHighScores();
        highScoreModel = new DefaultListModel<>();
        for (HighScore highScore : highScores) {
            highScoreModel.addElement(highScore);
        }
        highScoreList = new JList<>(highScoreModel);

    }

    private void initializeUI() {
        setTitle("High Scores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/pl/edu/pja/s22687/resources/ghost_logo.png").getImage());
        setVisible(true);
    }
}
