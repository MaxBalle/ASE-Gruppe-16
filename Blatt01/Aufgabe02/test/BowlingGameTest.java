import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BowlingGameTest {

    @Test
    void testStandardGame() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(3);
        assertEquals(8, game.score());
    }
}
