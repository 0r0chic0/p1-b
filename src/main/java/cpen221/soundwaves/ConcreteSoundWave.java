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

    /**
     * Appends wave samples to left and right channels of the sound wave.
     *
     * @param lchannel: samples to append to left channel
     *                  requires that lchannel is equal in length to rchannel
     * @param rchannel: samples to append to right channel
     *                  requires that rchannel is equal in length to lchannel
     */
    @Override
    public void append(double[] lchannel, double[] rchannel) {
        // TODO: Implement this method.
        double[] templ = new double[lchannel.length+leftChannel.length];
        double[] tempr = new double[rchannel.length+rightChannel.length];
        for (int i=0;i<leftChannel.length;i++) {
            templ[i] = leftChannel[i];
        }
        for (int i = 0; i < lchannel.length; i++) {
            templ[leftChannel.length+i] = lchannel[i];
        }
        for (int i=0;i<rightChannel.length;i++) {
            tempr[i] = rightChannel[i];
        }
        for (int i = 0; i < rchannel.length; i++) {
            tempr[rightChannel.length+i] = rchannel[i];
        }
        leftChannel = templ;
        rightChannel = tempr;
    }

    @Override
    public void append(SoundWave other) {
        // TODO: Implement this method.
        append(other.getLeftChannel(),other.getRightChannel());
    }

    /**
     * Superimposes a wave other to SoundWave.
     *
     * @param other: wave to superimpose to SoundWave
     * @return the superimposed wave with elements normalized to [-1,1]
     */
    @Override
    public SoundWave add(SoundWave other) {
        if (debug) {
            checkRep();
        }
        List<Double> newR = new ArrayList<>();
        List<Double> newL = new ArrayList<>();
        List<Double> waveL = getLeftChannelList();
        List<Double> waveR = getRightChannelList();
        List<Double> otherL = other.getRightChannelList();
        List<Double> otherR = other.getLeftChannelList();

        for (int i = 0; i < getChannelSize() || i < other.getChannelSize(); i++) {
            extendWaveList(i, waveL, waveR);
            extendWaveList(i, otherL, otherR);
            double addedSample = waveL.get(i) + otherL.get(i);
            newL.add(i, addedSample);
            addedSample = waveR.get(i) + otherR.get(i);
            newR.add(i, addedSample);
        }

        double[] addedL = new double[newL.size()];
        double[] addedR = new double[newR.size()];

        for (int i = 0; i < newR.size(); i++) {
            addedL[i] = newL.get(i);
            addedR[i] = newR.get(i);
        }

        normalizeWave(addedL);
        normalizeWave(addedR);

        if (debug) {
            checkRep();
        }

        return new ConcreteSoundWave(addedL, addedR);
    }

    /**
     * Extends wave list by adding a 0.0 element to list if index i is greater than channel size.
     *
     * @param i: index to check
     * @param waveL: left channel of wave
     * @param waveR: right channel of wave
     */
    public static void extendWaveList(int i, List<Double> waveL, List<Double> waveR) {
        if (i >= waveR.size()) {
            waveL.add(0.0);
            waveR.add(0.0);
        }
    }

    /**
     * Gets right channel of ConcreteSoundWave as a list.
     *
     * @return right channel as list
     */
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

    /**
     * Gets left channel of ConcreteSoundWave as a list.
     *
     * @return left channel as list
     */
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
     * Checks if a sample it is too high or low in amplitude and then normalizes it
     *
     * @param channel: wave channel to normalize
     * @author dzhen2023
     */
    private static void normalizeWave(double[] channel) {
        double loudest = 1.0;
        for (double sample: channel) {
            if (Math.abs(sample) > loudest) {
                loudest = Math.abs(sample);
            }
        }
        if (loudest > 1.0) {
            for (int i = 0; i < channel.length; i++) {
                channel[i] = channel[i] / loudest;
            }
        }
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

    /**
     * Calculates the similarity of the ConcreteSoundWave to wave other
     *
     * @param other is not null.
     * @return return the similarity of the wave and other
     */
    @Override
    public double similarity(SoundWave other) {
        if (debug) {
            checkRep();
        }
        double beta = 0;
        other.scale(beta);
        List<Double> w1R = getRightChannelList();
        List<Double> w1L = getLeftChannelList();
        List<Double> w2R = other.getRightChannelList();
        List<Double> w2L = other.getLeftChannelList();

        double gamma1 = gamma(w1R, w1L, w2R, w2L, other);
        double gamma2 = gamma(w2R, w2L, w1R, w1L, other);

        return (gamma1 + gamma2) / 2;
    }

    private double gamma(List<Double> w1R, List<Double> w1L, List<Double> w2R, List<Double> w2L, SoundWave other) {
        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < w1L.size() || i < w2L.size(); i++) {
            extendWaveList(i, w1L, w1R);
            extendWaveList(i, w2L, w1R);
            sum1 += Math.pow(w1R.get(i) - w2R.get(i),2);
            sum2 += Math.pow(w1L.get(i) - w2L.get(i),2);
        }

        if (isSingleChannel() && other.isSingleChannel()) {
            return 1 / (1 + sum1);
        }

        return 1 / (1 + sum1 + sum2);
    }

    /**
     * Checks if sound wave has the same channel in left and right
     *
     * @return true if left and right channels have same values
     */
    public boolean isSingleChannel() {
        for (int i = 0; i < getChannelSize(); i++) {
            if (getLeftChannel()[0] != getRightChannel()[0]) {
                return false;
            }
        }
        return true;
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
