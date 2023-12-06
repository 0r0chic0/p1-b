package cpen221.soundwaves;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class Task3Tests {

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * Both the waves are not empty and have same length.
     * Wave 2 occurs in wave 1 with amplitude scaling of 2.
     */
    @Test
    public void containsNotEmptySameLength1() {
        double[] left = {0.5, 0.5, 0.5};
        double[] right = {0.5, 0.5, 0.5};
        double[] otherLeft = {0.5, 0.5, 0.5};
        double[] otherRight = {0.5, 0.5, 0.5};
        SoundWave wave1 = new ConcreteSoundWave(left, right);
        SoundWave wave2 = new ConcreteSoundWave(otherLeft, otherRight);
        assertTrue(wave1.contains(wave2));
    }

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * Both the waves are not empty and have same length.
     * Wave 2 occurs in wave 1 with amplitude scaling of 2.
     */
    @Test
    public void containsNotEmptySameLength2() {
        double[] left = {0.5, 0.5, 0.5};
        double[] right = {0.5, 0.5, 0.5};
        double[] otherLeft = {0.25, 0.25, 0.25};
        double[] otherRight = {0.25, 0.25, 0.25};
        SoundWave wave1 = new ConcreteSoundWave(left, right);
        SoundWave wave2 = new ConcreteSoundWave(otherLeft, otherRight);
        assertTrue(wave1.contains(wave2));
    }

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * Both the waves are not empty and have same length.
     * Wave2 occurs in Wave1 with amplitude scaling of 2.
     */
    @Test
    public void containsNotEmptyDiffLength() {
        double[] left = {-0.5, 0.8, 0.8, -0.2};
        double[] right = {-0.5, 0.8, 0.8, -0.2};
        double[] otherLeft = {0.4, 0.4};
        double[] otherRight = {0.4, 0.4};
        SoundWave wave1 = new ConcreteSoundWave(left, right);
        SoundWave wave2 = new ConcreteSoundWave(otherLeft, otherRight);
        assertTrue(wave1.contains(wave2));
    }

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * Both the waves are zero waves and differ in length.
     */
    @Test
    public void containsBothZero1() {
        double[] left = {0, 0, 0, 0, 0, 0};
        double[] right = {0, 0, 0, 0, 0, 0};
        double[] otherLeft = {0, 0};
        double[] otherRight = {0, 0};
        SoundWave wave = new ConcreteSoundWave(left, right);
        SoundWave other = new ConcreteSoundWave(otherLeft, otherRight);
        assertTrue(wave.contains(other));
    }

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * Both the waves are not empty and have same length.
     * Wave does not contain the other.
     */
    @Test
    public void containsNotEmptySameLength3() {
        double[] left = {0.1, 0.2, 0.3, 0.2};
        double[] right = {0.1, 0.2, 0.3, 0.2};
        double[] otherLeft = {0.15, 0.56, 0.78, 0.23};
        double[] otherRight = {0.15, 0.56, 0.78, 0.23};
        SoundWave wave = new ConcreteSoundWave(left, right);
        SoundWave other = new ConcreteSoundWave(otherLeft, otherRight);
        assertFalse(wave.contains(other));
    }

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * Both the waves are not empty and have same length.
     * Wave does not contain the other.
     */
    @Test
    public void containsNotEmptyDiffLength1() {
        double[] left = {0.1, 0.2, 0.3, 0.2, 0.5, 0.4};
        double[] right = {0.1, 0.2, 0.3, 0.2, 0.5, 0.4};
        double[] otherLeft = {0.23, 0.13, 0.05};
        double[] otherRight = {0.23, 0.13, 0.05};
        SoundWave wave = new ConcreteSoundWave(left, right);
        SoundWave other = new ConcreteSoundWave(otherLeft, otherRight);
        assertFalse(wave.contains(other));
    }

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * Test when the other wave is longer.
     */
    @Test
    public void containsOtherIsLonger() {
        double[] left = {0.1, 0.2};
        double[] right = {0.1, 0.2};
        double[] otherLeft = {0.23, 0.13, 0.05, -0.9, 1.0, -0.5};
        double[] otherRight = {0.23, 0.13, 0.05, -0.9, 1.0, -0.5};
        SoundWave wave = new ConcreteSoundWave(left, right);
        SoundWave other = new ConcreteSoundWave(otherLeft, otherRight);
        assertFalse(wave.contains(other));
    }

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * One wave is a zero wave, the other is a non zero wave. They have the same length.
     * Wave does not contain the other.
     */
    @Test
    public void containsOneZero1() {
        double[] left = {0.1, 0.2, 0.3, 0.2, 0.5, 0.4};
        double[] right = {0.1, 0.2, 0.3, 0.2, 0.5, 0.4};
        double[] otherLeft = {0, 0, 0, 0, 0, 0};
        double[] otherRight = {0, 0, 0, 0, 0, 0};
        SoundWave wave = new ConcreteSoundWave(left, right);
        SoundWave other = new ConcreteSoundWave(otherLeft, otherRight);
        assertFalse(wave.contains(other));
    }

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * Test with bigger waves.
     */
    @Test
    public void containsRandom() {
        var containers = new Random()
            .doubles(1000, -1, 1)
            .boxed()
            .collect(Collectors.toCollection(ArrayList::new));
        var position = new Random().nextInt(900);
        var scaling = 0.76;
        var smallwave = containers.stream()
            .skip(position)
            .map(x -> x * scaling)
            .limit(100)
            .collect(Collectors.toCollection(ArrayList::new));

        double[] wave1 = new double[containers.size()];
        for (int i = 0; i < containers.size(); i++) {
            wave1[i] = containers.get(i);
        }

        double[] wave2 = new double[smallwave.size()];
        for (int i = 0; i < smallwave.size(); i++) {
            wave2[i] = smallwave.get(i);
        }

        var big = new ConcreteSoundWave(wave1, wave1);
        var small = new ConcreteSoundWave(wave2, wave2);

        assertTrue(big.contains(small));
    }

    /**
     * similarity(SoundWave other)
     * Test when both of the waves are empty.
     * Specification states that two identical waves have similarity 1.0.
     */
    @Test
    public void similarityBothEmpty() {
        SoundWave sw1 = new ConcreteSoundWave(new double[] {0}, new double[] {0});
        SoundWave sw2 = new ConcreteSoundWave(new double[] {0}, new double[] {0});
        assertEquals(1.0, (sw1.similarity(sw2)), 0.000001);
    }

    /**
     * similarity(SoundWave other)
     * Test when both of the waves are zero waves. Both waves have different length.
     * Specification states that the shorter wave is padded with zeroes.
     * Specification states that the similarity of two zero waves is 1.
     */
    @Test
    public void similarityBothZeroDiffLength() {
        double[] left1 = {0, 0};
        double[] right1 = {0, 0};
        double[] left2 = {0, 0, 0, 0, 0, 0};
        double[] right2 = {0, 0, 0, 0, 0, 0};
        SoundWave sw1 = new ConcreteSoundWave(left1, right1);
        SoundWave sw2 = new ConcreteSoundWave(left2, right2);
        assertEquals(1.0, (sw1.similarity(sw2)), 0.000001);
        /* Test for symmetry. */
        assertEquals(1.0, (sw2.similarity(sw1)), 0.000001);
    }

    /**
     * similarity(SoundWave other)
     * Test similarity between two non-zero waves. Both waves have the same length.
     * Specification states that two identical waves have similarity 1.0.
     */
    @Test
    public void similarityBothNonZeroIdentical() {
        double[] left1 = {0.2, 0.5, -0.2, 0.7};
        double[] right1 = {0.2, 0.5, -0.2, 0.7};
        double[] left2 = {0.2, 0.5, -0.2, 0.7};
        double[] right2 = {0.2, 0.5, -0.2, 0.7};
        SoundWave sw1 = new ConcreteSoundWave(left1, right1);
        SoundWave sw2 = new ConcreteSoundWave(left2, right2);
        assertEquals(1.0, (sw1.similarity(sw2)), 0.000001);
        /* Test for symmetry. */
        assertEquals(1.0, (sw2.similarity(sw1)), 0.000001);
    }

    /**
     * similarity(SoundWave other)
     * Test similarity between two non-zero waves. Both waves have the same length.
     * sw2 is identical to sw1 after amplitude scaling by factor of 2.
     */
    @Test
    public void similarityBothNonZeroScale() {
        double[] left1 = {0.7, 0.5, 0.9, -0.7};
        double[] right1 = {0.7, 0.5, 0.9, -0.7};
        double[] left2 = {0.35, 0.25, 0.45, -0.35};
        double[] right2 = {0.35, 0.25, 0.45, -0.35};
        SoundWave sw1 = new ConcreteSoundWave(left1, right1);
        SoundWave sw2 = new ConcreteSoundWave(left2, right2);
        assertEquals((sw1.similarity(sw2)), 1.0, 0.000001);
        /* Test for symmetry. */
        assertEquals((sw2.similarity(sw1)), 1.0, 0.000001);
    }

    /**
     * similarity(SoundWave other)
     * Test similarity between two non-zero waves. Both waves have the same length.
     */
    @Test
    public void similarityBothNonZero() {
        double[] left1 = {0.7};
        double[] right1 = {0.3};
        double[] left2 = {0.5};
        double[] right2 = {0.1};
        SoundWave sw1 = new ConcreteSoundWave(left1, right1);
        SoundWave sw2 = new ConcreteSoundWave(left2, right2);
        assertEquals(0.9825309620671148638, (sw1.similarity(sw2)), 0.000001);
        /* Test for symmetry. */
        assertEquals(0.9825309620671148638, (sw2.similarity(sw1)), 0.000001);
    }

    /**
     * similarity(SoundWave other)
     * Test similarity between two non-zero waves. Both waves have the same length.
     */
    @Test
    public void similarityBothNonZero1() {
        double[] left1 = {0.3, 0.2, 0.5};
        double[] right1 = {0.4, -0.2, 0.7};
        double[] left2 = {0.5, 0.9, 0.6};
        double[] right2 = {0.1, 0.2, 0.1};
        SoundWave sw1 = new ConcreteSoundWave(left1, right1);
        SoundWave sw2 = new ConcreteSoundWave(left2, right2);
        assertEquals(0.5348080338822119, (sw1.similarity(sw2)), 0.000001);
        /* Test for symmetry. */
        assertEquals(0.5348080338822119, (sw2.similarity(sw1)), 0.000001);
    }

    /**
     * similarity(SoundWave other)
     * Test similarity between two non-zero waves. Two waves have different length.
     * Specification states that the shorter wave is padded with zeroes.
     */
    @Test
    public void similarityBothNonZeroDiffLength() {
        double[] left1 = {0.3};
        double[] right1 = {0.4};
        double[] left2 = {0.5, 0.1, 0.1};
        double[] right2 = {0.1, 0.2, 0.1};
        SoundWave sw1 = new ConcreteSoundWave(left1, right1);
        SoundWave sw2 = new ConcreteSoundWave(left2, right2);
        assertEquals(0.86009083855719, (sw1.similarity(sw2)), 0.000001);
        /* Test for symmetry. */
        assertEquals(0.86009083855719, (sw2.similarity(sw1)), 0.000001);
    }

    /**
     * similarity(SoundWave other)
     * Test similarity between two non-zero waves. Two waves have different length.
     * Specification states that the shorter wave is padded with zeroes.
     */
    @Test
    public void similarityBothNonZeroPadded() {
        double[] left1 = {0.3, 0.0, 0.0};
        double[] right1 = {0.4, 0.0, 0.0};
        double[] left2 = {0.5, 0.1, 0.1};
        double[] right2 = {0.1, 0.2, 0.1};
        SoundWave sw1 = new ConcreteSoundWave(left1, right1);
        SoundWave sw2 = new ConcreteSoundWave(left2, right2);
        assertEquals(0.86009083855719, (sw1.similarity(sw2)), 0.000001);
        /* Test for symmetry. */
        assertEquals(0.86009083855719, (sw2.similarity(sw1)), 0.000001);
    }
}
