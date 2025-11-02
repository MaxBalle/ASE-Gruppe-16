package Blatt01.Aufgabe02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BowlingGame
 *
 * Group 16:
 * @author Max Balle (3592845) - st178636@stud.uni-stuttgart.de
 * @author Annalena Schmid (3530959) - st173100@stud.uni-stuttgart.de
 * @author Marie Kufner (3535116) - st173710@stud.uni-stuttgart.de
 * @author Moritz Neubert (3860551) - st199704@stud.uni-stuttgart.de
 * @author Anh Quan Le (3593420) - st177186@stud.uni-stuttgart.de
 * @author Elena Restivo (3594296) - st177463@stud.uni-stuttgart.de
 * @author Martin Huynh (3532274) - st173632@stud.uni-stuttgart.de
 * @author Luca Hirt (3591972) - st178216@stud.uni-stuttgart.de
 * @author Trang Hua (3259405) - st149076@stud.uni-stuttgart.de
 * @author Christoph Alber (3824940) - st196946@stud.uni-stuttgart.de
 */
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
