package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.CellType;
import pl.edu.pja.s22687.Direction;
import pl.edu.pja.s22687.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame {
    private GameModel model;
    private JTable table;
    private JScrollPane scrollPane;

    public GameFrame(GameModel model) {
        this.model = model;
        this.table = new JTable(model);
        this.scrollPane = new JScrollPane(table);
        table.setDefaultRenderer(CellType.class, new MazeCellRenderer());
        configureTable(table);
        add(scrollPane, BorderLayout.CENTER);
        initializeUI();
        resizeTableToFillWindow();

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    private void initializeUI() {
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

        //dodaj żeby nie było przerwy między komórkami
        table.setIntercellSpacing(new Dimension(0, 0));
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

    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                model.movePacman(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                model.movePacman(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                model.movePacman(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                model.movePacman(Direction.RIGHT);
                break;
            default:
                break;
        }
    }
}
