package cpen221.soundwaves;

import cpen221.soundwaves.soundutils.FilterType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
     * @return duration of sound wave in seconds assuming sampling rate is 44,100 samples per second
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

    /**
     * Superimposes a wave to SoundWave.
     * @param other: wave to superimpose to SoundWave
     * @return the superimposed wave with elements in [-1,1]
     */
    @Override
    public SoundWave add(SoundWave other) {
        if (debug) {
            checkRep();
        }

        List<Double> wave3R = new ArrayList<>();
        List<Double> wave3L = new ArrayList<>();
        List<Double> wave1L = getLeftChannelList();
        List<Double> wave1R = getRightChannelList();
        List<Double> wave2L = other.getRightChannelList();
        List<Double> wave2R = other.getLeftChannelList();

        for (int i = 0; i < getChannelSize() || i < other.getChannelSize(); i++) {
            if (i >= getChannelSize()) {
                wave1L.add(0.0);
                wave1R.add(0.0);
            }
            if (i >= other.getChannelSize()) {
                wave2L.add(0.0);
                wave2R.add(0.0);
            }
            double addedSample = wave1L.get(i) + wave2L.get(i);
            addedSample = capAmplitude(addedSample);
            wave3L.add(i, addedSample);
            addedSample = wave1R.get(i) + wave2R.get(i);
            addedSample = capAmplitude(addedSample);
            wave3R.add(i, addedSample);
        }

        double[] addedL = new double[wave3L.size()];
        double[] addedR = new double[wave3R.size()];

        for (int i = 0; i < wave3R.size(); i++) {
            addedL[i] = wave3L.get(i);
            addedR[i] = wave3R.get(i);
        }

        return new ConcreteSoundWave(addedL, addedR);
    }

    @Override
    public List<Double> getRightChannelList() {
        if (debug) {
            checkRep();
        }
        List<Double> rightChannelList = new ArrayList<>();
        for (int i = 0; i < getChannelSize(); i++) {
            rightChannelList.add(getRightChannel()[i]);
        }
        if (debug) {
            checkRep();
        }
        return rightChannelList;
    }

    @Override
    public List<Double> getLeftChannelList() {
        if (debug) {
            checkRep();
        }
        List<Double> leftChannelList = new ArrayList<>();
        for (int i = 0; i < getChannelSize(); i++) {
            leftChannelList.add(getLeftChannel()[i]);
        }
        if (debug) {
            checkRep();
        }
        return leftChannelList;
    }


    /**
     * Checks if a sample it is too high or low in amplitude and caps it to the boundary
     *
     * @param sample: sample to check
     * @return capped sample if out of boundaries, otherwise the same sample
     * @author dzhen2023
     */
    private static double capAmplitude(double sample) {
        if (sample > 1.0) {
            sample = 1.0;
        } else if (sample < -1.0) {
            sample = -1.0;
        }
        return sample;
    }

    /**
     * Gets size of channels in SoundWave.
     * @return length of right and left channels of SoundWave
     * @author dzhen2023
     */
    @Override
    public int getChannelSize() {
        return getRightChannel().length;
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
