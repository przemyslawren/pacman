package pl.edu.pja.s22687.model;

import pl.edu.pja.s22687.CellType;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class GameModel extends AbstractTableModel {
    private int rows;
    private int cols;
    private final CellType[][] maze;
    private final boolean[][] visited;
    private final Random rand = new Random();

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new CellType[rows][cols];
        this.visited = new boolean[rows][cols];
        initializeMaze();
        generateMaze();
    }

    private void initializeMaze() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maze[row][col] = CellType.WALL;
            }
        }
    }

    public void generateMaze() {
        // Rozpocznij od losowego punktu
        dfs(rand.nextInt(rows), rand.nextInt(cols));
    }

    private void dfs(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols || visited[row][col]) {
            return;
        }

        visited[row][col] = true;
        maze[row][col] = CellType.CORRIDOR;

        // Lista kierunków: góra, dół, lewo, prawo
        List<int[]> directions = new ArrayList<>(Arrays.asList(
                new int[]{-2, 0}, new int[]{2, 0}, new int[]{0, -2}, new int[]{0, 2}
        ));

        Collections.shuffle(directions); // Losowa kolejność kierunków

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (canCreateCorridor(newRow, newCol)) {
                maze[(row + newRow) / 2][(col + newCol) / 2] = CellType.CORRIDOR;
                dfs(newRow, newCol);
            }
        }
    }

    private boolean canCreateCorridor(int row, int col) {
        // Uproszczona wersja warunku dla przełamania ściany
        return row > 0 && col > 0 && row < rows - 1 && col < cols - 1
                && (rand.nextDouble() < 0.5 || !visited[row][col]);
    }

    public CellType[][] getMaze() {
        return maze;
    }

    @Override
    public int getRowCount() {
        return maze.length;
    }



    @Override
    public int getColumnCount() {
        return maze[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return maze[rowIndex][columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return CellType.class;
    }
}
