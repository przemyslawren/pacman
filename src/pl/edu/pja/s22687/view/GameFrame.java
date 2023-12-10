package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.CellType;
import pl.edu.pja.s22687.Direction;
import pl.edu.pja.s22687.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {
    private GameModel model;
    private Timer gameTimer;
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

        gameTimer = new Timer(200, e -> model.updateGameState());
        gameTimer.start();

        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                if (!gameTimer.isRunning()) {
                    gameTimer.start();
                }
            }
        });

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
                model.setCurrentDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                model.setCurrentDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                model.setCurrentDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                model.setCurrentDirection(Direction.RIGHT);
                break;
            default:
                break;
        }
    }
}
