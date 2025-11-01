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
    private final int length;
    private final int height;

    /**
     * Constructor to initialize the Game of Life grid with given dimensions.
     *
     * @param length The length of the grid
     * @param height The height of the grid
     */
    public GameOfLife(int length, int height) {
        this.generation = 1;
        this.grid = createGridWithDeadCells(length, height);
        this.length = length;
        this.height = height;
    }

    /**
     * Creates a grid of given size, initializing all cells as dead. This method is only used in the constructor and
     * therefore private.
     *
     * @param length The length of the grid
     * @param height The height of the grid
     * @return The created grid
     * @throws NegativeArraySizeException if length or height is negative
     * @throws IllegalArgumentException   if length or height is zero
     *
     * ## AI Generated Documentation (AutoComplete, GitHub Copilot) ##
     */
    private boolean[][] createGridWithDeadCells(final int length, final int height) {

        boolean[][] newGrid;

        if (length == 0 || height == 0) {
            throw new IllegalArgumentException("Error: Grid dimensions must be greater than zero.");
        }

        try {
            newGrid = new boolean[length][height];
        } catch (NegativeArraySizeException e) {
            throw new NegativeArraySizeException("Error: Grid dimensions must be non-negative.");
        }
        for (int x = 0; x < length; x++) {
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
     * Sets the cell at the given point to alive.
     *
     * @param point The coordinates of the cell to set alive
     * @throws IndexOutOfBoundsException if the point is out of grid bounds
     */
    public void setAlive(final Point point) {
        if(!isPartOfGrid(point)) {
            throw new IndexOutOfBoundsException("Point " + point + " is out of grid bounds.");
        }
        grid[point.x][point.y] = true;
    }

    /**
     * Sets the cell at the given point to dead.
     *
     * @param point The coordinates of the cell to set dead
     * @throws IndexOutOfBoundsException if the point is out of grid bounds
     */
    public void setDead(final Point point) {
        if(!isPartOfGrid(point)) {
            throw new IndexOutOfBoundsException("Point " + point + " is out of grid bounds.");
        }
        grid[point.x][point.y] = false;
    }

    /**
     * Checks if the cell at the given point is alive.
     *
     * @param point The point of the cell to check
     * @return True if the cell is alive, false otherwise
     */
    public boolean isAlive(final Point point) {
        boolean partOfGrid = isPartOfGrid(point);
        if (!partOfGrid) {
            return false;
        } else {
            return grid[point.x][point.y];
        }
    }

    /**
     * Advances the grid to the next generation based on the rules of Conway's Game of Life.
     **/
    public void step() {
        generation++;
        int length = this.length;
        int height = this.height;
        boolean[][] newGrid = new boolean[length][height];

        // Copy current grid to new grid
        for (int x = 0; x < length; x++) {
            System.arraycopy(grid[x], 0, newGrid[x], 0, height);
        }

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                Point currentCell = new Point(x, y);
                List<Point> currentNeighbours = getNeighbours(currentCell);
                newGrid[currentCell.x][currentCell.y] = isAliveInNextGeneration(currentCell, currentNeighbours);
            }
        }
        grid = newGrid;
    }

    /**
     * Gets the neighbouring cells of a given cell.
     *
     * @param point The point of the cell
     * @return A list of points representing the neighbouring cells
     */
    public List<Point> getNeighbours(final Point point) {
        List<Point> neighbours = new ArrayList<>();
        int x = point.x;
        int y = point.y;

        neighbours.add(new Point(x - 1, y + 1));
        neighbours.add(new Point(x, y + 1));
        neighbours.add(new Point(x + 1, y + 1));

        neighbours.add(new Point(x - 1, y));
        neighbours.add(new Point(x + 1, y));

        neighbours.add(new Point(x - 1, y - 1));
        neighbours.add(new Point(x, y - 1));
        neighbours.add(new Point(x + 1, y - 1));

        return neighbours;
    }

    /**
     * Checks if a point is within the bounds of the grid.
     *
     * @param point The point to check
     * @return True if the point is within the grid, false otherwise
     */
    public boolean isPartOfGrid(final Point point) {
        return !(point.x < 0 || point.x > grid.length - 1 || point.y > grid[0].length - 1 || point.y < 0);
    }

    /**
     * Determines if a cell will be alive in the next generation based on its current state and its neighbours.
     * Rules:
     * 1. Underpopulation: Any live cell with fewer than two live neighbours dies.
     * 2. Overpopulation: Any live cell with more than three live neighbours dies.
     * 3. Stability: Any live cell with two or three live neighbours lives on to the next generation.
     * 4. Birth: Any dead cell with exactly three live neighbours becomes a live cell.
     *
     * @param cell       The cell to evaluate
     * @param neighbours The list of neighbouring cells
     * @return True if the cell will be alive in the next generation, false otherwise
     *
     * ## AI Generated Documentation (AutoComplete, GitHub Copilot) ##
     */
    public boolean isAliveInNextGeneration(Point cell, List<Point> neighbours) {
        int neighboursAlive = getNumberNeighboursAlive(neighbours);
        if (isAlive(cell)) {
            return neighboursAlive == 2 || neighboursAlive == 3;
        } else {
            return neighboursAlive == 3;
        }
    }

    /**
     * Counts the number of alive neighbours for a given cell.
     *
     * @param neighbours The list of neighbouring cells
     * @return The number of alive neighbours
     */
    public int getNumberNeighboursAlive(List<Point> neighbours) {
        int neighboursAlive = 0;
        for (Point neighbour : neighbours) {
            if (isAlive(neighbour)) {
                neighboursAlive++;
            }
        }
        return neighboursAlive;
    }

    public int getGeneration() {return generation;}

    public int getLength() {return length;}

    public int getHeight() {return height;}

    public boolean[][] getGrid() {return grid;}
}
