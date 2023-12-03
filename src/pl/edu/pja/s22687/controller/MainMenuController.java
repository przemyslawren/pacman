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
        this.model = new GameModel();
        this.mainMenuFrame = new MainMenuFrame(this);
    }

    @Override
    public void startNewGame(int rows, int columns) {
        model.setRows(rows);
        model.setColumns(columns);
        mainMenuFrame.setVisible(false);
        new GameFrame(rows, columns);
    }

    @Override
    public void showHighScores() {
        new HighScoresFrame();
    }

    @Override
    public void showSizeOptions() {
        new SizeOptionsFrame(this);
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
