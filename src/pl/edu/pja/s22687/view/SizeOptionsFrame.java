package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.GameManager;
import java.awt.*;

public class SizeOptionsFrame {
    private GameManager controller;

    public SizeOptionsFrame(GameManager controller, Frame owner) {
        this.controller = controller;
        SizeOptionsDialog dialog = new SizeOptionsDialog(owner, controller);
        dialog.setVisible(true);

        if (!dialog.isConfirmed()) {
            controller.returnToMainMenu();
        }
    }
}
