package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.model.CellType;
import pl.edu.pja.s22687.model.MazeTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;

    public GameFrame(int rows, int columns) {
        MazeTableModel model = new MazeTableModel(rows, columns);
        this.table = new JTable(model);
        this.scrollPane = new JScrollPane(table);
        table.setDefaultRenderer(CellType.class, new MazeCellRenderer());
        configureTable(table);
        add(scrollPane, BorderLayout.CENTER);
        initializeUI(rows, columns);
        resizeTableToFillWindow();
    }

    private void initializeUI(int rows, int columns) {
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setSize(1024, 768);
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

    private void resizeTableToFillWindow() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleTable();
            }
        });
    }

    private void scaleTable() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int tableWidth = scrollPane.getViewport().getWidth();
        int tableHeight = scrollPane.getViewport().getHeight();

        int rowHeight = tableHeight / table.getRowCount();
        int columnWidth = tableWidth / table.getColumnCount();

        table.setRowHeight(rowHeight);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth);
        }
    }
}
