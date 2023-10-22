package cpen221.soundwaves;

// See: https://en.wikipedia.org/wiki/Smoke_testing_(software)

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import static cpen221.soundwaves.soundutils.FilterType.*;
import static org.junit.jupiter.api.Assertions.*;

public class SmokeTests {

    @Test
    public void testCreateWave() {
        double[] lchannel = {1.0, -1.0};
        double[] rchannel = {1.0, -1.0};
        SoundWave wave = new ConcreteSoundWave(lchannel, rchannel);
        double[] lchannel1 = wave.getLeftChannel();
        assertArrayEquals(lchannel, lchannel1, 0.00001);
        double[] rchannel1 = wave.getRightChannel();
        assertArrayEquals(rchannel, rchannel1, 0.00001);
    }

    /**
     * SoundWave(double[] lchannel, double[] rchannel)
     * Test when entries of lchannel and rchannel are all zero.
     */
    @Test
    public void testCreateWaveZero() {
        double[] leftChannel = {0.0, 0.0, 0.0, 0.0, 0.0};
        double[] rightChannel = {0.0, 0.0, 0.0, 0.0, 0.0};
        SoundWave wave = new ConcreteSoundWave(leftChannel, rightChannel);
        double[] leftChannel1 = wave.getLeftChannel();
        assertArrayEquals(leftChannel, leftChannel1, 0.00001);
        double[] rightChannel1 = wave.getRightChannel();
        assertArrayEquals(rightChannel, rightChannel1, 0.00001);
    }

    /**
     * SoundWave(double freq, double phase, double amplitude, double duration)
     */
    @Test
    public void testCreateSineWave() {
        double freq = 400.0;
        double phase = 0.0;
        double amplitude = 1.0;
        double duration = 4.0 / 44100.0;
        SoundWave sw1 = SinusoidalWave.getInstance(freq, phase, amplitude, duration);
        double[] left = {0, 0.056959498116, 0.113734047592, 0.17013930031};
        double[] right = {0, 0.056959498116, 0.113734047592, 0.17013930031};
        assertArrayEquals(left, sw1.getLeftChannel(), 0.00001);
        assertArrayEquals(right, sw1.getRightChannel(), 0.00001);
    }

    @Test
    public void testCreateSineWave2() {
        double freq = 6000.0;
        double phase = 3.4;
        double amplitude = 1.0;
        double duration = 5.0 / 44100.0;
        SoundWave sw1 = SinusoidalWave.getInstance(freq, phase, amplitude, duration);
        double[] left = {- 1.4205192796 * Math.pow(10,-11), 0.754475850908, 0.990366961496, 0.545534901223, -0.274267510656};
        double[] right = {- 1.4205192796 * Math.pow(10,-11), 0.754475850908, 0.990366961496, 0.545534901223, -0.274267510656};
        assertArrayEquals(left, sw1.getLeftChannel(), 0.00001);
        assertArrayEquals(right, sw1.getRightChannel(), 0.00001);
    }

    /**
     * Test for SquareWave: assumes that the value at jump time is +amplitude
     * Example: wave at time 0 with no phase change and amplitude 1 is 1
     */
    @Test
    public void testCreateSquareWave1() {
        double freq = 6000;
        double phase = 0.0;
        double amplitude = 1.0;
        double duration = 5.0 / 44100.0;
        SoundWave sw1 = SquareWave.getInstance(freq, phase, amplitude, duration);
        double[] left = {1, 1, 1, 1, -1};
        double[] right = {1, 1, 1, 1, -1};

        assertArrayEquals(left, sw1.getLeftChannel(), 0.00001);
        assertArrayEquals(right, sw1.getRightChannel(), 0.00001);
    }

    @Test
    public void testCreateSquareWave2() {
        double freq = 6000;
        double phase = 3.4;
        double amplitude = 1.0;
        double duration = 5.0 / 44100.0;
        SoundWave sw1 = SquareWave.getInstance(freq, phase, amplitude, duration);
        double[] left = {-1, 1, 1, 1, -1};
        double[] right = {-1, 1, 1, 1, -1};

        assertArrayEquals(left, sw1.getLeftChannel(), 0.00001);
        assertArrayEquals(right, sw1.getRightChannel(), 0.00001);
    }

    /**
     * Test for triangle wave
     */
    @Test
    public void testCreateTriangleWave() {
        double freq = 1;
        double phase = 0.0;
        double amplitude = 1.0;
        double duration = 5.0 / 44100.0;
        SoundWave sw1 = TriangleWave.getInstance(freq, phase, amplitude, duration);
        double[] left = {0, 0.0000907, 0.0001814, 0.0002721, 0.0003628};
        double[] right = {0, 0.0000907, 0.0001814, 0.0002721, 0.0003628};

        assertArrayEquals(left, sw1.getLeftChannel(), 0.00001);
        assertArrayEquals(right, sw1.getRightChannel(), 0.00001);
    }

    /**
     * append(double[] leftChannel, double[] rightChannel)
     * Test when both leftChannel and rightChannel are not empty. The wave is not empty too.
     * Left and right channels have different values to test more values.
     */
    @Test
    public void testAppend() {
        double[] leftChannel = {0.5, 0.3, -0.2};
        double[] rightChannel = {0.3, 0.4, 0.5};
        double[] addLeft = {0.9, 0.9};
        double[] addRight = {0.1, -0.5};
        double[] leftAfter = {0.5, 0.3, -0.2, 0.9, 0.9};
        double[] rightAfter = {0.3, 0.4, 0.5, 0.1, -0.5};
        SoundWave sw1 = new ConcreteSoundWave(leftChannel, rightChannel);
        sw1.append(addLeft, addRight);
        assertArrayEquals(leftAfter, sw1.getLeftChannel(), 0.0001);
        assertArrayEquals(rightAfter, sw1.getRightChannel(), 0.0001);
    }

    @Test
    public void testAddEcho1() {
        SoundWave wave1 = SinusoidalWave.getInstance(2205.0, 0.0, 0.7, 50.0 / SoundWave.SAMPLES_PER_SECOND);
        SoundWave wave2 = wave1.addEcho(0.0, 0.0);
        assertTrue(wave1.equals(wave2));
    }

    @Test
    public void testAddEcho2() {
        double[] leftChannel = {0.3, 0.1, -0.4, 0.2};
        double[] rightChannel = {0.4, 0.2, -0.3, 0.3};

        SoundWave test1 = new ConcreteSoundWave(leftChannel, rightChannel);
        SoundWave result = test1.addEcho(2.0 / 44100.0, 0.5);
        double[] leftAfter = {0.3, 0.1, -0.25, 0.25};
        double[] rightAfter = {0.4, 0.2, -0.1, 0.4};
        assertArrayEquals(leftAfter, result.getLeftChannel(), 0.0001);
        assertArrayEquals(rightAfter, result.getRightChannel(), 0.0001);
    }

    @Test
    public void testAddEcho3() {
        double[] leftChannel = {0.3, 0.1, -0.4, 0.2};
        double[] rightChannel = {0.4, 0.2, -0.3, 0.3};

        SoundWave test1 = new ConcreteSoundWave(leftChannel, rightChannel);
        SoundWave result2 = test1.addEcho(4.0 / 44100.0, 1.0);
        assertArrayEquals(leftChannel, result2.getLeftChannel(), 0.0001);
        assertArrayEquals(rightChannel, result2.getRightChannel(), 0.0001);
    }

    /**
     * SoundWave add(SoundWave other)
     * Create a new wave by adding another wave to this wave.
     * Wave is then normalized to [-1,1].
     * The length of the channels in both waves are the same.
     */
    @Test
    public void testAdd() {
        double[] left = {0.7, -0.3, 0.1, -0.9};
        double[] right = {0.7, -0.3, 0.1, -0.9};
        double[] otherLeft = {0.5, 0.7, -0.5, -0.5};
        double[] otherRight = {0.5, 0.7, -0.5, -0.5};
        double[] resultLeft = {1.2/1.4, 0.4/1.4, -0.4/1.4, -1.4/1.4};
        double[] resultRight = {1.2/1.4, 0.4/1.4, -0.4/1.4, -1.4/1.4};
        SoundWave wave = new ConcreteSoundWave(left, right);
        SoundWave other = new ConcreteSoundWave(otherLeft, otherRight);
        SoundWave result = wave.add(other);
        assertArrayEquals(resultLeft, result.getLeftChannel(), 0.00001);
        assertArrayEquals(resultRight, result.getRightChannel(), 0.00001);
    }

    @Test
    public void testScale1() {
        double[] left = {0.8, -0.6, 0.1, -0.5};
        double[] right = {0.3, -0.3, 0.6, -0.4};
        double[] leftAfter = {1, -0.75, 0.125, -0.625};
        double[] rightAfter = {0.5, -0.5, 1.0, -0.6666666667};

        SoundWave test1 = new ConcreteSoundWave(left, right);
        test1.scale(2.0);

        assertArrayEquals(leftAfter, test1.getLeftChannel(), 0.00001);
        assertArrayEquals(rightAfter, test1.getRightChannel(), 0.00001);

        test1.scale(0.5);

        double[] leftAfter2 = {1.0 / 2.0, -0.75 / 2, 0.125 / 2, -0.625 / 2};
        double[] rightAfter2 = {0.5 / 2, -0.5 / 2, 1.0 / 2, -0.6666666667 / 2};

        assertArrayEquals(leftAfter2, test1.getLeftChannel(), 0.00001);
        assertArrayEquals(rightAfter2, test1.getRightChannel(), 0.00001);
    }

    /**
     * contains(SoundWave other)
     * Determine if this wave fully contains the other sound wave as a pattern.
     * Both the waves are not empty and have same length.
     * Wave 2 occurs in wave 1 with amplitude scaling of 2.
     */
    @Test
    public void testContains1() {
        double[] left = {0.5, 0.5, 0.5};
        double[] right = {0.5, 0.5, 0.5};
        double[] otherLeft = {0.25, 0.25, 0.25};
        double[] otherRight = {0.25, 0.25, 0.25};
        SoundWave wave1 = new ConcreteSoundWave(left, right);
        SoundWave wave2 = new ConcreteSoundWave(otherLeft, otherRight);
        assertTrue(wave1.contains(wave2));
    }

    @Test
    public void testContains2() {
        double[] left = {0.5, 0.5, 0.5};
        double[] right = {0.5, 0.5, 0.5};
        double[] otherLeft = {-0.25, -0.25, -0.25};
        double[] otherRight = {0.25, 0.25, 0.25};

        SoundWave wave1 = new ConcreteSoundWave(left, right);
        SoundWave wave2 = new ConcreteSoundWave(otherLeft, otherRight);
        assertFalse(wave1.contains(wave2));
    }

    @Test
    public void testContains3() {
        double[] left = {0.5, 0.5, 0.5};
        double[] right = {0, 0.5, 0.5};
        double[] otherLeft = {0.25, 0.25, 0.25};
        double[] otherRight = {0.25, 0.25, 0.25};

        SoundWave wave1 = new ConcreteSoundWave(left, right);
        SoundWave wave2 = new ConcreteSoundWave(otherLeft, otherRight);
        assertFalse(wave1.contains(wave2));
    }

    @Test
    public void testContains4() {
        double[] left = {0.5, 0.5, 0.5};
        double[] right = {0.5, 0.5, 0.5};
        double[] otherLeft = {0.5, 0.25, 0.25};
        double[] otherRight = {0.5, 0.25, 0.25};

        SoundWave wave1 = new ConcreteSoundWave(left, right);
        SoundWave wave2 = new ConcreteSoundWave(otherLeft, otherRight);
        assertFalse(wave1.contains(wave2));
    }

    @Test
    public void testContains5() {
        double[] left = {0.5, 0.5, 0.5};
        double[] right = {0.5, 0.5, 0.5};
        double[] otherLeft = {0.25, 0.25};
        double[] otherRight = {0.25, 0.25};

        SoundWave wave1 = new ConcreteSoundWave(left, right);
        SoundWave wave2 = new ConcreteSoundWave(otherLeft, otherRight);
        assertTrue(wave1.contains(wave2));
    }

    @Test
    public void testContains6() {
        double[] left = {0.5, -0.5, 0.5};
        double[] right = {0.5, -0.5, 0.5};
        double[] otherLeft = {0.25, -0.25, 0.25};
        double[] otherRight = {0.25, -0.25, 0.25};

        SoundWave wave1 = new ConcreteSoundWave(left, right);
        SoundWave wave2 = new ConcreteSoundWave(otherLeft, otherRight);
        assertTrue(wave1.contains(wave2));
    }

    /**
     * similarity(SoundWave other)
     * Test similarity between two non-zero waves. Both waves have the same length.
     * sw2 is identical to sw1 after amplitude scaling by factor of 2.
     */
    @Test
    public void testSimilarity() {
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

    @Test
    public void testSimilarSounds() {
        var sounds = new ArrayList<SoundWave>();
        for (var i = 0; i < 5; i++) {
            var l = randomSignal();
            var r = randomSignal();
            sounds.add(new ConcreteSoundWave(l, r));

            sounds.add(new ConcreteSoundWave(randomNoise(l), randomNoise(r)));
            sounds.add(new ConcreteSoundWave(randomNoise(l), randomNoise(r)));
        }

        var set = new HashSet<>(sounds);

        /* Verify the set we get back are the original three we added noise to. */
        var firstSet = new SoundWaveSimilarity().getSimilarSounds(set, 5, sounds.get(0));
        assert firstSet != null;
        assertTrue(firstSet.containsAll(sounds.subList(0, 3)));
    }

    @Test
    public void testHighestAmplitudeFrequencyComponent() {
        SoundWave wave1 = SinusoidalWave.getInstance(400.0,0,0.50,100.0 / SoundWave.SAMPLES_PER_SECOND);
        assertEquals(441, wave1.highestAmplitudeFrequencyComponent(), 1);
        SoundWave wave2 = SinusoidalWave.getInstance(2205,0,0.30,100.0 / SoundWave.SAMPLES_PER_SECOND);
        assertEquals(2205, wave2.highestAmplitudeFrequencyComponent(), 1);
        SoundWave wave3 = SinusoidalWave.getInstance(980, 0, 0.40, 100.0 / SoundWave.SAMPLES_PER_SECOND);
        SoundWave wave4 = wave3.add(wave2);
        assertEquals(882, wave4.highestAmplitudeFrequencyComponent(), 1);
        SoundWave wave5 = wave4.add(wave1);
        assertEquals(441.0, wave5.highestAmplitudeFrequencyComponent());
    }

    @Test
    public void testHighestAmplitudeFrequencyComponentWithPhase() {
        SoundWave wave1 = SinusoidalWave.getInstance(400.0,3.2,0.50,100.0 / SoundWave.SAMPLES_PER_SECOND);
        assertEquals(441, wave1.highestAmplitudeFrequencyComponent(), 1);
        SoundWave wave2 = SinusoidalWave.getInstance(2205,3.6,0.30,100.0 / SoundWave.SAMPLES_PER_SECOND);
        assertEquals(2205, wave2.highestAmplitudeFrequencyComponent(), 1);
        SoundWave wave3 = SinusoidalWave.getInstance(980, 5, 0.40, 100.0 / SoundWave.SAMPLES_PER_SECOND);
        SoundWave wave4 = wave3.add(wave2);
        assertEquals(882, wave4.highestAmplitudeFrequencyComponent(), 1);
        SoundWave wave5 = wave4.add(wave1);
        assertEquals(441.0, wave5.highestAmplitudeFrequencyComponent());
    }

    @Test
    public void testFilter() {
        // Test high pass filter
        SoundWave wave1 = SinusoidalWave.getInstance(441.0, 0, 0.50, 100.0 / SoundWave.SAMPLES_PER_SECOND);
        SoundWave wave2 = SinusoidalWave.getInstance(2205.0, 0, 0.7, 100.0 / SoundWave.SAMPLES_PER_SECOND);
        SoundWave wave3 = wave1.add(wave2);
        SoundWave wave3f = wave3.filter(HIGHPASS, 2000.0);
        assertEquals(2205.0, wave3f.highestAmplitudeFrequencyComponent());

        // Test low pass filter
        SoundWave wave4 = SinusoidalWave.getInstance(441.0, 0, 0.50, 100.0 / SoundWave.SAMPLES_PER_SECOND);
        SoundWave wave5 = SinusoidalWave.getInstance(2205.0, 0, 0.7, 100.0 / SoundWave.SAMPLES_PER_SECOND);
        SoundWave wave6 = wave4.add(wave5);
        SoundWave wave6f = wave6.filter(LOWPASS, 500.0);
        assertEquals(441.0, wave6f.highestAmplitudeFrequencyComponent());

        SoundWave wave7 = SinusoidalWave.getInstance(441.0, 0, 0.50, 100.0 / SoundWave.SAMPLES_PER_SECOND);
        SoundWave wave8 = SinusoidalWave.getInstance(2646.0, 0, 0.70, 100.0 / SoundWave.SAMPLES_PER_SECOND);
        SoundWave wave9 = wave8.add(wave7);
        SoundWave wave9f = wave9.filter(BANDPASS, 500.0, 3000.0);
        assertEquals(2646.0, wave9f.highestAmplitudeFrequencyComponent());

        SoundWave wave10 = wave9.add(wave6);
        SoundWave wave10f = wave10.filter(LOWPASS, 500.0);
        assertEquals(441.0, wave10f.highestAmplitudeFrequencyComponent());
    }

    @Test
    public void testSimilarityWithIdenticalWaves() {

        double[] leftChannel = {0.1, 0.2, 0.3};
        double[] rightChannel = {0.1, 0.2, 0.3};

        ConcreteSoundWave wave1 = new ConcreteSoundWave(leftChannel, rightChannel);
        ConcreteSoundWave wave2 = new ConcreteSoundWave(leftChannel, rightChannel);

        wave1.scale(0.99);
        wave2.scale(0.99);


        double similarity = wave1.similarity(wave2);


        assertEquals(1.0, similarity, 0.01);
    }

    @Test
    public void testSimilarityWithOppositeWaves() {

        double[] Channel1 = {0.1, 0.2, 0.3};
        double[] Channel2 = {-0.1, -0.2, -0.3};

        ConcreteSoundWave wave1 = new ConcreteSoundWave(Channel1, Channel1);
        ConcreteSoundWave wave2 = new ConcreteSoundWave(Channel2, Channel2);

        double similarity = wave1.similarity(wave2);

        assertEquals(0.78125, similarity, 0.01);
    }

    @Test
    public void testSimilarityOnBoundary() {

        double[] Channel1 = {0.0 ,0.1, 0.2, 0.3};
        double[] Channel2 = {0.1 ,0.3, -0.1, -0.4};

        ConcreteSoundWave wave1 = new ConcreteSoundWave(Channel1, Channel1);
        ConcreteSoundWave wave2 = new ConcreteSoundWave(Channel2, Channel2);

        double similarity = wave1.similarity(wave2);

        assertEquals(0.7153, similarity, 0.01);
    }

    /**
     * Generate a random signal of length 100.
     *
     */
    private double[] randomSignal() {
        return new Random().doubles(100).map(x -> -1.0 + (x * 2)).toArray();
    }

    /**
     * Corrupt a signal slightly, adding +/- 0.1 to every sample.
     */
    private double[] randomNoise(double[] original) {
        var rand = new Random();
        return Arrays.stream(original).map(x -> x + rand.nextDouble() * 0.2 - 0.1).toArray();
    }

}
