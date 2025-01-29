

import java.util.Random;

public class GameBoard {
    private int[][] grid;
    private int score;
    private int size;
    private Random random;

    public GameBoard(int size) {
        this.size = size;
        this.grid = new int[size][size];
        this.random = new Random();
        initializeGrid();
    }

    public void initializeGrid() {
        addNewTile();
        addNewTile();
    }


    public void reset() {
        this.grid = new int[size][size];
        this.score = 0;
        initializeGrid();
    }

    public void setSize(int newSize) {
        this.size = newSize;
        reset();
    }

    public boolean move(Direction direction) {
        int[][] previous = copyGrid();
        switch (direction) {
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
        }
        return !gridsEqual(previous, grid);
    }

    private void moveLeft() {
        for (int i = 0; i < size; i++) {
            grid[i] = processRow(grid[i]);
        }
    }

    private void moveRight() {
        for (int i = 0; i < size; i++) {
            grid[i] = reverseRow(processRow(reverseRow(grid[i])));
        }
    }

    private void moveUp() {
        transpose();
        moveLeft();
        transpose();
    }

    private void moveDown() {
        transpose();
        moveRight();
        transpose();
    }

    private int[] processRow(int[] row) {
        int[] newRow = new int[size];
        int index = 0;
        for (int num : row) {
            if (num != 0) {
                if (newRow[index] == 0) {
                    newRow[index] = num;
                } else if (newRow[index] == num) {
                    newRow[index] *= 2;
                    score += newRow[index];
                    index++;
                } else {
                    index++;
                    newRow[index] = num;
                }
            }
        }
        return newRow;
    }

    private int[] reverseRow(int[] row) {
        int[] reversed = new int[size];
        for (int i = 0; i < size; i++) {
            reversed[i] = row[size - 1 - i];
        }
        return reversed;
    }

    private void transpose() {
        int[][] transposed = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                transposed[j][i] = grid[i][j];
            }
        }
        grid = transposed;
    }

    public void addNewTile() {
        int value = random.nextInt(10) == 0 ? 4 : 2;
        int x, y;
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (grid[x][y] != 0);
        grid[x][y] = value;
    }

    public boolean isGameOver() {
        if (hasEmptyTiles()) return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((j < size - 1 && grid[i][j] == grid[i][j + 1]) ||
                        (i < size - 1 && grid[i][j] == grid[i + 1][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasEmptyTiles() {
        for (int[] row : grid) {
            for (int num : row) {
                if (num == 0) return true;
            }
        }
        return false;
    }

    private int[][] copyGrid() {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    private boolean gridsEqual(int[][] a, int[][] b) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

    public int getSize() {
        return size;
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
