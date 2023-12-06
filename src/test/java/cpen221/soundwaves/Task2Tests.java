package cpen221.soundwaves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Task2Tests {
    /**
     * append(double[] leftChannel, double[] rightChannel)
     * Test when both leftChannel and rightChannel are not empty. The wave is not empty too.
     * Left and right channels have different values to test more values.
     */
    @Test
    public void testAppend0() {
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

    /**
     * append(double[] leftChannel, double[] rightChannel)
     * Test when both addLeft and addRight are not empty. But the wave is empty.
     */
    @Test
    public void testAppend1() {
        double[] addLeft = {0.3, 0.2, -0.5, 0.9};
        double[] addRight = {0.3, 0.2, -0.5, 0.9};
        double[] leftAfter = {0.3, 0.2, -0.5, 0.9};
        double[] rightAfter = {0.3, 0.2, -0.5, 0.9};
        SoundWave sw1 = new ConcreteSoundWave(new double[]{}, new double[] {});
        sw1.append(addLeft, addRight);
        assertArrayEquals(leftAfter, sw1.getLeftChannel(), 0.0001);
        assertArrayEquals(rightAfter, sw1.getRightChannel(), 0.0001);
    }

    /**
     * append(double[] leftChannel, double[] rightChannel)
     * Test when both leftChannel and rightChannel are empty. But the wave is not empty.
     */
    @Test
    public void testAppend2() {
        double[] leftChannel = {0.1, -0.5, 0.7, 0.9};
        double[] rightChannel = {0.1, -0.5, 0.7, 0.9};
        double[] addLeft = {};
        double[] addRight = {};
        double[] leftAfter = {0.1, -0.5, 0.7, 0.9};
        double[] rightAfter = {0.1, -0.5, 0.7, 0.9};
        SoundWave sw1 = new ConcreteSoundWave(leftChannel, rightChannel);
        sw1.append(addLeft, addRight);
        assertArrayEquals(leftAfter, sw1.getLeftChannel(), 0.0001);
        assertArrayEquals(rightAfter, sw1.getRightChannel(), 0.0001);
    }

    /**
     * append(SoundWave other)
     * Original and appending wave are both not empty
     */
    @Test
    public void testAppendWave1() {
        double[] leftChannel = {0.2, -0.3, 0.1};
        double[] rightChannel = {0.2, -0.3, 0.1};
        double[] leftAdd = {-0.5, 0.3, 0.2};
        double[] rightAdd = {-0.5, 0.3, 0.2};
        double[] resultLeft = {0.2, -0.3, 0.1, -0.5, 0.3, 0.2};
        double[] resultRight = {0.2, -0.3, 0.1, -0.5, 0.3, 0.2};
        SoundWave wave = new ConcreteSoundWave(leftChannel, rightChannel);
        SoundWave other = new ConcreteSoundWave(leftAdd, rightAdd);
        wave.append(other);
        double[] leftChannelAfter = wave.getLeftChannel();
        double[] rightChannelAfter = wave.getRightChannel();
        assertArrayEquals(resultLeft, leftChannelAfter, 0.00001);
        assertArrayEquals(resultRight, rightChannelAfter, 0.00001);
    }

    /**
     * append(SoundWave other)
     * Original wave is not empty, but appending wave is empty.
     */
    @Test
    public void testAppendWave2() {
        double[] leftChannel = {0.2, 0.9, -0.3};
        double[] rightChannel = {0.2, 0.9, -0.3};
        double[] leftAdd = {};
        double[] rightAdd = {};
        double[] resultLeft = {0.2, 0.9, -0.3};
        double[] resultRight = {0.2, 0.9, -0.3};
        SoundWave wave = new ConcreteSoundWave(leftChannel, rightChannel);
        SoundWave other = new ConcreteSoundWave(leftAdd, rightAdd);
        wave.append(other);
        double[] leftChannelAfter = wave.getLeftChannel();
        double[] rightChannelAfter = wave.getRightChannel();
        assertArrayEquals(resultLeft, leftChannelAfter, 0.00001);
        assertArrayEquals(resultRight, rightChannelAfter, 0.00001);
    }

    /**
     * append(SoundWave other)
     * Original wave is a zero wave, other is not.
     */
    @Test
    public void testAppendWave3() {
        double[] leftChannel = {0.0, 0.0, 0.0, 0.0};
        double[] rightChannel = {0.0, 0.0, 0.0, 0.0};
        double[] leftAdd = {0.3, 0.7};
        double[] rightAdd = {0.3, 0.7};
        double[] resultLeft = {0.0, 0.0, 0.0, 0.0, 0.3, 0.7};
        double[] resultRight = {0.0, 0.0, 0.0, 0.0, 0.3, 0.7};
        SoundWave wave = new ConcreteSoundWave(leftChannel, rightChannel);
        SoundWave other = new ConcreteSoundWave(leftAdd, rightAdd);
        wave.append(other);
        double[] leftChannelAfter = wave.getLeftChannel();
        double[] rightChannelAfter = wave.getRightChannel();
        assertArrayEquals(resultLeft, leftChannelAfter, 0.00001);
        assertArrayEquals(resultRight, rightChannelAfter, 0.00001);
    }

    /**
     * SoundWave add(SoundWave other)
     * Create a new wave by adding another wave to this wave.
     * Wave is then normalized to [-1,1].
     * The length of the channels in both waves are the same.
     */
    @Test
    public void testAddNotEmptySameLength() {
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

    /**
     * SoundWave add(SoundWave other)
     * Create a new wave by adding another wave to this wave.
     * Wave is then clipped to [-1,1].
     * The length of the channels in the first wave is longer.
     */
    @Test
    public void testAddNotEmptyDiffLength() {
        double[] left = {0.2, 0.1, -0.5, 0.9, -0.9};
        double[] right = {0.2, 0.1, -0.5, 0.9, -0.9};
        double[] otherLeft = {0.5, -0.7};
        double[] otherRight = {0.5, -0.7};
        double[] resultLeft = {0.7, -0.6, -0.5, 0.9, -0.9};
        double[] resultRight = {0.7, -0.6, -0.5, 0.9, -0.9};
        SoundWave wave = new ConcreteSoundWave(left, right);
        SoundWave other = new ConcreteSoundWave(otherLeft, otherRight);
        SoundWave result = wave.add(other);
        assertArrayEquals(resultLeft, result.getLeftChannel(), 0.00001);
        assertArrayEquals(resultRight, result.getRightChannel(), 0.00001);
    }

    /**
     * SoundWave add(SoundWave other)
     * Create a new wave by adding another wave to this wave.
     * Wave is then clipped to [-1,1].
     * The length of the channels in the first wave is shorter.
     */
    @Test
    public void testAdd3() {
        double[] left = {-0.3, -0.3};
        double[] right = {-0.3, -0.3};
        double[] otherLeft = {0.2, -0.9, -0.1, 0.2};
        double[] otherRight = {0.2, -0.9, -0.1, 0.2};
        double[] resultLeft = {-0.1/1.2, -1.2/1.2, -0.1/1.2, 0.2/1.2};
        double[] resultRight = {-0.1/1.2, -1.2/1.2, -0.1/1.2, 0.2/1.2};
        SoundWave wave = new ConcreteSoundWave(left, right);
        SoundWave other = new ConcreteSoundWave(otherLeft, otherRight);
        SoundWave result = wave.add(other);
        assertArrayEquals(resultLeft, result.getLeftChannel(), 0.00001);
        assertArrayEquals(resultRight, result.getRightChannel(), 0.00001);
    }

    /**
     * addEcho(int delta, double alpha)
     * Create a new wave by adding an echo to this wave.
     * Test when the wave is empty, delta is > 0.
     */
    @Test
    public void echoTestEmpty() {
        double[] left = {0, 0, 0};
        double[] right = {0, 0, 0};
        SoundWave wave = new ConcreteSoundWave(left, right);
        SoundWave result = wave.addEcho(2/SoundWave.SAMPLES_PER_SECOND, 0.5);
        assertArrayEquals(left, result.getLeftChannel(), 0.00001);
        assertArrayEquals(right, result.getRightChannel(), 0.00001);
    }

    /**
     * addEcho(int delta, double alpha)
     * Create a new wave by adding an echo to this wave.
     * Test when the wave is not empty, delta is zero but alpha is not.
     */
    @Test
    public void echoTestNotEmpty() {
        double[] leftChannel = {0.5, 0.2, 0.7};
        double[] rightChannel = {0.5, 0.2, 0.7};
        SoundWave wave = new ConcreteSoundWave(leftChannel, rightChannel);
        double[] left = {0.55, 0.22, 0.77};
        double[] right = {0.55, 0.22, 0.77};
        SoundWave result = wave.addEcho(0, 0.1);
        assertArrayEquals(left, result.getLeftChannel(), 0.00001);
        assertArrayEquals(right, result.getRightChannel(), 0.00001);
    }

    /**
     * addEcho(int delta, double alpha)
     * Create a new wave by adding an echo to this wave.
     * Test when the wave is not empty, delta is zero, alpha is not zero and < 1.
     */
    @Test
    public void echoTestNotEmpty2() {
        double[] leftChannel = {0.5, 0.2, 0.7};
        double[] rightChannel = {0.3, 0.2, -0.7};
        SoundWave wave = new ConcreteSoundWave(leftChannel, rightChannel);
        double[] left = {0.75/1.05, 0.3/1.05, 1.05/1.05};
        double[] right = {0.45/1.05, 0.3/1.05, -1.05/1.05};
        SoundWave result = wave.addEcho(0, 0.5);
        assertArrayEquals(left, result.getLeftChannel(), 0.00001);
        assertArrayEquals(right, result.getRightChannel(), 0.00001);
    }

    /**
     * addEcho(int delta, double alpha)
     * Create a new wave by adding an echo to this wave.
     * Test when the wave is not empty, delta is not zero.
     */
    @Test
    public void echoTestNotEmpty3() {
        double[] leftChannel = {1.0, 0.5, 0.7, -0.3};
        double[] rightChannel = {1.0, 0.5, 0.7, -0.3};
        SoundWave wave = new ConcreteSoundWave(leftChannel, rightChannel);
        double[] left = {1.0, 0.5, 0.7, 0.2, 0.25, 0.35, -0.15};
        double[] right = {1.0, 0.5, 0.7, 0.2, 0.25, 0.35, -0.15};
        SoundWave result = wave.addEcho(3/SoundWave.SAMPLES_PER_SECOND, 0.5);
        assertArrayEquals(left, result.getLeftChannel(), 0.00001);
        assertArrayEquals(right, result.getRightChannel(), 0.00001);
    }

    /**
     * addEcho(int delta, double alpha)
     * Create a new wave by adding an echo to this wave.
     * Test when the wave is not empty, delta and alpha are both not zero.
     */
    @Test
    public void echoTestNotEmpty4() {
        double[] leftChannel = {0.3, -0.7, 0.5, 0.7};
        double[] rightChannel = {0.3, -0.7, 0.5, 0.7};
        SoundWave wave = new ConcreteSoundWave(leftChannel, rightChannel);
        double[] left = {0.3, -0.7, 0.65, 0.35, 0.25, 0.35};
        double[] right = {0.3, -0.7, 0.65, 0.35, 0.25, 0.35};
        SoundWave result = wave.addEcho(2/SoundWave.SAMPLES_PER_SECOND, 0.5);
        assertArrayEquals(left, result.getLeftChannel(), 0.00001);
        assertArrayEquals(right, result.getRightChannel(), 0.00001);
    }

    /**
     * addEcho(int delta, double alpha)
     * Create a new wave by adding an echo to this wave.
     * Test when the wave is not empty, delta is zero, alpha is not zero and greater than 1.
     */
    @Test
    public void echoTestNotEmpty5() {
        double[] leftChannel = {0.5, -0.4, 0.1, 0.2};
        double[] rightChannel = {0.5, -0.4, 0.1, 0.2};
        SoundWave wave = new ConcreteSoundWave(leftChannel, rightChannel);
        double[] left = {0.5/1.1, -0.4/1.1, 1.1/1.1, -0.6/1.1, 0.2/1.1, 0.4/1.1};
        double[] right = {0.5/1.1, -0.4/1.1, 1.1/1.1, -0.6/1.1, 0.2/1.1, 0.4/1.1};
        SoundWave result = wave.addEcho(2/SoundWave.SAMPLES_PER_SECOND, 2.0);
        assertArrayEquals(left, result.getLeftChannel(), 0.00001);
        assertArrayEquals(right, result.getRightChannel(), 0.00001);
    }

    /**
     * scale(double scalingFactor)
     * Test when the wave is not empty. Check if the wave is normalized to [-1,1].
     * scaling factor is greater than 1.
     */
    @Test
    public void scaleTest1() {
        double[] left  = {0.9, 0.9, -0.3};
        double[] right = {0.9, 0.9, -0.3};
        SoundWave wave = new ConcreteSoundWave(left, right);
        wave.scale(2.0);
        double[] scaleLeft = {1.8/1.8, 1.8/1.8, -0.6/1.8};
        double[] scaleRight = {1.8/1.8, 1.8/1.8, -0.6/1.8};
        assertArrayEquals(wave.getLeftChannel(), scaleLeft, 0.00001);
        assertArrayEquals(wave.getRightChannel(), scaleRight, 0.00001);
    }

    /**
     * scale(double scalingFactor)
     * Test when the wave is not empty. Check if the wave is normalized to [-1,1].
     * scaling factor is exactly 1.
     */
    @Test
    public void scaleTest2() {
        double[] left = {0.2, -0.7, -0.4, 0.5, 0.3};
        double[] right = {0.2, -0.7, -0.4, 0.5, 0.3};
        SoundWave wave = new ConcreteSoundWave(left, right);
        wave.scale(1.0);
        double[] scaleLeft = {0.2, -0.7, -0.4, 0.5, 0.3};
        double[] scaleRight = {0.2, -0.7, -0.4, 0.5, 0.3};
        assertArrayEquals(wave.getLeftChannel(), scaleLeft, 0.00001);
        assertArrayEquals(wave.getRightChannel(), scaleRight, 0.00001);
    }

    /**
     * scale(double scalingFactor)
     * Test when the wave is not empty. Check if the wave is clipped to [-1,1].
     * scaling factor is less than 1.
     */
    @Test
    public void scaleTest3() {
        double[] left = {0.2, -0.7, -0.4, 0.5, 0.3};
        double[] right = {0.2, -0.7, -0.4, 0.5, 0.3};
        SoundWave wave = new ConcreteSoundWave(left, right);
        wave.scale(0.1);
        double[] scaleLeft = {0.02, -0.07, -0.04, 0.05, 0.03};
        double[] scaleRight = {0.02, -0.07, -0.04, 0.05, 0.03};
        assertArrayEquals(wave.getLeftChannel(), scaleLeft, 0.00001);
        assertArrayEquals(wave.getRightChannel(), scaleRight, 0.00001);
    }
}
