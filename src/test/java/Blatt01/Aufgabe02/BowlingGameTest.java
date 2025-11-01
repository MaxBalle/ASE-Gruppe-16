package Blatt01.Aufgabe02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BowlingGameTest {

    @Test
    public void testSingleFrame() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        assertEquals(5, game.score());
        game.roll(3);
        assertEquals(8, game.score());
    }

    @Test
    public void testFullNormalGame() {
        BowlingGame game = new BowlingGame();
        //Frame 1
        game.roll(5);
        game.roll(3);
        //Frame 2
        game.roll(1);
        game.roll(1);
        //Frame 3
        game.roll(0);
        game.roll(0);
        //Frame 4
        game.roll(3);
        game.roll(4);
        //Frame 5
        game.roll(9);
        game.roll(0);
        //Frame 6
        game.roll(2);
        game.roll(6);
        //Frame 7
        game.roll(3);
        game.roll(3);
        //Frame 8
        game.roll(5);
        game.roll(4);
        //Frame 9
        game.roll(1);
        game.roll(8);
        //Frame 10
        game.roll(7);
        game.roll(0);

        assertEquals(65, game.score());
        assertTrue(game.isOver());
    }

    @Test 
    public void testSpare() {
        BowlingGame game = new BowlingGame();
        //Frame 1 - Spare
        game.roll(5);
        game.roll(5);
        //Frame 2 - Incomplete
        game.roll(5);
        assertEquals(20, game.score());
    }

    @Test 
    public void testStrike() {
        BowlingGame game = new BowlingGame();
        //Frame 1 - Strike
        game.roll(10);
        //Frame 2
        game.roll(5);
        assertEquals(20, game.score());
        game.roll(1);
        assertEquals(22, game.score());
    }

    @Test
    public void testStrikeEnding() {
        BowlingGame game = new BowlingGame();
        //Frames 1 to 9 - All nothing
        for (int i = 1; i <= 9; i++) {
            game.roll(0);
            game.roll(0);
        }
        assertEquals(0, game.score());
        //Frame 10
        game.roll(10);
        game.roll(1);
        game.roll(1);
        assertEquals(12, game.score());
        assertTrue(game.isOver());
    }

    @Test
    public void testSpareEnding() {
        BowlingGame game = new BowlingGame();
        //Frames 1 to 9 - All nothing
        for (int i = 1; i <= 9; i++) {
            game.roll(0);
            game.roll(0);
        }
        assertEquals(0, game.score());
        //Frame 10
        game.roll(5);
        game.roll(5);
        game.roll(1);
        assertEquals(11, game.score());
        assertTrue(game.isOver());
    }

    @Test
    public void testPerfectGame() {
        BowlingGame game = new BowlingGame();
        for (int i = 1; i <= 12; i++) {
            game.roll(10);
        }
        assertEquals(300, game.score());
        assertTrue(game.isOver());
    }

    @Test
    public void testWorstGame() {
        BowlingGame game = new BowlingGame();
        for (int i = 1; i <= 20; i++) {
            game.roll(0);
        }
        assertEquals(0, game.score());
        assertTrue(game.isOver());
    }

    @Test
    public void test11FrameGame() {
        BowlingGame game = new BowlingGame();
        assertThrows(IllegalStateException.class, () -> {
            for (int i = 1; i <= 11; i++) {
                game.roll(0);
                game.roll(0);
            }
        });
    }

    @Test
    public void testIllegalScores() {
        BowlingGame game = new BowlingGame();
        assertThrows(IllegalArgumentException.class, () -> game.roll(-1));
        assertThrows(IllegalArgumentException.class, () -> game.roll(11));
    }

    @Test
    public void testTooManyPinsPerFrame() {
        BowlingGame game = new BowlingGame();
        game.roll(6);
        assertThrows(IllegalArgumentException.class, () -> game.roll(6));
    }
}
