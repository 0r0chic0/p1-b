package cpen221.soundwaves;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SinusoidalWave extends ConcreteSoundWave {
    public static final double SECONDS_PER_SAMPLE = 1.0 / SAMPLES_PER_SECOND;
    private boolean debug = true;

    private void checkRep() {
        assert Arrays.stream(getLeftChannel()).allMatch(i -> i <= 1 && i >= -1): "leftChannel should only contain values in [-1, 1]";
        assert Arrays.stream(getRightChannel()).allMatch(i -> i <= 1 && i >= -1): "rightChannel should only contain values in [-1, 1]";
        assert Arrays.equals(getRightChannel(), getLeftChannel()) : "rightChannel and leftChannel should be the same reference";
        assert getRightChannel().length == getLeftChannel().length: "rightChannel and leftChannel should have equal samples";
    }

    // The abstraction function is
    //      AF(r) = sinusoidal sound wave w such that
    //

    /**
     * A private constructor
     *
     * @param channel the time series of amplitude values, is not null
     */
    private SinusoidalWave(double[] channel) {
        super(channel, channel);
        checkRep();
    }

    /**
     * Obtain a new {@code SinusoidalWave} instance.
     *
     * @param freq      the frequency of the wave, > 0
     * @param phase     the phase of the wave in seconds, >= 0
     * @param amplitude the amplitude of the wave, is in (0, 1]
     * @param duration  the duration of the wave in seconds, >= 0
     * @return a {@code SinusoidalWave} instance with the specified parameters
     */
    public static SinusoidalWave getInstance(double freq, double phase, double amplitude,
                                             double duration) {
        int samples = (int) (duration * SAMPLES_PER_SECOND);
        double[] channel = new double[samples];
        double angFreq = 2 * Math.PI * freq;
        double time = 0;

        for (int i = 0; i < samples; time += SECONDS_PER_SAMPLE, i++) {
            channel[i] = amplitude * Math.sin(angFreq * time + phase);
        }

        return new SinusoidalWave(channel);
    }

}
