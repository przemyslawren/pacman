package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.model.CellType;
import pl.edu.pja.s22687.model.MazeTableModel;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(int rows, int columns) {
        MazeTableModel model = new MazeTableModel(rows, columns);
        JTable table = new JTable(model);
        table.setDefaultRenderer(CellType.class, new MazeCellRenderer());
        configureTable(table);
        add(new JScrollPane(table));
        initializeUI(rows, columns);
    }

    private void initializeUI(int rows, int columns) {
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/pl/edu/pja/s22687/resources/ghost.png").getImage());
        setVisible(true);
    }

    private void configureTable(JTable table) {
        table.setDefaultRenderer(CellType.class, new MazeCellRenderer());
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setTableHeader(null);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setFillsViewportHeight(true);
    }

}
