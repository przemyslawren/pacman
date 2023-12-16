package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.GameManager;

import javax.swing.*;
import java.awt.*;

public class SizeOptionsDialog extends JDialog {
    JTextField rowsField = new JTextField(5);
    JTextField columnsField = new JTextField(5);
    private int rows;
    private int columns;
    private boolean confirmed = false;

    public SizeOptionsDialog(Frame owner, GameManager controller) {
        super(owner, "Size options", true);
        initializeUI();
        setupActions(controller);
    }

    private void initializeUI() {
        JPanel panel = new JPanel();

        panel.add(new JLabel("Rows:"));
        panel.add(rowsField);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(new JLabel("Columns:"));
        panel.add(columnsField);

        add(panel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void setupActions(GameManager controller) {
        JButton okButton = (JButton) ((JPanel) getContentPane().getComponent(1)).getComponent(0);
        JButton cancelButton = (JButton) ((JPanel) getContentPane().getComponent(1)).getComponent(1);

        okButton.addActionListener(e -> {
            try {
                rows = Integer.parseInt(rowsField.getText());
                columns = Integer.parseInt(columnsField.getText());
                if (rows < 10 || columns < 10 || rows > 100 || columns > 100) {
                    JOptionPane.showMessageDialog(this, "The size must be between 10-100 x 10-100", "Wrong size", JOptionPane.ERROR_MESSAGE);
                } else {
                    confirmed = true;
                    controller.startNewGame(rows, columns);
                    dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "The size of maze must be a number", "Wrong type", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
