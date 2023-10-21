package cpen221.soundwaves;

import cpen221.soundwaves.soundutils.FilterType;
import jdk.jfr.Frequency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cpen221.soundwaves.soundutils.FilterType.*;

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
        if (debug) {
            checkRep();
        }
        return getRightChannel().length;
    }

    @Override
    public SoundWave addEcho(double delta, double alpha) {
        double[] leftChannel = this.getLeftChannel();
        double[] rightChannel = this.getRightChannel();



        for (int i = (int) delta; i < leftChannel.length; i++) {

            leftChannel[i] += alpha * leftChannel[i - (int) delta];
            rightChannel[i] += alpha * rightChannel[i - (int) delta];
        }
        normalizeWave(leftChannel);
        normalizeWave(rightChannel);

        return new ConcreteSoundWave(leftChannel, rightChannel);
    }

    @Override
    public void scale(double scalingFactor) {
        if(scalingFactor <= 0)
        {
            throw new IllegalArgumentException();
        }
       for(int i = 0; i < getChannelSize() ; i++)
       {
           leftChannel[i] = leftChannel[i] * scalingFactor;
           rightChannel[i] = rightChannel[i] * scalingFactor;
       }

       for(int j = 0 ; j < getChannelSize() ; j++)
       {
           if(leftChannel[j] > 1 || leftChannel[j] < -1)
           {
               normalizeWave(leftChannel);
           }
           if(rightChannel[j] > 1 || rightChannel[j] < -1)
           {
               normalizeWave(rightChannel);

           }
       }

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
        if (debug) {
            checkRep();
        }
        for (int i = 0; i < getChannelSize(); i++) {
            if (getLeftChannel()[0] != getRightChannel()[0]) {
                return false;
            }
        }
        if (debug) {
            checkRep();
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


    /**
     * Computes the highest amplitude frequency component of both channels.
     * Ignores any values above the Nyquist limit.
     * The frequency components of a discrete Fourier transformed sequence will
     * only contain frequency multiples of the frequency resolution (44,100 / N)
     * if N is the number of elements in a sequence.
     * So, less samples results in less frequency accuracy.
     *
     * @return the frequency of the highest value in the fourier transformed sequence
     */
    @Override
    public double highestAmplitudeFrequencyComponent() {
        if (debug) {
            checkRep();
        }
        double freqRes = SAMPLES_PER_SECOND / (double) getChannelSize();
        double[] rightDFT = DFT.turnDFTReal(DFT.performDFT(getRightChannel()));
        double[] leftDFT = DFT.turnDFTReal(DFT.performDFT(getLeftChannel()));
        double[] combinedDFT = new double[rightDFT.length];

        for (int i = 0; i < rightDFT.length; i++) {
            combinedDFT[i] = leftDFT[i] + rightDFT[i];
        }

        double maxFreqComp = 0;
        double maxFreq = 0;
        for (int t = 0; t < combinedDFT.length; t++) {
            if (t * freqRes > NYQUIST_LIMIT) {
                break;
            }
            if (maxFreqComp < combinedDFT[t]) {
                maxFreqComp = combinedDFT[t];
                maxFreq = t * freqRes;
            }
        }
        if (debug) {
            checkRep();
        }
        return maxFreq;
    }

    /**
     * Filters the left and right channels of the ConcreteSoundWave, by type:
     * HIGHPASS: filters all frequencies < the maximum frequency provided
     * BANDPASS: filters all frequencies not in between the two specifies frequencies
     * LOWPASS: filters all frequencies > minimum provided frequency
     *
     * @param type: type of filter to apply
     *              requires that type is valid (HIGHPASS, BANDPASS, LOWPASS)
     * @param frequencies: frequencies to use for filtering
     * @return  the filtered ConcreteSoundWave if valid filter type,
     *          else returns unchanged ConcreteSoundWave
     */
    @Override
    public SoundWave filter(FilterType type, Double... frequencies) {
        if (debug) {
            checkRep();
        }
        if (frequencies.length > 2) {
            System.out.println("Error, too many frequencies");
            return new ConcreteSoundWave(getLeftChannel(), getRightChannel());
        }
        else if (type != LOWPASS && type != BANDPASS && type != HIGHPASS){
            System.out.println("Error, invalid filter");
            return new ConcreteSoundWave(getLeftChannel(), getRightChannel());
        }

        double filterFreqMin;
        double filterFreqMax;

        if (frequencies.length == 1) {
            filterFreqMax = frequencies[0];
            filterFreqMin = frequencies[0];
        } else {
            filterFreqMax = Math.max(frequencies[0], frequencies[1]);
            filterFreqMin = Math.min(frequencies[0], frequencies[1]);
        }

        ComplexNumber[] rightDFT = DFT.performDFT(getRightChannel());
        ComplexNumber[] leftDFT = DFT.performDFT(getLeftChannel());
        double N = rightDFT.length;
        double freqRes = SAMPLES_PER_SECOND / N;

        switch (type) {
            case LOWPASS:
                for (int t = 0; t < (int) N; t++) {
                    if (freqRes * t > filterFreqMin && freqRes * t < NYQUIST_LIMIT) {
                        rightDFT[t] = new ComplexNumber(0.0, 0.0);
                        leftDFT[t] = new ComplexNumber(0.0, 0.0);
                    } else if (freqRes * t < SAMPLES_PER_SECOND - filterFreqMin && freqRes * t > NYQUIST_LIMIT) {
                        rightDFT[t] = new ComplexNumber(0.0, 0.0);
                        leftDFT[t] = new ComplexNumber(0.0, 0.0);
                    }
                }
                break;
            case BANDPASS:
                for (int t = 0; t < (int) N; t++) {
                if (freqRes * t > filterFreqMax || freqRes * t < filterFreqMin && freqRes * t < NYQUIST_LIMIT) {
                    rightDFT[t] = new ComplexNumber(0.0, 0.0);
                    leftDFT[t] = new ComplexNumber(0.0, 0.0);
                } else if ((freqRes * t < SAMPLES_PER_SECOND - filterFreqMax || freqRes * t > SAMPLES_PER_SECOND - filterFreqMin) && freqRes * t > NYQUIST_LIMIT) {
                    rightDFT[t] = new ComplexNumber(0.0, 0.0);
                    leftDFT[t] = new ComplexNumber(0.0, 0.0);
                }
            }
                break;
            case HIGHPASS:
                for (int t = 0; t < (int) N; t++) {
                if (freqRes * t < filterFreqMax && freqRes * t < NYQUIST_LIMIT) {
                    rightDFT[t] = new ComplexNumber(0.0, 0.0);
                    leftDFT[t] = new ComplexNumber(0.0, 0.0);
                } else if (freqRes * t > SAMPLES_PER_SECOND - filterFreqMax && freqRes * t > NYQUIST_LIMIT) {
                    rightDFT[t] = new ComplexNumber(0.0, 0.0);
                    leftDFT[t] = new ComplexNumber(0.0, 0.0);
                }
            }
                break;
            default: break;
        }

        double[] leftFiltered = DFT.turnIntoWave(leftDFT);
        double[] rightFiltered = DFT.turnIntoWave(rightDFT);

        if (debug) {
            checkRep();
        }

        return new ConcreteSoundWave(leftFiltered, rightFiltered);
    }
}
