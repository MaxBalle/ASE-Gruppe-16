package Blatt01.Aufgabe02;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FrameTest {

    @Test
    void testEmptyFrame() {
        Frame frame = new Frame(1);
        assertEquals(1, frame.getFrameNumber());
        assertFalse(frame.hasFirstScore());
        assertFalse(frame.hasSecondScore());
        assertFalse(frame.hasThirdScore());
    }

    @Test
    void testIllegalFrameNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Frame(-1));
        assertThrows(IllegalArgumentException.class, () -> new Frame(0));
        assertThrows(IllegalArgumentException.class, () -> new Frame(11));
    }

    @Test
    void testNormalFrame() {
        Frame frame = new Frame(1);

        frame.addScore(1);
        assertFalse(frame.isComplete());

        frame.addScore(2);
        assertTrue(frame.isComplete());

        assertTrue(frame.hasFirstScore());
        assertTrue(frame.hasSecondScore());
        assertFalse(frame.hasThirdScore());

        assertEquals(1, frame.getFirstScore());
        assertEquals(2, frame.getSecondScore());
        assertThrows(NoSuchElementException.class, frame::getThirdScore);

        assertFalse(frame.isSpare());
        assertFalse(frame.isStrike());
    }

    @Test
    void testAddScoreToCompleteFrame() {
        Frame frame = new Frame(1);
        frame.addScore(1);
        frame.addScore(2);
        assertThrows(IllegalStateException.class, () -> frame.addScore(3));
    }

    @Test
    void testAddIllegalScore() {
        Frame frame = new Frame(1);
        //To many per throw
        assertThrows(IllegalArgumentException.class, () -> frame.addScore(-1));
        assertThrows(IllegalArgumentException.class, () -> frame.addScore(11));
        //To many per frame
        frame.addScore(6);
        assertThrows(IllegalArgumentException.class, () -> frame.addScore(6));
    }

    @Test
    void testStrikeFrame() {
        Frame frame = new Frame(1);
        frame.addScore(10);

        assertTrue(frame.hasFirstScore());
        assertFalse(frame.hasSecondScore());
        assertFalse(frame.hasThirdScore());

        assertEquals(10, frame.getFirstScore());
        assertThrows(NoSuchElementException.class, frame::getSecondScore);
        assertThrows(NoSuchElementException.class, frame::getThirdScore);

        assertFalse(frame.isSpare());
        assertTrue(frame.isStrike());
    }

    @Test
    void testSpareFrame() {
        Frame frame = new Frame(1);
        frame.addScore(5);
        frame.addScore(5);

        assertTrue(frame.hasFirstScore());
        assertTrue(frame.hasSecondScore());
        assertFalse(frame.hasThirdScore());

        assertEquals(5, frame.getFirstScore());
        assertEquals(5, frame.getSecondScore());
        assertThrows(NoSuchElementException.class, frame::getThirdScore);

        assertTrue(frame.isComplete());
        assertTrue(frame.isSpare());
        assertFalse(frame.isStrike());
    }

    @Test
    void testLastFrameNormal() {
        Frame frame = new Frame(10);
        frame.addScore(6);
        frame.addScore(3);

        assertTrue(frame.hasFirstScore());
        assertTrue(frame.hasSecondScore());
        assertFalse(frame.hasThirdScore());

        assertEquals(6, frame.getFirstScore());
        assertEquals(3, frame.getSecondScore());
        assertThrows(NoSuchElementException.class, frame::getThirdScore);

        assertTrue(frame.isComplete());
        assertFalse(frame.isSpare());
        assertFalse(frame.isStrike());
    }

    @Test
    void testLastFrameStrike() {
        Frame frame = new Frame(10);
        frame.addScore(10);
        assertFalse(frame.isComplete());
        frame.addScore(10);
        assertFalse(frame.isComplete());
        frame.addScore(10);

        assertTrue(frame.hasFirstScore());
        assertTrue(frame.hasSecondScore());

        assertEquals(10, frame.getFirstScore());
        assertEquals(10, frame.getSecondScore());
        assertEquals(10, frame.getThirdScore());

        assertTrue(frame.isComplete());
        assertFalse(frame.isSpare());
        assertTrue(frame.isStrike());
    }

    @Test
    void testLastFrameSpare() {
        Frame frame = new Frame(10);
        frame.addScore(6);
        assertFalse(frame.isComplete());
        frame.addScore(4);
        assertFalse(frame.isComplete());
        frame.addScore(3);

        assertTrue(frame.hasFirstScore());
        assertTrue(frame.hasSecondScore());
        assertTrue(frame.hasThirdScore());

        assertEquals(6, frame.getFirstScore());
        assertEquals(4, frame.getSecondScore());
        assertEquals(3, frame.getThirdScore());

        assertTrue(frame.isComplete());
        assertTrue(frame.isSpare());
        assertFalse(frame.isStrike());
    }
}
