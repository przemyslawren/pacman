package pl.edu.pja.s22687.view;

import pl.edu.pja.s22687.controller.GameController;
import java.awt.*;

public class SizeOptionsFrame {
    private GameController controller;

    public SizeOptionsFrame(GameController controller, Frame owner) {
        this.controller = controller;
        SizeOptionsDialog dialog = new SizeOptionsDialog(owner, controller);
        dialog.setVisible(true);

        if (!dialog.isConfirmed()) {
            controller.returnToMainMenu();
        }
    }
}
