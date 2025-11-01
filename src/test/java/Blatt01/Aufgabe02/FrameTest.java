package Blatt01.Aufgabe02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FrameTest {

    @Test
    void testEmptyFrame() {
        Frame frame = new Frame(1);
        assertEquals(1, frame.getFrameNumber());
        assertFalse(frame.hasFirstScore());
        assertFalse(frame.hasSecondScore());
        assertFalse(frame.hasThirdScore());
    }
}
