package pl.edu.pja.s22687.model;

import pl.edu.pja.s22687.CellType;
import pl.edu.pja.s22687.Direction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameModel extends AbstractTableModel {
    private int score = 0;
    private int rows;
    private int cols;
    private CellType[][] maze;

    private boolean[][] visited;
    private boolean[][] coins;
    private boolean[][] ghosts;
    private final Random rand = new Random();
    private final Point pacmanLocation;
    private Direction currentDirection = Direction.NONE;

    public GameModel(int rows, int cols) {
        initializeFields(rows, cols);
        initializeMaze();
        generateMaze();
        placeCoins();
        pacmanLocation = placePacman();
        placeGhosts(calculateDifficulty(rows, cols));
        moveGhosts();
    }

    private void initializeFields(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new CellType[rows][cols];
        this.visited = new boolean[rows][cols];
        this.coins = new boolean[rows][cols];
        this.ghosts = new boolean[rows][cols];
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

    public Point placePacman() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(maze[0].length);
            y = rand.nextInt(maze.length);
        } while (maze[y][x] != CellType.CORRIDOR);

        maze[y][x] = CellType.PACMAN;
        System.out.println("Pacman placed at: " + x + ", " + y);
        return new Point(x, y);
    }

    public synchronized void movePacman(Direction direction) {
        Point nextLocation = new Point(pacmanLocation);
        moveToNextLocation(direction, nextLocation);

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

    private void moveToNextLocation(Direction direction, Point nextLocation) {
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
            if (areAllCoinsCollected()) {
                // Wyświetl komunikat o wygranej
                JOptionPane.showMessageDialog(null, "Wygrałeś! Twój wynik to: " + getScore(), "Koniec gry", JOptionPane.INFORMATION_MESSAGE);
                // Zapisz wynik
                saveHighScore(getScore());
                // Resetuj grę lub zamknij okno gry
            }
        }
    }

    private void saveHighScore(int score) {

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
        int totalCells = rows * cols;
        int difficulty = totalCells / 50;
        return Math.max(2, difficulty);
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

    private void placeGhost() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(maze[0].length);
            y = rand.nextInt(maze.length);
        } while (maze[y][x] != CellType.CORRIDOR);

        maze[y][x] = CellType.GHOST;
        ghosts[y][x] = true;
    }

    private void placeGhosts(int difficulty) {
        for (int i = 1; i <= difficulty; i++) {
            placeGhost();
        }
    }

    public synchronized void moveGhosts() {
        for (int row = 0; row < ghosts.length; row++) {
            for (int col = 0; col < ghosts[0].length; col++) {
                if (ghosts[row][col]) {
                    ghosts[row][col] = false;
                    maze[row][col] = CellType.CORRIDOR;
                    moveGhost(row, col);
                }
            }
        }
    }

    private synchronized void moveGhost(int row, int col) {
        List<Direction> possibleDirections = getPossibleDirections(row, col);
        if (!possibleDirections.isEmpty()) {
            Direction dir = possibleDirections.get(rand.nextInt(possibleDirections.size()));
            Point nextLocation = calculateNextLocation(row, col, dir);
            ghosts[nextLocation.y][nextLocation.x] = true;
            maze[nextLocation.y][nextLocation.x] = CellType.GHOST;
        } else {
            ghosts[row][col] = true;
            maze[row][col] = CellType.GHOST;
        }
    }

    private List<Direction> getPossibleDirections(int row, int col) {
        List<Direction> possibleDirections = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            Point nextLocation = calculateNextLocation(row, col, dir);
            if (canMove(nextLocation.y, nextLocation.x)) {
                possibleDirections.add(dir);
            }
        }
        return possibleDirections;
    }

    private Point calculateNextLocation(int row, int col, Direction dir) {
        Point nextLocation = new Point(row, col);
        moveToNextLocation(dir, nextLocation);
        return nextLocation;
    }

    private boolean canMove(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && maze[row][col] != CellType.WALL;
    }

    public synchronized boolean areAllCoinsCollected() {
        for (boolean[] coin : coins) {
            for (int col = 0; col < coins[0].length; col++) {
                if (coin[col]) {
                    return false;  // Znaleziono monetę, która jeszcze nie została zebrana
                }
            }
        }
        return true;  // Wszystkie monety zostały zebrane
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
