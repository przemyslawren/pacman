package pl.edu.pja.s22687.model;

import pl.edu.pja.s22687.CellType;
import pl.edu.pja.s22687.Direction;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameModel extends AbstractTableModel {
    private int score = 0;
    private int rows;
    private int cols;
    private final CellType[][] maze;

    private final boolean[][] visited;
    private boolean[][] coins;
    private boolean[][] ghosts;
    private final Random rand = new Random();
    private Point pacmanLocation;
    private Point ghostLocation;
    private Direction currentDirection = Direction.NONE;

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new CellType[rows][cols];
        this.visited = new boolean[rows][cols];
        this.coins = new boolean[rows][cols];
        this.ghosts = new boolean[rows][cols];
        initializeMaze();
        generateMaze();
        placeCoins();
        pacmanLocation = placeObject(CellType.PACMAN);
        placeGhosts(calculateDifficulty(rows, cols));
        moveGhosts();
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

            if (coins[nextLocation.y][nextLocation.x]) {
                coins[nextLocation.y][nextLocation.x] = false;
                addScore(10);
            }

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

    private void placeCoins() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (maze[row][col] == CellType.CORRIDOR) {
                    coins[row][col] = true;
                }
            }
        }
    }

    private int calculateDifficulty(int rows, int cols) {
        return (rows + cols) / 10;
    }

    public boolean[][] getCoins() {
        return coins;
    }

    public synchronized void addScore(int points) {
        this.score += points;
    }

    public synchronized int getScore() {
        return score;
    }

    private void placeGhosts(int difficulty) {
        for (int i = 1; i <= difficulty; i++) {
            placeObject(CellType.GHOST);
        }
    }

    private void moveGhosts() {
        for (int i = 0; i < ghosts.length; i++) {
            for (int j = 0; j < ghosts[i].length; j++) {
                if (ghosts[i][j]) {
                    ghosts[i][j] = false;
                    maze[i][j] = CellType.CORRIDOR;
                }
            }
        }
        for (int i = 0; i < ghosts.length; i++) {
            for (int j = 0; j < ghosts[i].length; j++) {
                if (maze[i][j] == CellType.GHOST) {
                    ghosts[i][j] = true;
                }
            }
        }
        for (int i = 0; i < ghosts.length; i++) {
            for (int j = 0; j < ghosts[i].length; j++) {
                if (ghosts[i][j]) {
                    ghostLocation = new Point(j, i);
                    maze[i][j] = CellType.GHOST;
                    moveGhost(ghostLocation);
                }
            }
        }
    }

    private void moveGhost(Point ghostLocation) {
        Point nextLocation = new Point(ghostLocation);
        int direction = rand.nextInt(4);
        switch (direction) {
            case 0:
                nextLocation.y--;
                break;
            case 1:
                nextLocation.y++;
                break;
            case 2:
                nextLocation.x--;
                break;
            case 3:
                nextLocation.x++;
                break;
        }

        if (canMove(nextLocation)) {
            maze[ghostLocation.y][ghostLocation.x] = CellType.CORRIDOR;
            maze[nextLocation.y][nextLocation.x] = CellType.GHOST;
        }
    }

    public boolean[][] getGhosts() {
        return ghosts;
    }
    public Point placeObject(CellType type) {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(maze[0].length);
            y = rand.nextInt(maze.length);
        } while (maze[y][x] != CellType.CORRIDOR);

        maze[y][x] = type;
        System.out.println("Object placed at: " + x + ", " + y);
        return new Point(x, y);
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
