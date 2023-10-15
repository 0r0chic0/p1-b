package cpen221.soundwaves;

import cpen221.soundwaves.soundutils.FilterType;

import java.util.Arrays;

public class ConcreteSoundWave implements SoundWave {

    /**
     * Create an instance of {@code SoundWave} with specified amplitude values for
     * the left and right channel (assuming stereo).
     *
     * @param leftChannel
     * @param rightChannel
     */
    public ConcreteSoundWave(double[] leftChannel, double[] rightChannel) {
        // TODO: Implement this method
    }

    @Override
    public double[] getLeftChannel() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public double[] getRightChannel() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public double duration() {
        // TODO: Implement this method
        return -1.0;
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
