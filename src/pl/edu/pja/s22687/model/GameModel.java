package pl.edu.pja.s22687.model;

import pl.edu.pja.s22687.CellType;
import pl.edu.pja.s22687.Direction;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameModel extends AbstractTableModel {
    private int rows;
    private int cols;
    private final CellType[][] maze;
    private final boolean[][] visited;
    private final Random rand = new Random();
    private Point pacmanLocation;
    private Direction currentDirection = Direction.NONE;

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new CellType[rows][cols];
        this.visited = new boolean[rows][cols];
        initializeMaze();
        generateMaze();
        placePacman();
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
                && (rand.nextDouble() < 0.2 || !visited[row][col]);
    }

    private void placePacman() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(maze[0].length);
            y = rand.nextInt(maze.length);
        } while (maze[y][x] != CellType.CORRIDOR);

        pacmanLocation = new Point(x, y);
        maze[y][x] = CellType.PACMAN;
    }

    public void movePacman(Direction direction) {
        Point nextLocation = new Point(pacmanLocation);
        switch (direction) {
            case UP:
                nextLocation.y--;
                break;
            case DOWN:
                nextLocation.y++;
                break;
            case LEFT:
                nextLocation.x--;
                break;
            case RIGHT:
                nextLocation.x++;
                break;
        }

        if (canMove(nextLocation)) {
            maze[pacmanLocation.y][pacmanLocation.x] = CellType.CORRIDOR;
            pacmanLocation.setLocation(nextLocation);
            maze[pacmanLocation.y][pacmanLocation.x] = CellType.PACMAN;
        }

        fireTableDataChanged();
    }

    private boolean canMove(Point location) {
        return location.x >= 0 && location.x < maze[0].length
                && location.y >= 0 && location.y < maze.length
                && maze[location.y][location.x] == CellType.CORRIDOR;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void updateGameState() {
        if (currentDirection != Direction.NONE) {
            movePacman(currentDirection);
        }
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
