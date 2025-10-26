import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import BowlingGame;
public class BowlingGameTest {

    @Test
    void testStandardGame() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(3);
        assertEquals(8, game.score());
    }

    @Test 
    void testSpare() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5);
        game.roll(5);
        game.roll(5);
        game.roll(5);
        assertEquals(30, game.score());
    }

    @Test 
    void testStrike() {
        BowlingGame game = new BowlingGame(); 
        game.roll(10);
        game.roll(5)
        assertEqual(20, game.score8))
    }
}
