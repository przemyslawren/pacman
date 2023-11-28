package pl.edu.pja.s22687.view;

import javax.swing.*;

public class GameFrame extends JFrame {


    public GameFrame(int rows, int columns) {
        super("Pacman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/pl/edu/pja/s22687/resources/ghost.png").getImage());
        JTable table = new JTable(rows, columns);
        add(table);
        setVisible(true);
    }
}
