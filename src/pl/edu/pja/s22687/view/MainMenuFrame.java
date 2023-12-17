package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.GameManager;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private final GameManager controller;

    public MainMenuFrame(GameManager controller) {
        this.controller = controller;
        initializeUI();
    }

        private void initializeUI() {
            setTitle("Pacman");
            setDefaultLookAndFeelDecorated(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 500);
            setLocationRelativeTo(null);
            setResizable(true);
            setIconImage(new ImageIcon("src/pl/edu/pja/s22687/resources/ghost_logo.png").getImage());

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.DARK_GRAY);

            JPanel logoPanel = new JPanel();
            logoPanel.setBackground(Color.DARK_GRAY);
            logoPanel.add(new JLabel(new ImageIcon("src/pl/edu/pja/s22687/resources/pacman_logo.png")));
            panel.add(logoPanel);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
            buttonPanel.setBackground(Color.DARK_GRAY);

            panel.add(buttonPanel);

            JButton newGameButton = new JButton("New Game");
            JButton highScoresButton = new JButton("High Scores");
            JButton exitButton = new JButton("Exit");

            newGameButton.addActionListener(e -> controller.showSizeOptions());
            highScoresButton.addActionListener(e -> controller.showHighScores());
            exitButton.addActionListener(e -> controller.exitGame());

            setButtonLooks(newGameButton);
            setButtonLooks(highScoresButton);
            setButtonLooks(exitButton);

            buttonPanel.add(newGameButton);
            buttonPanel.add(highScoresButton);
            buttonPanel.add(exitButton);
            add(panel);

            setVisible(true);
        }

    private void setButtonLooks(JButton button) {
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GRAY);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY);
            }
        });
    }
}
