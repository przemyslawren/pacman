package pl.edu.pja.s22687.model;

public class GameModel {
    private int rows;
    private int columns;

    public GameModel() {
        this.rows = 0;
        this.columns = 0;
    }

    public int getRows() { return rows; }
    public int getColumns() { return columns; }

    public void setRows(int rows) { this.rows = rows; }
    public void setColumns(int columns) { this.columns = columns; }
}
