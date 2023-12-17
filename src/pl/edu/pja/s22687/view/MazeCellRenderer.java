package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.CellType;
import pl.edu.pja.s22687.Direction;
import pl.edu.pja.s22687.model.GameModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MazeCellRenderer extends DefaultTableCellRenderer {
    private final ImageIcon pacmanIcon;
    private final ImageIcon pacmandownIcon;
    private final ImageIcon pacmanleftIcon;
    private final ImageIcon pacmanrightIcon;
    private final ImageIcon pacmanupIcon;
    private ImageIcon ghostleftIcon;
    private ImageIcon ghostrightIcon;
    private final ImageIcon foodIcon;


    public MazeCellRenderer() {
        pacmanIcon = new ImageIcon("src/pl/edu/pja/s22687/resources/pacman/pacman.jpg");
        pacmandownIcon = new ImageIcon("src/pl/edu/pja/s22687/resources/pacman/pacman_down.jpg");
        pacmanleftIcon = new ImageIcon("src/pl/edu/pja/s22687/resources/pacman/pacman_left.jpg");
        pacmanrightIcon = new ImageIcon("src/pl/edu/pja/s22687/resources/pacman/pacman_right.jpg");
        pacmanupIcon = new ImageIcon("src/pl/edu/pja/s22687/resources/pacman/pacman_up.jpg");
        ghostleftIcon = new ImageIcon("src/pl/edu/pja/s22687/resources/ghost/ghost_left.jpg");
        ghostrightIcon = new ImageIcon("src/pl/edu/pja/s22687/resources/ghost/ghost_right.jpg");
        foodIcon = new ImageIcon("src/pl/edu/pja/s22687/resources/food/food.png");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
        );
        GameModel model = (GameModel) table.getModel();
        Direction pacmanDirection = model.getPacmanDirection();

        CellType cellType = (CellType) value;
        switch (cellType) {
            case WALL:
                setText("");
                setBackground(new Color(73, 77, 176));
                setIcon(null);
                break;
            case CORRIDOR:
                if (model.getCoins()[row][column]) {
                    setText("");
                    setBackground(new Color(15, 15, 15));
                    setIcon(getFoodIcon());
                } else {
                    setText("");
                    setBackground(new Color(15, 15, 15));
                    setIcon(null);
                }
                break;
            case PACMAN:
                setText("");
                setBackground(new Color(15, 15, 15));
                ImageIcon icon = model.isPacmanMouthOpen() ? getPacmanIcon(pacmanDirection) : pacmanIcon;
                setIcon(icon);
                break;
            case GHOST:
                setText("");
                setBackground(new Color(15, 15, 15));
                setIcon(model.getCurrentGhostIcon());
                break;
        }

        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        return this;
    }

    private ImageIcon getPacmanIcon(Direction direction) {
        return switch (direction) {
            case UP -> pacmanupIcon;
            case DOWN -> pacmandownIcon;
            case LEFT -> pacmanleftIcon;
            case RIGHT -> pacmanrightIcon;
            default -> pacmanIcon;
        };
    }

    private ImageIcon getFoodIcon() {
        return foodIcon;
    }
}
