import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BowlingGame {

    private Map<Integer, List<Integer>> frames;
    private int score;

    public BowlingGame() {

        frames = new HashMap<Integer, List<Integer>>();
        for (int i = 1; i<=10; i++) {
            frames.put(i, new ArrayList<Integer>());
        }

        score = 0;
    }


    public void roll(int pins) {
        //TODO
    }

    public int score() {
        return score;
    }
}