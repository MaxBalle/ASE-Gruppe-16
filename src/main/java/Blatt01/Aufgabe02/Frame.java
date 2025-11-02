package Blatt01.Aufgabe02;

import java.util.Optional;

/**
 * Represents one frame in a game of bowling
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

    /**
     * hasFirstScore returns a boolean telling if a first score of the frame is present or not
     *
     * @return true if first score of the frame if present, otherwise false
     */
    public boolean hasFirstScore() {
        return firstScore.isPresent();
    }

    /**
     * getFirstScore() returns the first score of the frame
     * call {@link #hasFirstScore()} bevor calling this method
     *
     * @return the first score of the frame
     *
     * @see #hasFirstScore()
     *
     * @throws java.util.NoSuchElementException if called without a value present
     */
    public int getFirstScore() {
        return firstScore.get();
    }

    /**
     * hasSecondScore() returns a boolean telling if a second score of the frame is present or not
     *
     * @return true if second score of the frame is present, otherwise false
     */
    public boolean hasSecondScore() {
        return secondScore.isPresent();
    }

    /**
     * getSecondScore() returns the second score of the frame
     * call {@link #hasSecondScore()} bevor calling this method
     *
     * @return the second score of the frame
     *
     * @see #hasSecondScore()
     *
     * @throws java.util.NoSuchElementException if called without a value present
     */
    public int getSecondScore() {
        return secondScore.get();
    }

    /**
     * hasThirdScore() returns a boolean telling if a third score of the frame exists or not
     *
     * @return true if a third score is present, otherwise false
     */
    public boolean hasThirdScore() {
        return thirdScore.isPresent();
    }

    /**
     * getThirdScore() returns the third score of the frame
     * call {@link #hasThirdScore()} bevor calling this method
     *
     * @return the third score of the frame
     *
     * @see #hasThirdScore()
     *
     * @throws java.util.NoSuchElementException if called without a value present
     */
    public int getThirdScore() {
        return thirdScore.get();
    }

    /**
     * isSpare() returns a boolean telling if a spare is present in the frame
     *
     * @return true if a spare is present in the frame, otherwise false
     */
    public boolean isSpare() {
        if (!hasFirstScore() || !hasSecondScore() || isStrike()) {
            return false;
        }
        return getFirstScore() + getSecondScore() == 10;
    }

    /**
     * isStrike() returns a boolean telling if a spare is present in the frame
     *
     * @return true if a strike is present, otherwise false
     */
    public boolean isStrike() {
        if (!hasFirstScore()) {
            return false;
        }
        return getFirstScore() == 10;
    }

    /**
     * getFrameNumber() returns the position of the frame as an integer inside a whole bowling game
     *
     * @return integer of the frame inside the game
     */
    public int getFrameNumber() {
        return frameNumber;
    }

    /**
     * isComplete returns a boolean telling if the frame is complete
     *
     * @return true if the frame is complete, otherwise false
     */
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