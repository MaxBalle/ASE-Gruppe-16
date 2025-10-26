package test;

import main.GameOfLife;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {

    /**
     * Test creating a grid with valid dimensions.
     */
    @Test
    public void testCreateGrid() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        for (boolean[] row : grid) {
            for (boolean cell : row) {
                assertFalse(cell);
            }
        }
    }

    /**
     * Test creating a grid with negative dimensions.
     */
    @Test
    public void testCreateGridNegativeSize() {
        assertThrows(NegativeArraySizeException.class, () -> {
            GameOfLife.createGrid(-1, -1);
        });}

    /**
     * Test creating a grid with zero dimensions.
     */
    @Test
    public void testCreateGridZeroSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            GameOfLife.createGrid(0, 0);
        });}

    /**
     * Test setting a cell to alive.
     */
    @Test
    public void testSetCellAlive() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        Point p = new Point(1, 1);
        GameOfLife.setAlive(p, grid);
        assertTrue(grid[1][1]);
    }

    /**
     * Test setting a cell to alive that is out of bounds.
     */
    @Test
    public void testSetCellAliveOutOfBounds() {
        boolean[][] grid = GameOfLife.createGrid(1, 1);
        Point p = new Point(3, 3);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            GameOfLife.setAlive(p, grid);
        });
    }

    /**
     * Test setting a cell to dead.
     */
    @Test
    public void testSetCellDead() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        Point p = new Point(1, 1);
        GameOfLife.setDead(p, grid);
        assertFalse(grid[1][1]);
    }

    /**
     * Test setting a cell to dead that is out of bounds.
     */
    @Test
    public void testSetCellDeadOutOfBounds() {
        boolean[][] grid = GameOfLife.createGrid(1, 1);
        Point p = new Point(3, 3);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            GameOfLife.setDead(p, grid);
        });
    }

    /**
     * Test checking if a cell is alive.
     */
    @Test
    public void testIsAlive() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        Point p = new Point(1, 1);
        GameOfLife.setAlive(p, grid);
        assertTrue(GameOfLife.isAlive(p, grid));
    }

    /**
     * Test getting all neighbors of a cell.
     */
    @Test
    public void testGetNeighbors() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        Point p = new Point(1, 1);
        List<Point> neighbors = GameOfLife.getNeighbours(p);
        List<Point> actualNeighbors = List.of(
                new Point(0, 2), new Point(1, 2), new Point(2, 2),
                new Point(0, 1),                        new Point(2, 1),
                new Point(0, 0), new Point(1, 0), new Point(2, 0)
        );
        assertArrayEquals(neighbors.toArray(), actualNeighbors.toArray());
    }

    /**
     * Test checking if a point is part of the grid.
     */
    @Test
    public void testIsPartOfGrid() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        Point insidePoint = new Point(1, 1);
        Point outsidePoint = new Point(5, 5);
        assertTrue(GameOfLife.isPartOfGrid(grid, insidePoint));
        assertFalse(GameOfLife.isPartOfGrid(grid, outsidePoint));
    }

    /**
     * Test the underpopulation rule: Any live cell with fewer than two live neighbours dies. This rule is checked
     * during the step() function.
     */
    @Test
    public void testUnderpopulationRule() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        GameOfLife.setAlive(new Point(1, 1), grid);
        boolean[][] newGrid = GameOfLife.step(grid);
        assertFalse(GameOfLife.isAlive(new Point(1, 1), newGrid));
    }

    /**
     * Test the overpopulation rule: Any live cell with more than three live neighbours dies. This rule is checked
     * during the step() function.
     */
    @Test
    public void testOverpopulationRule() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);

        GameOfLife.setAlive(new Point(1, 1), grid);

        GameOfLife.setAlive(new Point(0, 0), grid);
        GameOfLife.setAlive(new Point(0, 1), grid);
        GameOfLife.setAlive(new Point(0, 2), grid);
        GameOfLife.setAlive(new Point(1, 0), grid);

        boolean[][] newGrid = GameOfLife.step(grid);
        assertFalse(GameOfLife.isAlive(new Point(1, 1), newGrid));
    }

    /**
     * Test the stability rule: Any live cell with two or three live neighbours lives on to the next generation. This rule is checked
     * during the step() function.
     */
    @Test
    public void testStabilityRule() {
        boolean[][] grid = GameOfLife.createGrid(4, 4);

        GameOfLife.setAlive(new Point(1, 1), grid);
        GameOfLife.setAlive(new Point(1, 2), grid);
        GameOfLife.setAlive(new Point(2, 1), grid);
        GameOfLife.setAlive(new Point(2, 2), grid);

        boolean[][] newGrid = GameOfLife.step(grid);

        assertTrue(GameOfLife.isAlive(new Point(1, 1), newGrid));
        assertTrue(GameOfLife.isAlive(new Point(1, 2), newGrid));
        assertTrue(GameOfLife.isAlive(new Point(2, 1), newGrid));
        assertTrue(GameOfLife.isAlive(new Point(2, 2), newGrid));
    }

    /**
     * Test the birth rule: Any dead cell with exactly three live neighbours becomes a live cell. This rule is checked
     * during the step() function.
     */
    @Test
    public void testBirthRule() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);

        GameOfLife.setAlive(new Point(0, 1), grid);
        GameOfLife.setAlive(new Point(1, 0), grid);
        GameOfLife.setAlive(new Point(1, 2), grid);

        boolean[][] newGrid = GameOfLife.step(grid);
        assertTrue(GameOfLife.isAlive(new Point(1, 1), newGrid));
    }

    /**
     * Test getting the number of alive neighbours of a cell.
     */
    @Test
    public void testGetNumberNeighboursAlive() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);

        GameOfLife.setAlive(new Point(0, 1), grid);
        GameOfLife.setAlive(new Point(1, 0), grid);
        GameOfLife.setAlive(new Point(1, 2), grid);

        int aliveNeighbors = GameOfLife.getNumberNeighboursAlive(grid, GameOfLife.getNeighbours(new Point(1, 1)));
        assertEquals(3, aliveNeighbors);
    }

    /**
     * Test getting the current generation.
     */
    @Test
    public void getGeneration(){
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        GameOfLife.step(grid);
        GameOfLife.step(grid);
        assertEquals(3, GameOfLife.getGeneration());
    }
}

