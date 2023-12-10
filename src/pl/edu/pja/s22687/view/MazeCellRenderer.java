package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.CellType;
import pl.edu.pja.s22687.model.GameModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MazeCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
        );
        GameModel model = (GameModel) table.getModel();

        boolean hasCoin = model.getCoins()[row][column];

        CellType cellType = (CellType) value;
        switch (cellType) {
            case WALL:
                setText("");
                setBackground(new Color(73,77,176));
                break;
            case CORRIDOR:
                if (hasCoin) {
                    setText("C");
                    setBackground(new Color(15,15,15));
                } else {
                    setText("");
                    setBackground(new Color(15,15,15));
                }
                break;
            case COIN:
                setText("");
                setBackground(Color.YELLOW);
                break;
            case PACMAN:
                setText("");
                setBackground(Color.ORANGE);
                break;
            case GHOST:
                setText("");
                setBackground(Color.BLUE);
                break;
            default:
                break;
        }

        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        return this;
    }
}
