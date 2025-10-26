package test;

import main.GameOfLife;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {

    /**
     * Test creating a grid with valid dimensions.
     */
    @Test
    void testCreateGrid() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        for (boolean[] row : grid) {
            for (boolean cell : row) {
                assertFalse(cell);
            }
        }
    }

    @Test
    void testCreateGridNegativeSize() {
        assertThrows(NegativeArraySizeException.class, () -> {
            GameOfLife.createGrid(-1, -1);
        });}

    @Test
    void testCreateGridZeroSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            GameOfLife.createGrid(0, 0);
        });}


    @Test
    void testSetCellAlive() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        Point p = new Point(1, 1);
        GameOfLife.setAlive(p, grid);
        assertTrue(grid[1][1]);
    }

    @Test
    void testSetCellAliveOutOfBounds() {
        boolean[][] grid = GameOfLife.createGrid(1, 1);
        Point p = new Point(3, 3);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            GameOfLife.setAlive(p, grid);
        });
    }

    @Test
    void testSetCellDead() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        Point p = new Point(1, 1);
        GameOfLife.setDead(p, grid);
        assertFalse(grid[1][1]);
    }

    @Test
    void testSetCellDeadOutOfBounds() {
        boolean[][] grid = GameOfLife.createGrid(1, 1);
        Point p = new Point(3, 3);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            GameOfLife.setDead(p, grid);
        });
    }

    @Test
    void testIsAlive() {
        boolean[][] grid = GameOfLife.createGrid(3, 3);
        Point p = new Point(1, 1);
        GameOfLife.setAlive(p, grid);
        assertTrue(GameOfLife.isAlive(p, grid));
    }
}

