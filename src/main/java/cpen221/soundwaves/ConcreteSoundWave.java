package cpen221.soundwaves;

import cpen221.soundwaves.soundutils.FilterType;

import java.util.Arrays;
import java.util.Collections;

public class ConcreteSoundWave implements SoundWave {
    private double[] leftChannel;
    private double[] rightChannel;
    private static boolean debug = true;

    // The abstraction function is
    //      AF(r) = sound wave w such that
    //          w.leftChannel = r.leftChannel
    //          w.rightChannel = r.rightChannel

    private void checkRep() {
        assert Arrays.stream(leftChannel).allMatch(i -> i <= 1 && i >= -1): "leftChannel should only contain values in [-1, 1]";
        assert Arrays.stream(rightChannel).allMatch(i -> i <= 1 && i >= -1): "rightChannel should only contain values in [-1, 1]";
        assert rightChannel.length == leftChannel.length: "rightChannel and leftChannel should have equal samples";
    }

    /**
     * Create an instance of {@code SoundWave} with specified amplitude values for
     * the left and right channel (assuming stereo).
     *
     * @param leftChannel:  array of wave samples for left channel
     *                      requires that leftChannel only contains values in [-1, +1]
     *                      and leftChannel is same length as RightChannel
     * @param rightChannel: array of wave samples for right channel
     *                      requires that rightChannel only contains values in [-1, +1]
     *                      and rightChannel is same length as leftChannel
     */
    public ConcreteSoundWave(double[] leftChannel, double[] rightChannel) {
        this.rightChannel = rightChannel;
        this.leftChannel = leftChannel;

        if (debug) {
            checkRep();
        }
    }

    @Override
    public double[] getLeftChannel() {
        if (debug) {
            checkRep();
        }
        return leftChannel.clone();
    }

    @Override
    public double[] getRightChannel() {
        if (debug) {
            checkRep();
        }
        return rightChannel.clone();
    }

    /**
     * Gets duration of sound wave
     * @return duration of sound wave assuming sampling rate is 44,100 samples per second
     */
    @Override
    public double duration() {
        if (debug) {
            checkRep();
        }
        return (double) rightChannel.length / (double) SAMPLES_PER_SECOND;
    }

    @Override
    public void append(double[] lchannel, double[] rchannel) {
        // TODO: Implement this method.
    }

    @Override
    public void append(SoundWave other) {
        // TODO: Implement this method.
    }

    @Override
    public SoundWave add(SoundWave other) {
        // TODO: Implement this method
        return null; // change this
    }

    @Override
    public SoundWave addEcho(double delta, double alpha) {
        // TODO: Implement this method
        return null; // change this
    }

    @Override
    public void scale(double scalingFactor) {
        // TODO: Implement this method.
    }

    @Override
    public boolean contains(SoundWave other) {
        // TODO: Implement this method
        return true; // change this
    }

    @Override
    public double similarity(SoundWave other) {
        // TODO: Implement this method
        return -1;
    }

    /**
     * Generate a fractal waveform from the given sound wave
     * (this is for fun!)
     * @param period the fractal periodicity, >= 1
     * @return a fractalized ConcreteSoundWave
     */
    public ConcreteSoundWave fractalize(int period) {
        final long SCALE = period * SAMPLES_PER_SECOND;
        double[] lchannel = this.getLeftChannel();
        double[] rchannel = this.getRightChannel();

        double[] newLChannel = Arrays.stream(lchannel).mapToLong(t -> (int)(SCALE * t))
                .map(t -> t & (t >>> 3) % SCALE)
                .mapToDouble(t -> (double) t / SCALE)
                .toArray();
        double[] newRChannel = Arrays.stream(rchannel).mapToLong(t -> (long)(SCALE * t))
                .map(t -> t & (t >>> 3) % SCALE)
                .mapToDouble(t -> (double) t / SCALE)
                .toArray();
        // One could also try:
        // t & (t >> 3) & (t >> 8) % SCALE;
        return new ConcreteSoundWave(newLChannel, newRChannel);
    }

    @Override
    public double highestAmplitudeFrequencyComponent() {
        // TODO: Implement this method
        return -1; // change this
    }

    @Override
    public SoundWave filter(FilterType type, Double... frequencies) {
        // TODO: Implement this method correctly.
        // The provided code illustrates -- rather simply -- two ideas:
        // (1) the use of varargs, and
        // (2) one use of enums.

        if (frequencies.length > 2) {
            System.out.println("Error");
        }
        else {
            System.out.println(frequencies[0]);
        }

        switch (type) {
            case LOWPASS: break;
            case BANDPASS: break;
            case HIGHPASS: break;
            default:
        }
        return null;
    }
}
