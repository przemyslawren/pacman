package pl.edu.pja.s22687;

public interface GameManager {
    void startNewGame(int rows, int columns);
    void showHighScores();
    void showSizeOptions();
    void exitGame();
    void returnToMainMenu();
}