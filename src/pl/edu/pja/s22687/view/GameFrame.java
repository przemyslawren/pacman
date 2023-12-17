package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.*;
import pl.edu.pja.s22687.model.GameModel;
import pl.edu.pja.s22687.threads.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GameFrame extends JFrame {
    private final GameModel model;
    private final MainMenuFrame mainMenuFrame;
    private final JTable table;
    private final JScrollPane scrollPane;
    private final JLabel scoreLabel;
    private final GameUpdateThread gameUpdateThread;
    private final GhostUpdateThread ghostUpdateThread;
    private final ScoreUpdateThread scoreUpdateThread;
    private final GhostAnimationThread ghostAnimationThread;
    private final PacmanAnimationThread pacmanAnimationThread;

    public GameFrame(GameModel model, MainMenuFrame mainMenuFrame) {
        this.model = model;
        this.mainMenuFrame = mainMenuFrame;
        this.table = new JTable(model);
        this.scrollPane = new JScrollPane(table);
        table.setDefaultRenderer(CellType.class, new MazeCellRenderer());
        configureTable(table);
        add(scrollPane, BorderLayout.CENTER);
        initializeUI();
        resizeTableToFillWindow();
        setFocusable(true);
        requestFocusInWindow();
        setupKeyBindings();

        scoreLabel = new JLabel("Score: " + model.getScore());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBackground(new Color(15, 15, 15));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        add(scoreLabel, BorderLayout.NORTH);

        gameUpdateThread = new GameUpdateThread(model, this);
        ghostUpdateThread = new GhostUpdateThread(model, this);
        scoreUpdateThread = new ScoreUpdateThread(model, this);
        ghostAnimationThread = new GhostAnimationThread(model, table);
        pacmanAnimationThread = new PacmanAnimationThread(model);
        startThreads();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdownThreads();
            }
        });

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                requestFocusInWindow();
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
        setIconImage(new ImageIcon("src/pl/edu/pja/s22687/resources/ghost_logo.png").getImage());
        setVisible(true);
    }

    private void configureTable(JTable table) {
        table.setDefaultRenderer(CellType.class, new MazeCellRenderer());
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setTableHeader(null);
        table.setCellSelectionEnabled(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setFillsViewportHeight(true);
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

    private void setupKeyBindings() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "QuitGame");
        actionMap.put("QuitGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });
    }

    public void quitGame() {
        shutdownThreads();
        this.dispose();
        mainMenuFrame.setVisible(true);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    private void startThreads() {
        gameUpdateThread.start();
        ghostUpdateThread.start();
        scoreUpdateThread.start();
        ghostAnimationThread.start();
        pacmanAnimationThread.start();
    }

    public void shutdownThreads() {
        gameUpdateThread.shutdown();
        ghostUpdateThread.shutdown();
        scoreUpdateThread.shutdown();
        ghostAnimationThread.shutdown();
        pacmanAnimationThread.shutdown();
    }
}
