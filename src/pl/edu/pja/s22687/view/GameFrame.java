package pl.edu.pja.s22687.view;

import javax.swing.*;

public class GameFrame {


    public GameFrame(int x, int y) {
        JFrame frame = new JFrame("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JTable table = new JTable(x, y);

        frame.add(table);
        frame.setVisible(true);
    }
}
