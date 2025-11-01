package Blatt01.Aufgabe02;

import java.util.Optional;

public class Frame {
    
    private final int frameNumber;
    private Optional<Integer> firstScore;
    private Optional<Integer> secondScore;
    private Optional<Integer> thirdScore;

    public Frame(final int frameNumber) {
        if (frameNumber < 1 || frameNumber > 10) {
            throw new IllegalArgumentException("Frame number must be between 1 and 10");
        }
        this.frameNumber = frameNumber;
        this.firstScore = Optional.empty();
        this.secondScore = Optional.empty();
        this.thirdScore = Optional.empty();
    }

    /**
     * addScore is called to add a score of a throw to the frame
     *
     *
     * @param pins The number of pins knocked over this throw
     * @throws IllegalStateException if it gets called even though the frame is complete
     * @throws IllegalArgumentException if you want to add more points than possible to the current frame
     */
    public void addScore(final int pins) throws IllegalStateException, IllegalArgumentException {
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("Pins must be between 0 and 10");
        }
        if (isComplete()) throw new IllegalStateException("Frame is already complete!");

        IllegalArgumentException tooManyPinsException = new IllegalArgumentException("Too many pins for this frame");
        if (getFrameNumber() < 10) {
            if (hasFirstScore() && getFirstScore() + pins > 10) {
                throw tooManyPinsException;
            }
        } else if (getFrameNumber() == 10) {
            int limit = 10;
            if (hasFirstScore()) {
                limit -= getFirstScore();
                if (isStrike()) {
                    limit = 20;
                }
                if (!hasSecondScore() && pins > limit) {
                    throw tooManyPinsException;
                }
            }
            if (hasSecondScore()) {
                limit -= getSecondScore();
                if (isSpare()) {
                    limit = 10;
                }
                if (pins > limit) {
                    throw tooManyPinsException;
                }
            }
        }

        if (firstScore.isEmpty()) {
            this.firstScore = Optional.of(pins);
        } else if (secondScore.isEmpty()) {
            this.secondScore = Optional.of(pins);
        } else if (thirdScore.isEmpty()) {
            this.thirdScore = Optional.of(pins);
        }
    }

    public boolean hasFirstScore() {
        return firstScore.isPresent();
    }

    public int getFirstScore() {
        return firstScore.get();
    }

    public boolean hasSecondScore() {
        return secondScore.isPresent();
    }

    public int getSecondScore() {
        return secondScore.get();
    }

    public boolean hasThirdScore() {
        return thirdScore.isPresent();
    }

    public int getThirdScore() {
        return thirdScore.get();
    }

    public boolean isSpare() {
        if (!hasFirstScore() || !hasSecondScore() || isStrike()) {
            return false;
        }
        return getFirstScore() + getSecondScore() == 10;
    }

    public boolean isStrike() {
        if (!hasFirstScore()) {
            return false;
        }
        return getFirstScore() == 10;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public boolean isComplete() {
        if (firstScore.isEmpty()) {
            return false;
        } else if (secondScore.isEmpty() && !isStrike()) {
            return false;
        } else if (getFrameNumber() == 10 && (isStrike() || isSpare())  && thirdScore.isEmpty()) {
            return false;
        }
        return true;
    }
}