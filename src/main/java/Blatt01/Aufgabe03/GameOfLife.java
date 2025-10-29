package Blatt01.Aufgabe03;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for Conway's Game of Life.
 */

public class GameOfLife {

    private static int generation;

    /**
     * Creates a grid of given size, initializing all cells as dead.
     *
     * @param length The length of the grid
     * @param height The height of the grid
     * @return The created grid
     * @throws NegativeArraySizeException if length or height is negative
     * @throws IllegalArgumentException   if length or height is zero
     */
    public static boolean[][] createGrid(final int length, final int height) {
        generation = 1;
        boolean[][] grid;

        if (length == 0 || height == 0) {
            throw new IllegalArgumentException("Error: Grid dimensions must be greater than zero.");
        }

        try {
            grid = new boolean[length][height];
        } catch (NegativeArraySizeException e) {
            System.err.println("Error: Grid dimensions must be non-negative.");
            throw e;
        }
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = false;
            }
        }
        return grid;
    }

    /**
     * Prints the current state of the grid to the console.
     *
     * @param grid The grid to print
     */
    public static void printGrid(final boolean[][] grid) {
        System.out.println();
        System.out.println("Generation: " + generation);
        System.out.println("X = ALIVE | █ = DEAD");
        System.out.println();
        for (boolean[] cell : grid) {
            for (boolean b : cell) {
                if (b) {
                    System.out.print("X ");
                } else {
                    System.out.print("█ ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Sets the cell at the given point to alive.
     *
     * @param point The point of the cell to set alive
     * @param grid  The grid containing the cell
     * @throws IndexOutOfBoundsException if the point is out of grid bounds
     */
    public static void setAlive(final Point point, final boolean[][] grid) {
        if(!isPartOfGrid(grid, point)) {
            throw new IndexOutOfBoundsException("Point " + point + " is out of grid bounds.");
        }
        grid[point.x][point.y] = true;
    }

    /**
     * Sets the cell at the given point to dead.
     *
     * @param point The point of the cell to set dead
     * @param grid  The grid containing the cell
     * @throws IndexOutOfBoundsException if the point is out of grid bounds
     */
    public static void setDead(final Point point, final boolean[][] grid) {
        if(!isPartOfGrid(grid, point)) {
            throw new IndexOutOfBoundsException("Point " + point + " is out of grid bounds.");
        }
        grid[point.x][point.y] = false;
    }

    /**
     * Checks if the cell at the given point is alive.
     *
     * @param point The point of the cell to check
     * @param grid  The grid containing the cell
     * @return True if the cell is alive, false otherwise
     */
    public static boolean isAlive(final Point point, final boolean[][] grid) {
        boolean partOfGrid = isPartOfGrid(grid, point);
        if (!partOfGrid) {
            return false;
        } else {
            return grid[point.x][point.y];
        }
    }

    /**
     * Advances the grid to the next generation based on the rules of Conway's Game of Life.
     *
     * @param grid The current grid
     * @return The grid for the next generation
     */
    public static boolean[][] step(final boolean[][] grid) {
        generation++;
        int length = grid.length;
        int height = grid[0].length;
        boolean[][] newGrid = new boolean[length][height];

        for (int x = 0; x < length; x++) {
            System.arraycopy(grid[x], 0, newGrid[x], 0, height);
        }
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                Point currentCell = new Point(x, y);
                List<Point> currentNeighbours = getNeighbours(currentCell);
                if (isAliveInNextGeneration(grid, currentCell, currentNeighbours)) {
                    setAlive(currentCell, newGrid);
                } else {
                    setDead(currentCell, newGrid);
                }
            }
        }
        return newGrid;
    }

    /**
     * Gets the neighbouring cells of a given cell.
     *
     * @param point The point of the cell
     * @return A list of points representing the neighbouring cells
     */
    public static List<Point> getNeighbours(final Point point) {
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
     * @param grid  The grid
     * @param point The point to check
     * @return True if the point is within the grid, false otherwise
     */
    public static boolean isPartOfGrid(final boolean[][] grid, final Point point) {
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
     * @param grid       The current grid
     * @param cell       The cell to evaluate
     * @param neighbours The list of neighbouring cells
     * @return True if the cell will be alive in the next generation, false otherwise
     */
    public static boolean isAliveInNextGeneration(boolean[][] grid, Point cell, List<Point> neighbours) {
        int neighboursAlive = getNumberNeighboursAlive(grid, neighbours);
        if (GameOfLife.isAlive(cell, grid)) {
            return neighboursAlive == 2 || neighboursAlive == 3;
        } else {
            return neighboursAlive == 3;
        }
    }

    /**
     * Counts the number of alive neighbours for a given cell.
     *
     * @param grid       The current grid
     * @param neighbours The list of neighbouring cells
     * @return The number of alive neighbours
     */
    public static int getNumberNeighboursAlive(boolean[][] grid, List<Point> neighbours) {
        int neighboursAlive = 0;
        for (Point neighbour : neighbours) {
            if (GameOfLife.isAlive(neighbour, grid)) {
                neighboursAlive++;
            }
        }
        return neighboursAlive;
    }

    /**
     * Gets the current generation of the grid.
     *
     * @return The current generation
     */
    public static int getGeneration() {
        return generation;
    }
}
