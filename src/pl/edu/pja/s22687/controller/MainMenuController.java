package pl.edu.pja.s22687.controller;

import pl.edu.pja.s22687.model.GameModel;
import pl.edu.pja.s22687.view.GameFrame;
import pl.edu.pja.s22687.view.HighScoresFrame;
import pl.edu.pja.s22687.view.MainMenuFrame;
import pl.edu.pja.s22687.view.SizeOptionsFrame;

public class MainMenuController implements GameController {
    private GameModel model;
    private MainMenuFrame mainMenuFrame;

    public MainMenuController() {
        this.mainMenuFrame = new MainMenuFrame(this);
    }

    @Override
    public void startNewGame(int rows, int columns) {
        this.model = new GameModel(rows, columns);
        mainMenuFrame.setVisible(false);
        new GameFrame(model);
    }

    @Override
    public void showHighScores() {
        new HighScoresFrame();
    }

    @Override
    public void showSizeOptions() {
        new SizeOptionsFrame(this, mainMenuFrame);
    }

    @Override
    public void exitGame() {
        System.exit(0);
    }

    @Override
    public void returnToMainMenu() {
        mainMenuFrame.setVisible(true);
    }
}
