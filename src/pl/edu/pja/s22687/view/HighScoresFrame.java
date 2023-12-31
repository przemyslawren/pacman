package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.GameManager;
import pl.edu.pja.s22687.HighScore;
import pl.edu.pja.s22687.HighScoresManager;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class HighScoresFrame extends JFrame {
    private final GameManager controller;
    private JList<HighScore> highScoreList;
    private DefaultListModel<HighScore> highScoreModel;

    public HighScoresFrame(GameManager controller) {
        this.controller = controller;
        highScoreModel = new DefaultListModel<>();
        highScoreList = new JList<>(highScoreModel);
        loadHighScores();
        add(new JScrollPane(highScoreList), BorderLayout.CENTER);

        JButton removeButton = new JButton("Remove");
        JButton backButton = new JButton("Back");

        removeButton.addActionListener(e -> {
            removeSelectedHighScore();
        });
        backButton.addActionListener(e -> returnToMainMenu());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
        initializeUI();
    }

    private void loadHighScores() {
        highScoreModel.clear();
        HighScoresManager.loadHighScores().forEach(highScoreModel::addElement);
    }

    private void removeSelectedHighScore() {
        int selectedIndex = highScoreList.getSelectedIndex();
        if (selectedIndex >= 0) {
            HighScore highScore = highScoreModel.getElementAt(selectedIndex);
            highScoreModel.remove(selectedIndex);
            HighScoresManager.remove(highScore);
        }
        HighScoresManager.saveHighScores(Collections.list((highScoreModel.elements())));
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
