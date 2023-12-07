package pl.edu.pja.s22687.model;

import javax.swing.table.AbstractTableModel;

public class MazeTableModel extends AbstractTableModel {
    private CellType[][] mazeData;

    public MazeTableModel(int rows, int columns) {
        this.mazeData = new CellType[rows][columns];
        initializeMaze();
    }

    private void initializeMaze() {
        for (int row = 0; row < mazeData.length; row++) {
            for (int col = 0; col < mazeData[row].length; col++) {
                if (isWallPosition(row, col)) {
                    mazeData[row][col] = CellType.WALL;
                } else {
                    mazeData[row][col] = CellType.COIN;
                }
            }
        }
        // Dodaj monety i inne elementy, jeśli to konieczne
    }

    private boolean isWallPosition(int row, int col) {
        // Prosta logika definiująca, gdzie znajdują się ściany
        // Na przykład tworzenie granic labiryntu:
        if (row == 0 || col == 0 || row == mazeData.length - 1 || col == mazeData[0].length - 1) {
            return true;
        }
        // Możesz dodać inne wzory dla ścian wewnątrz labiryntu
        return false;
    }

    public int getRowCount() { return mazeData.length; }
    public int getColumnCount() { return mazeData[0].length; }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return CellType.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return mazeData[rowIndex][columnIndex];
    }

    public CellType[][] getMazeData() { return mazeData; }

    public void setMazeData(CellType[][] mazeData) { this.mazeData = mazeData; }
}
