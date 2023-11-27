package pl.edu.pja.s22687.view;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    //FlowLayout

        public MainMenuFrame() {
            super("Pacman");
            setDefaultLookAndFeelDecorated(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 500);
            setLocationRelativeTo(null);
            setResizable(true);
            setVisible(true);
            setIconImage(new ImageIcon("src/pl/edu/pja/s22687/resources/ghost.png").getImage());
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 200));
            panel.setBackground(Color.DARK_GRAY);

            JButton newGameButton = new JButton("New Game");
            JButton highScoresButton = new JButton("High Scores");
            JButton exitButton = new JButton("Exit");

            newGameButton.addActionListener(e -> {
                new GameFrame();
                exitMainMenuFrame();
            });

            highScoresButton.addActionListener(e -> {
                new HighScoresFrame();
                exitMainMenuFrame();
            });

            exitButton.addActionListener(e -> {
                System.exit(0);

            });


            panel.add(newGameButton);
            panel.add(highScoresButton);
            panel.add(exitButton);
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(panel);
        }

        public void exitMainMenuFrame() {
            this.dispose();
        }
}