package pl.edu.pja.s22687.view;

import javax.swing.*;

public class SizeOptionsFrame {

    public SizeOptionsFrame() {
        while (true) {
            JTextField rowsField = new JTextField(5);
            JTextField columnsField = new JTextField(5);

            JPanel panel = new JPanel();
            panel.add(new JLabel("Rows:"));
            panel.add(rowsField);
            panel.add(Box.createHorizontalStrut(15));
            panel.add(new JLabel("Columns:"));
            panel.add(columnsField);

            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Choose the size of maze", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int rows = Integer.parseInt(rowsField.getText());
                    int columns = Integer.parseInt(columnsField.getText());
                    if (rows < 10 || columns < 10 || rows > 100 || columns > 100) {
                        JOptionPane.showMessageDialog(null, "The size must be between 10-100 x 10-100", "Wrong size", JOptionPane.ERROR_MESSAGE);
                    } else {
                        new GameFrame(rows, columns);
                        break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "The size of maze must be a number", "Wrong type", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                new MainMenuFrame();
                break;
            }
        }
    }
}
