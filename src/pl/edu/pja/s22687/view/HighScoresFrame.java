package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.GameManager;
import pl.edu.pja.s22687.HighScore;
import pl.edu.pja.s22687.HighScoresManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class HighScoresFrame extends JFrame {
    private final GameManager controller;
    private JList<HighScore> highScoreList;
    private DefaultListModel<HighScore> highScoreModel;

    public HighScoresFrame(GameManager controller) {
        this.controller = controller;
        List<HighScore> highScores = HighScoresManager.loadHighScores();
        JList<HighScore> highScoreList = new JList<>(new Vector<>(highScores));
        loadHighScores();
        add(new JScrollPane(highScoreList), BorderLayout.CENTER);

        JButton removeButton = new JButton("Remove");
        JButton backButton = new JButton("Back");

        removeButton.addActionListener(e -> removeSelectedHighScore());
        backButton.addActionListener(e -> returnToMainMenu());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
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

    private void removeSelectedHighScore() {
        int selectedIndex = highScoreList.getSelectedIndex();
        if (selectedIndex != -1) {
            HighScore highScore = highScoreModel.getElementAt(selectedIndex);
            HighScoresManager.remove(highScore);
            highScoreModel.remove(selectedIndex);
        }
    }

    private void returnToMainMenu() {
        dispose();
        controller.returnToMainMenu();
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
