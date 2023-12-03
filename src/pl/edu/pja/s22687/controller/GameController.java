package pl.edu.pja.s22687.controller;

public interface GameController {
    void startNewGame(int rows, int columns);
    void showHighScores();
    void showSizeOptions();
    void exitGame();
    void returnToMainMenu();
}