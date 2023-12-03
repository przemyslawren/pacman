package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.controller.GameController;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private GameController controller;

    public MainMenuFrame(GameController controller) {
        this.controller = controller;
        initalizeUI();
    }

        private void initalizeUI() {
            setTitle("Pacman");
            setDefaultLookAndFeelDecorated(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 500);
            setLocationRelativeTo(null);
            setResizable(true);
            setIconImage(new ImageIcon("src/pl/edu/pja/s22687/resources/ghost.png").getImage());

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 200));
            panel.setBackground(Color.DARK_GRAY);

            JButton newGameButton = new JButton("New Game");
            JButton highScoresButton = new JButton("High Scores");
            JButton exitButton = new JButton("Exit");

            newGameButton.addActionListener(e -> controller.showSizeOptions());
            highScoresButton.addActionListener(e -> controller.showHighScores());
            exitButton.addActionListener(e -> controller.exitGame());

            panel.add(newGameButton);
            panel.add(highScoresButton);
            panel.add(exitButton);
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(panel);

            setVisible(true);
        }
}
