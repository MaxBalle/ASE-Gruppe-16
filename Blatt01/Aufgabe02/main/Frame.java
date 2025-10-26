import java.util.Optional;

public class Frame {
    
    private int frameNumber;
    private Optional<Integer> firstScore;
    private Optional<Integer> secondScore;
    private boolean spare;
    private boolean strike;

    public Frame(int frameNumber) {

        this.frameNumber = frameNumber;
        this.firstScore = Optional.empty();
        this.secondScore = Optional.empty();
        this.spare = false;
        this.strike = false;
    }

    public void addScore(int pins) {

        if (firstScore.isEmpty()) {
            this.firstScore = Optional.of(pins);
            if (pins == 10) {
                this.strike = true;
            }
        }
        else if (secondScore.isPresent()) {
            throw new Error("Frame scores already existing!");
        }
        else {
            this.secondScore = Optional.of(pins);

            if (firstScore.get() + secondScore.get() == 10) {
                this.spare = true;
            } 
        }
    }

    public int getFirstScore() {

        return firstScore.get();
    }

    public int getSecondScore() {

        return secondScore.get();
    }

    public boolean getSpare() {

        return spare;
    }

    public boolean getStrike() {

        return strike;
    }

    public void setFrameNumber(int number) {
    
        this.frameNumber = number;
    }

    public int getFrameNumber() {

        return frameNumber;
    }

}
