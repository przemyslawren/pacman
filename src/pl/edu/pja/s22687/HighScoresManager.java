package pl.edu.pja.s22687;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoresManager {

    private static final String FILE_PATH = "highscores.ser";

    public static void saveHighScores(List<HighScore> highScores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<HighScore> loadHighScores() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (List<HighScore>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
