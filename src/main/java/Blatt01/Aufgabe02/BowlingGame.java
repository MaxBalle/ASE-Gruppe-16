package Blatt01.Aufgabe02;

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
     * @throws IllegalStateException if it gets called when the game is already over
     * @throws IllegalArgumentException if called with an illegal pin count
     *
     * @see #isOver()
     */
    public void roll(final int pins) throws IllegalArgumentException, IllegalStateException {
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("Pins must be between 0 and 10");
        }

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

    /**
     * This method calculates the score of this game. 
     *
     * @return the score of the game
     */
    public int score() {
        int score = 0;
        Frame currentFrame = frames.get(currentFrameNumber);
        int lastCompleteRegularFrameNumber = currentFrameNumber;
        if (!currentFrame.isComplete() || lastCompleteRegularFrameNumber == 10) {
            lastCompleteRegularFrameNumber--;
        }
        for (int frameNumber = 1; frameNumber <= lastCompleteRegularFrameNumber; frameNumber++) {
            Frame frame = frames.get(frameNumber);
            score += frame.getFirstScore();
            if (frame.isStrike()) {
                score += getStrikeBonus(frame);
            } else {
                score += frame.getSecondScore();
                if (frame.isSpare()) {
                    score += getSpareBonus(frame);
                }
            }
        }
        if (!currentFrame.isComplete() && currentFrameNumber != 10) {
            if (currentFrame.hasFirstScore()) {
                score += currentFrame.getFirstScore();
            }
        }
        if (currentFrameNumber == 10) {
            Frame finalFrame = frames.get(currentFrameNumber);
            if (finalFrame.hasFirstScore()) {
                score += finalFrame.getFirstScore();
            }
            if (finalFrame.hasSecondScore()) {
                score += finalFrame.getSecondScore();
            }
            if ((finalFrame.isStrike() || finalFrame.isSpare()) && finalFrame.hasThirdScore()) {
                score += finalFrame.getThirdScore();
            }
        }
        return score;
    }

    private int getStrikeBonus(Frame frame) {
        int bonus = 0;
        int frameNumber = frame.getFrameNumber();
        if (frames.containsKey(frameNumber + 1)) {
            Frame nextFrame = frames.get(frameNumber + 1);
            if (nextFrame.hasFirstScore()) {
                bonus += nextFrame.getFirstScore();
            }
            if (nextFrame.hasSecondScore()) {
                bonus += nextFrame.getSecondScore();
            } else if (frames.containsKey(frameNumber + 2)) {
                Frame secondNextFrame = frames.get(frameNumber + 2);
                if (secondNextFrame.hasFirstScore()) {
                    bonus += secondNextFrame.getFirstScore();
                }
            }
        }
        return bonus;
    }

    private int getSpareBonus(Frame frame) {
        int bonus = 0;
        int frameNumber = frame.getFrameNumber();
        if (frames.containsKey(frameNumber + 1)) {
            Frame nextFrame = frames.get(frameNumber + 1);
            if (nextFrame.hasFirstScore()) {
                bonus += nextFrame.getFirstScore();
            }
        }
        return bonus;
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