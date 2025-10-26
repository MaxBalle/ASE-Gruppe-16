import java.util.Map;
import java.util.HashMap;

public class BowlingGame {

    private final Map<Integer, Frame> frames = new HashMap<Integer, Frame>();
    private boolean gameOver = false;
    private Integer currentFrameNumber = 1;

    public BowlingGame() {

        for (int i = 1; i<=10; i++) {
            frames.put(i, new Frame(i));
        }
    }

    /**
     * This method receives how many pins were knocked over and then stores
     * them in this game.
     *
     * @param pins The number of pins knocked over this throw
     *
     * @throws IllegalStateException if called when the game is already over
     *
     * @see #isOver()
     */
    public void roll(int pins) {
        if (gameOver) {
            throw new IllegalStateException("Game over");
        }

        Frame currentFrame = frames.get(currentFrameNumber);
        currentFrame.addScore(pins);

        if (currentFrame.isComplete()) {
            if (currentFrameNumber < 10) {
                currentFrameNumber++;
            } else {
                gameOver = true;
            }
        }
    }

    public int score() {
        int score = 0;
        Frame currentFrame = frames.get(currentFrameNumber);
        int lastCompleteFrameNumber = currentFrameNumber;
        if (!currentFrame.isComplete() || lastCompleteFrameNumber == 10) {
            lastCompleteFrameNumber--;
        }
        for (int frameNumber = 1; frameNumber < lastCompleteFrameNumber; frameNumber++) {
            Frame frame = frames.get(frameNumber);
            //TODO counting alg
        }

        return score;
    }

    /**
     * If all ten frames are played then the game is over.
     *
     * @return whether this game is over
     */
    public boolean isOver() {
        return gameOver;
    }
}