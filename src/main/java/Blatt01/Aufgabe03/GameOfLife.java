package Blatt01.Aufgabe03;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for Conway's Game of Life.
 */

public class GameOfLife {

    private int generation;
    private boolean[][] grid;
    private final int width;
    private final int height;

    /**
     * Constructor to initialize the Game of Life grid with given dimensions. The initial grid contains only dead cells.
     * The starting generation is set to 1. Cells can be set to alive using the setAlive method.
     *
     * @param width The width of the grid
     * @param height The height of the grid
     * @throws IllegalArgumentException if width or height is less than zero
     */
    public GameOfLife(final int width, final int height) {
        this.generation = 1;
        this.grid = createGridWithDeadCells(width, height);
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a grid of given size, initializing all cells as dead. This method is only used in the constructor and
     * therefore private.
     *
     * @param width The width of the grid
     * @param height The height of the grid
     * @return The created grid
     * @throws IllegalArgumentException if width or height is less than zero
     *
     * ## AI Generated Documentation (AutoComplete, GitHub Copilot) ##
     */
    private boolean[][] createGridWithDeadCells(final int width, final int height) {

        boolean[][] newGrid;

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Error: Grid dimensions must be greater than zero.");
        }
        newGrid = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newGrid[x][y] = false;
            }
        }
        return newGrid;
    }


    /**
     * Prints the current state of the grid to the console.
     */
    public void printGrid() {
        System.out.println();
        System.out.println("Generation: " + generation);
        System.out.println("A = ALIVE | D = DEAD");
        System.out.println();
        for (boolean[] cell : grid) {
            for (boolean b : cell) {
                if (b) {
                    System.out.print("A ");
                } else {
                    System.out.print("D ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Sets the cell at the given cell to alive.
     *
     * @param x The x-coordinate of the cell to set alive
     * @param y The y-coordinate of the cell to set alive
     * @throws IllegalArgumentException if the cell is out of grid bounds
     */
    public void setAlive(final int x, final int y) {
        if(!isPartOfGrid(x, y)) {
            throw new IllegalArgumentException("Coordinates are out of grid bounds.");
        }
        grid[x][y] = true;
    }

    /**
     * Sets the cell at given coordinates to dead.
     *
     * @param x The x-coordinate of the cell to set dead
     * @param y The y-coordinate of the cell to set dead
     * @throws IllegalArgumentException if the cell is out of grid bounds
     */
    public void setDead(final int x, final int y) {
        if(!isPartOfGrid(x, y)) {
            throw new IllegalArgumentException("Coordinates are out of grid bounds.");
        }
        grid[x][y] = false;
    }

    /**
     * Checks if the cell at the given coordinates is alive. If the cell is out of bounds, it returns
     * false (edge behavior).
     *
     * @param x The x-coordinate of the cell to check
     * @param y The y-coordinate of the cell to check
     * @return True if the cell is alive, false otherwise
     */
    public boolean isAlive(final int x, final int y) {
        boolean partOfGrid = isPartOfGrid(x, y);
        if (!partOfGrid) {
            return false;
        } else {
            return grid[x][y];
        }
    }

    /**
     * Advances the grid to the next generation based on the rules of Conway's Game of Life.
     **/
    public void step() {
        int width = this.width;
        int height = this.height;
        boolean[][] newGrid = cloneGrid();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newGrid[x][y] = isAliveInNextGeneration(x, y);
            }
        }
        grid = newGrid;
        generation++;
    }

    /**
     * Gets the neighbouring cells of a given cell and ensures they are part of the grid. This method provides a pre
     * step for counting alive neighbours and is private as it is only used internally.
     *
     * @param x The x-coordinate of the cell
     * @param y The y-coordinate of the cell
     * @return A list of points representing the neighbouring cells
     *
     * ## AI Generated Documentation (AutoComplete, GitHub Copilot) ##
     */
    private List<Point> getNeighbours(final int x, final int y) {
        List<Point> neighbours = new ArrayList<>();

        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetY = -1; offsetY <= 1; offsetY++) {
                Point currentNeighbour = new Point(x + offsetX, y + offsetY);
                // Exclude the cell itself and ensure the neighbour is part of the grid
                if ((offsetX != 0 || offsetY != 0) && isPartOfGrid(currentNeighbour.x, currentNeighbour.y)) {
                    neighbours.add(currentNeighbour);
                }
            }
        }

        return neighbours;
    }

    /**
     * Checks if a cell is within the bounds of the grid.
     *
     * @param x The x-coordinate of the cell
     * @param y The y-coordinate of the cell
     * @return True if the cell is within the grid, false otherwise
     *
     * ## AI Generated Documentation (AutoComplete, GitHub Copilot) ##
     */
    public boolean isPartOfGrid(final int x, final int y) {
        return !(x < 0 || x > grid.length - 1 || y > grid[0].length - 1 || y < 0);
    }

    /**
     * Determines if a cell on the grid will be alive in the next generation based on its current state and
     * its neighbours.
     *
     * Rules:
     * 1. Underpopulation: Any live cell with fewer than two live neighbours dies.
     * 2. Overpopulation: Any live cell with more than three live neighbours dies.
     * 3. Stability: Any live cell with two or three live neighbours lives on to the next generation.
     * 4. Birth: Any dead cell with exactly three live neighbours becomes a live cell.
     *
     * @param x The x-coordinate of the cell
     * @param y The y-coordinate of the cell
     * @return True if the cell will be alive in the next generation, false otherwise
     * @throws IllegalArgumentException if the cell is out of grid bounds
     *
     * ## AI Generated Documentation (AutoComplete, GitHub Copilot) ##
     */
    public boolean isAliveInNextGeneration(final int x, final int y) {
        if (!isPartOfGrid(x, y)) {
            throw new IllegalArgumentException("Coordinates are out of grid bounds.");
        }
        int neighboursAlive = countNeighboursAlive(x, y);
        if (isAlive(x, y)) {
            return neighboursAlive == 2 || neighboursAlive == 3;
        } else {
            return neighboursAlive == 3;
        }
    }

    /**
     * Counts the number of alive neighbours for a given cell of the grid.
     *
     * @param x The x-coordinate of the cell
     * @param y The y-coordinate of the cell
     * @return The number of alive neighbours
     * @throws IllegalArgumentException if the cell is out of grid bounds
     *
     * ## AI Generated Documentation (AutoComplete, GitHub Copilot) ##
     */
    public int countNeighboursAlive(final int x, final int y) {
        if (!isPartOfGrid(x, y)) {
            throw new IllegalArgumentException("Coordinates are out of grid bounds.");
        }
        List<Point> neighbours = getNeighbours(x, y);
        int neighboursAlive = 0;
        for (Point neighbour : neighbours) {
            if (isAlive(neighbour.x, neighbour.y)) {
                neighboursAlive++;
            }
        }
        return neighboursAlive;
    }

    public int getGeneration() {return generation;}

    public int getWidth() {return width;}

    public int getHeight() {return height;}

    public boolean[][] getGrid() {return cloneGrid();}


    /**
     * Creates a deep-copy of the current grid to prevent external modification.
     *
     * @return A cloned copy of the grid
     */
    private boolean[][] cloneGrid() {
        boolean[][] clone = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            System.arraycopy(grid[x], 0, clone[x], 0, height);
        }
        return clone;
    }
}
