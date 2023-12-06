package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.model.CellType;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MazeCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
        );


        CellType cellType = (CellType) value;

        switch (cellType) {
            case WALL:
                setBackground(Color.BLACK);
                break;
            case CORRIDOR:
                setBackground(Color.WHITE);
                break;
            case COIN:
                setBackground(Color.YELLOW);
                break;
            case PACMAN:
                setBackground(Color.ORANGE);
                break;
            case GHOST:
                setBackground(Color.BLUE);
                break;
            default:
                setBackground(Color.GRAY);
                setText("");
                break;
        }

        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        return this;
    }
}
