package cpen221.soundwaves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Tests {

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

    @Test
    public void testCreateSineWave() {
        double freq = 200.0;
        double phase = 0.0;
        double amplitude = 1.0;
        double duration = 4.0 / 44100.0;
        SoundWave sw1 = SinusoidalWave.getInstance(freq, phase, amplitude, duration);
        double[] left =  {0, 0.028491315390844967, 0.05695949811699588, 0.08538143429562847};
        double[] right = {0, 0.028491315390844967, 0.05695949811699588, 0.08538143429562847};
        assertArrayEquals(left,  sw1.getLeftChannel(),  0.00001);
        assertArrayEquals(right, sw1.getRightChannel(), 0.00001);
    }

    @Test
    public void testCreateTriangleWave() {
        double freq = 0.25;
        double phase = 0.0;
        double amplitude = 1.0;
        double duration = 4 / 44100.0;
        SoundWave sw1 = TriangleWave.getInstance(freq, phase, amplitude, duration);
        double[] left = {0,  2.2675736961451248E-5, 4.5351473922902495E-5, 6.802721088435374E-5};
        double[] right = {0, 2.2675736961451248E-5, 4.5351473922902495E-5, 6.802721088435374E-5};
        assertArrayEquals(left, sw1.getLeftChannel(), 0.000000001);
        assertArrayEquals(right, sw1.getRightChannel(), 0.000000001);


    }

    @Test
    public void testCreateSquareWaveZeroPhase() {
        double freq = 1;
        double phase = 0;
        double amplitude = 1.0;
        double duration = 2;
        SoundWave sw1 = SquareWave.getInstance(freq, phase, amplitude, duration);
        assertEquals(+1.0, sw1.getLeftChannel()[1], 0.001);
        assertEquals(+1.0, sw1.getRightChannel()[1], 0.001);
        assertEquals(+1.0, sw1.getRightChannel()[22000], 0.001);
        assertEquals(-1.0, sw1.getRightChannel()[22052], 0.001);
        assertEquals(-1.0, sw1.getRightChannel()[22055], 0.001);
        assertEquals(-1.0, sw1.getRightChannel()[44099], 0.001);
        assertEquals(+1.0, sw1.getRightChannel()[44102], 0.001);
    }

    @Test
    public void testCreateSquareWaveZeroPhaseAmp2() {
        double freq = 1;
        double phase = 0;
        double amplitude = 2.0;
        double duration = 2;
        SoundWave sw1 = SquareWave.getInstance(freq, phase, amplitude, duration);
        assertEquals(+1.0, sw1.getLeftChannel()[1], 0.001);
        assertEquals(+1.0, sw1.getRightChannel()[1], 0.001);
        assertEquals(+1.0, sw1.getRightChannel()[22000], 0.001);
        assertEquals(-1.0, sw1.getRightChannel()[22052], 0.001);
        assertEquals(-1.0, sw1.getRightChannel()[22055], 0.001);
        assertEquals(-1.0, sw1.getRightChannel()[44099], 0.001);
        assertEquals(+1.0, sw1.getRightChannel()[44102], 0.001);
    }
}
