import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class BowlingGameTest {

    @Test
    public void testStandardGame() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(3);
        assertEquals(8, game.score());
    }

    @Test 
    public void testSpare() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5);
        game.roll(5);
        game.roll(5);
        game.roll(5);
        assertEquals(30, game.score());
    }

    @Test 
    public void testStrike() {
        BowlingGame game = new BowlingGame(); 
        game.roll(10);
        game.roll(5);
        assertEquals(20, game.score());
    }

    @Test
    public void testSpareEnding() {
        BowlingGame game = new BowlingGame();
        for (int i = 1; i <= 9; i++) {
            game.roll(0);
            game.roll(0);
        }
        assertEquals(0, game.score());
        game.roll(10);
        game.roll(1);
        game.roll(1);
        assertEquals(12, game.score());
    }

    @Test
    public void testStrikeEnding() {
        BowlingGame game = new BowlingGame();
        for (int i = 1; i <= 9; i++) {
            game.roll(0);
            game.roll(0);
        }
        assertEquals(0, game.score());
        game.roll(5);
        game.roll(5);
        game.roll(1);
        assertEquals(11, game.score());
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
}
