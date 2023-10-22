package cpen221.soundwaves;

import cpen221.soundwaves.soundutils.FilterType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cpen221.soundwaves.soundutils.FilterType.*;

public class ConcreteSoundWave implements SoundWave {
    private double[] leftChannel;
    private double[] rightChannel;
    private static boolean debug;
    public static final double INFINITESIMAL = 0.000000000001;

    // The abstraction function is
    //      AF(r) = sound wave w such that
    //          w.leftChannel = r.leftChannel
    //          w.rightChannel = r.rightChannel

    private void checkRep() {
        assert Arrays.stream(leftChannel).allMatch(i -> i <= 1.0 && i >= -1.0)
                : "leftChannel should only contain values in [-1, 1]";
        assert Arrays.stream(rightChannel).allMatch(i -> i <= 1.0 && i >= -1.0)
                : "rightChannel should only contain values in [-1, 1]";
        assert rightChannel.length == leftChannel.length
                : "rightChannel and leftChannel should have equal samples";
    }

    /**
     * Create an instance of {@code SoundWave} with specified amplitude values for
     * the left and right channel (assuming stereo).
     *
     * @param leftChannel:  array of wave samples for left channel,
     *                      requires that leftChannel only contains values in [-1, +1]
     *                      and leftChannel is same length as RightChannel
     * @param rightChannel: array of wave samples for right channel,
     *                      requires that rightChannel only contains values in [-1, +1]
     *                      and rightChannel is same length as leftChannel
     */
    public ConcreteSoundWave(double[] leftChannel, double[] rightChannel) {
        if (leftChannel.length != rightChannel.length) {
            throw new IllegalArgumentException("Channels need to be same length!");
        }
        this.rightChannel = rightChannel;
        this.leftChannel = leftChannel;

        if (debug) {
            checkRep();
        }
    }

    /**
     * Gets left channel of sound wave.
     *
     * @return a copy of the sound wave's left channel
     */
    @Override
    public double[] getLeftChannel() {
        return leftChannel.clone();
    }

    /**
     * Gets right channel of sound wave.
     *
     * @return a copy of the sound wave's right channel
     */
    @Override
    public double[] getRightChannel() {
        return rightChannel.clone();
    }

    /**
     * Gets duration of sound wave in seconds.
     *
     * @return duration of sound wave in seconds where sampling rate is 44,100 samples per second
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
     * @param lchannel: samples to append to left channel,
     *                  requires that lchannel is equal in length to rchannel,
     *                  requires that lchannel is not null
     * @param rchannel: samples to append to right channel,
     *                  requires that rchannel is equal in length to lchannel,
     *                  requires that rchannel is not null
     */
    @Override
    public void append(double[] lchannel, double[] rchannel) {
        double[] templ = new double[lchannel.length + leftChannel.length];
        double[] tempr = new double[rchannel.length + rightChannel.length];
        for (int i = 0; i < leftChannel.length; i++) {
            templ[i] = leftChannel[i];
        }
        for (int i = 0; i < lchannel.length; i++) {
            templ[leftChannel.length + i] = lchannel[i];
        }
        for (int i = 0; i < rightChannel.length; i++) {
            tempr[i] = rightChannel[i];
        }
        for (int i = 0; i < rchannel.length; i++) {
            tempr[rightChannel.length + i] = rchannel[i];
        }
        leftChannel = templ;
        rightChannel = tempr;
    }

    /**
     * Appends a sound wave other onto the sound wave.
     *
     * @param other:    sound wave to append on,
     *                  requires other to not be null
     */
    @Override
    public void append(SoundWave other) {
        append(other.getLeftChannel(), other.getRightChannel());
    }

    /**
     * Superimposes a sound wave other to SoundWave.
     *
     * @param other: sound wave to superimpose to SoundWave
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
     * @param i:        index to check,
     *                  requires that i is not null
     * @param waveL:    left channel of wave,
     *                  requires that waveL is not null
     * @param waveR:    right channel of wave,
     *                  requires that waveR is not null
     */
    private static void extendWaveList(int i, List<Double> waveL, List<Double> waveR) {
        if (i >= waveR.size()) {
            waveL.add(0.0);
            waveR.add(0.0);
        }
    }

    /**
     * Gets right channel of {@code ConcreteSoundWave} as a list.
     *
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
     * Gets left channel of {@code ConcreteSoundWave} as a list.
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
     * Checks if a sample is > 1 or < -1 and then normalizes it to be in [-1,1].
     *
     * @param channel:  wave channel to normalize,
     *                  requires channel to be not null
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
     *
     * @return length of right and left channels of SoundWave
     */
    @Override
    public int getChannelSize() {
        return getRightChannel().length;
    }

    /**
     * Adds an echo to the sound wave.
     *
     * @param delta > 0. delta, in seconds, is the time lag between this wave and
     *              the echo wave.
     *              requires that delta is a multiple of 1 / 44100,
     *              requires that delta is not null
     * @param alpha > 0. alpha is the damping factor applied to the echo wave.
     *              requires that alpha is not null
     *
     * @return the ConcreteSoundWave with added echo
     */
    @Override
    public SoundWave addEcho(double delta, double alpha) {
        if (debug) {
            checkRep();
        }

        double[] lChannel = this.getLeftChannel();
        double[] rChannel = this.getRightChannel();

        for (int i = 0; i < getChannelSize(); i++) {
            if (i - (int) (delta * SAMPLES_PER_SECOND) >= 0) {
                lChannel[i] += alpha * lChannel[i - (int) (delta * SAMPLES_PER_SECOND)];
                rChannel[i] += alpha * rChannel[i - (int) (delta * SAMPLES_PER_SECOND)];
            }
        }

        normalizeWave(lChannel);
        normalizeWave(rChannel);

        if (debug) {
            checkRep();
        }

        return new ConcreteSoundWave(lChannel, rChannel);
    }

    /**
     * Scales the {@code SoundWave} by a scalingFactor
     *
     * @param scalingFactor is a value > 0.
     *                      requires that scalingFactor is not null
     */
    @Override
    public void scale(double scalingFactor) {
        if (debug) {
            checkRep();
        }

        if (scalingFactor <= 0) {
            return;
        }

        for (int i = 0; i < getChannelSize(); i++) {
            leftChannel[i] = leftChannel[i] * scalingFactor;
            rightChannel[i] = rightChannel[i] * scalingFactor;
        }

        normalizeWave(leftChannel);
        normalizeWave(rightChannel);

        if (debug) {
            checkRep();
        }
    }

    /**
     * Determines if {@code SoundWave} other occurs in this {@code SoundWave}
     *
     * @param other is the wave to search for in this wave.
     * @return      true if other is in this {@code SoundWave}
     */
    @Override
    public boolean contains(SoundWave other) {
        if (other.getLeftChannel().length > this.leftChannel.length) {
            return false;
        }
        double scale = -1;
        for (int i = 0; i < other.getLeftChannel().length; i++) {
            if (this.leftChannel[i] == 0) {
                if (this.rightChannel[i] == 0) {
                    continue;
                } else {
                    scale = other.getRightChannel()[i] / this.rightChannel[i];
                    break;
                }
            } else {
                scale = other.getLeftChannel()[i] / this.leftChannel[i];
                break;
            }
        }
        if (scale == -1) {
            for (int i = 0; i < other.getLeftChannel().length; i++) {
                if (other.getLeftChannel()[i] != 0) {
                    return false;
                }
            }
            for (int i = 0; i < other.getRightChannel().length; i++) {
                if (other.getRightChannel()[i] != 0) {
                    return false;
                }
            }
            return true;
        }
        for (int i = 0; i < other.getLeftChannel().length; i++) {
            if (this.leftChannel[i] == 0 || this.rightChannel[i] == 0) {
                return false;
            }
            if (scale == 0 && this.leftChannel[i] != 0 || scale == 0 && this.rightChannel[i] != 0) {
                return false;
            }
            if (other.getLeftChannel()[i] / this.leftChannel[i] != scale
                    || other.getRightChannel()[i] / this.rightChannel[i] != scale) {
                return false;
            }
        }
        return true; // change this
    }

    /**
     * Calculates the similarity of the sound wave to sound wave other
     *
     * @param other is not null.
     * @return return the similarity of the sound wave and other
     */
    @Override
    public double similarity(SoundWave other) {
        if (debug) {
            checkRep();
        }

        List<Double> w1R = getRightChannelList();
        List<Double> w1L = getLeftChannelList();
        List<Double> w2R = other.getRightChannelList();
        List<Double> w2L = other.getLeftChannelList();
        double scalingSumL1 = 0.0;
        double scalingSumR1 = 0.0;
        double scalingTermL1;
        double scalingTermR1;
        double scalingSumL2 = 0.0;
        double scalingSumR2 = 0.0;
        double scalingTermL2;
        double scalingTermR2;

        for (int i = 0; i < getChannelSize() || i < other.getChannelSize(); i++) {
            extendWaveList(i, w1L, w1R);
            extendWaveList(i, w2L, w1R);

            if (w1L.get(i) == 0.0 || w2L.get(i) == 0.0) {
                scalingTermL1 = 0.0;
                scalingTermL2 = 0.0;
            } else {
                scalingTermL1 = w1L.get(i) / w2L.get(i);
                scalingTermL2 = w2L.get(i) / w1L.get(i);
            }

            if (w1R.get(i) == 0.0 || w2R.get(i) == 0.0) {
                scalingTermR1 = 0.0;
                scalingTermR2 = 0.0;
            } else {
                scalingTermR1 = w1R.get(i) / w2R.get(i);
                scalingTermR2 = w2R.get(i) / w1R.get(i);
            }

            scalingSumL1 += scalingTermL1;
            scalingSumR1 += scalingTermR1;
            scalingSumL2 += scalingTermL2;
            scalingSumR2 += scalingTermR2;
        }

        double beta1;
        double scalingSum1 = scalingSumR1 + scalingSumL1;
        double beta2;
        double scalingSum2 = scalingSumR2 + scalingSumL2;

        if (scalingSum1 / (w1L.size() + w1R.size()) <= 0.0) {
            beta1 = INFINITESIMAL;
        } else {
            beta1 = scalingSum1 / (w1L.size() + w1R.size());
        }

        if (scalingSum2 / (w2L.size() + w2R.size()) <= 0.0) {
            beta2 = INFINITESIMAL;
        } else {
            beta2 = scalingSum2 / (w2L.size() + w2R.size());
        }

        other.scale(beta1);
        double gamma1 = gamma(this, other);
        this.scale(beta2);
        other.scale(1 / beta1);
        double gamma2 = gamma(other, this);
        this.scale(1 / beta2);

        return (gamma1 + gamma2) / 2.0;
    }

    /**
     * Computes the gamma element in similarity computation
     * g(wave1, wave2) = 1 / (1 + sum_{j=1}^{2} sum_t (w_{1,j}(t) - Bw_{2,j}(t))^2,
     * where B is scaling factor > 0, w_1 = wave1, w_2 = wave2, j = channel.
     *
     * @param wave1:    first {@code SoundWave},
     *                  requires that wave1 is not null
     * @param wave2:    second {@code SoundWave},
     *                  requires that wave2 is not null
     * @return the gamma value g(wave1, wave2)
     */
    private static double gamma(SoundWave wave1, SoundWave wave2) {
        List<Double> w1R = wave1.getRightChannelList();
        List<Double> w1L = wave1.getLeftChannelList();
        List<Double> w2R = wave2.getRightChannelList();
        List<Double> w2L = wave2.getLeftChannelList();
        double sum1 = 0;
        double sum2 = 0;

        for (int i = 0; i < w1L.size() || i < w2L.size(); i++) {
            extendWaveList(i, w1L, w1R);
            extendWaveList(i, w2L, w1R);
            sum1 += Math.pow(w1R.get(i) - w2R.get(i), 2);
            sum2 += Math.pow(w1L.get(i) - w2L.get(i), 2);
        }

        return 1.0 / (1.0 + sum1 + sum2);
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
     * Computes the highest amplitude frequency component
     * of a {@code SoundWave} that is periodic using the discrete Fourier transform.
     * The frequency components of a discrete Fourier transformed sequence will
     * only contain frequency multiples of the frequency resolution (44,100 / N)
     * if N is the number of elements in a sequence.
     *
     * @return  the frequency of the largest value in the fourier transformed sequence,
     *          that is within the Nyquist limit (44,100 / 2)
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
     * Filters the left and right channels of the {@code SoundWave} by type.
     * HIGHPASS: filters all frequencies < the maximum frequency provided
     * BANDPASS: filters all frequencies not in between the two specifies frequencies
     * LOWPASS: filters all frequencies > minimum provided frequency
     *
     * @param type:         type of filter to apply
     *                      requires that type is valid (HIGHPASS, BANDPASS, LOWPASS)
     * @param frequencies:  frequencies to use for filtering,
     *                      requires that frequencies are not null
     * @return              the filtered {@code SoundWave} if valid filter type
     *                      and <= 2 frequencies are provided,
     *                      else returns unchanged {@code SoundWave}
     */
    @Override
    public SoundWave filter(FilterType type, Double... frequencies) {
        if (debug) {
            checkRep();
        }
        if (frequencies.length > 2) {
            System.out.println("Error, too many frequencies");
            return new ConcreteSoundWave(getLeftChannel(), getRightChannel());
        } else if (type != LOWPASS && type != BANDPASS && type != HIGHPASS) {
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
                    } else if ((freqRes * t < SAMPLES_PER_SECOND - filterFreqMax
                            || freqRes * t > SAMPLES_PER_SECOND - filterFreqMin) && freqRes * t > NYQUIST_LIMIT) {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ConcreteSoundWave)) {
            return false;
        }
        ConcreteSoundWave wave = (ConcreteSoundWave) o;

        return Arrays.equals(getRightChannel(), wave.getRightChannel())
                && Arrays.equals(getLeftChannel(), wave.getLeftChannel());
    }

    @Override
    public int hashCode() {
        return getRightChannel().length + getLeftChannel().length;
    }
}
