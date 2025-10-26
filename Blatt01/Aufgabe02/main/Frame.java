import java.util.Optional;

public class Frame {
    
    private int frameNumber;
    private Optional<Integer> firstScore;
    private Optional<Integer> secondScore;
    private Optional<Integer> thirdScore;
    private boolean spare;
    private boolean strike;

    public Frame(int frameNumber) {

        this.frameNumber = frameNumber;
        this.firstScore = Optional.empty();
        this.secondScore = Optional.empty();
        this.thirdScore = Optional.empty();
        this.spare = false;
        this.strike = false;
    }

    public void addScore(int pins) {
        if (isComplete()) throw new Error("Frame is already complete!");

        if (firstScore.isEmpty()) {
            this.firstScore = Optional.of(pins);
            if (pins == 10) {
                this.strike = true;
            }
        } else if (secondScore.isEmpty()) {
            this.secondScore = Optional.of(pins);

            if (firstScore.get() + secondScore.get() == 10) {
                this.spare = true;
            }
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

        return spare;
    }

    public boolean isStrike() {

        return strike;
    }

    public void setFrameNumber(int number) {
    
        this.frameNumber = number;
    }

    public int getFrameNumber() {

        return frameNumber;
    }

    public boolean isComplete() {
        if (firstScore.isEmpty()) return false;
        if (secondScore.isEmpty() && !isStrike()) return false;
        if (getFrameNumber() == 10 && (isStrike() || isSpare())  && thirdScore.isEmpty()) return false;
        return true;
    }
}
