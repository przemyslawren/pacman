package pl.edu.pja.s22687;

import java.io.Serializable;

public class HighScore implements Serializable {
    private final String name;
    private final int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }



    @Override
    public String toString() {
        return name + " " + score;
    }
}
