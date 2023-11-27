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
            panel.add(new JButton("New Game"));
            panel.add(new JButton("High Scores"));
            panel.add(new JButton("Exit"));
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(panel);
        }
}
