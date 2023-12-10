package pl.edu.pja.s22687;

import pl.edu.pja.s22687.controller.GameController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameController());

    }
}