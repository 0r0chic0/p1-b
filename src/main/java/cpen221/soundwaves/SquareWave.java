package cpen221.soundwaves;

import java.util.Arrays;

public class SquareWave extends ConcreteSoundWave {
    public static final double SECONDS_PER_SAMPLE = 1.0 / SAMPLES_PER_SECOND;

    /**
     * A private constructor
     * @param channel the time series of amplitude values, is not null
     */
    private SquareWave(double[] channel) {
        super(channel, channel);
    }

    /**
     * Obtain a new {@code SquareWave} instance.
     *
     * @param freq      the frequency of the wave, > 0
     * @param phase     the phase of the wave in seconds, >= 0
     * @param amplitude the amplitude of the wave, is in (0, 1]
     * @param duration  the duration of the wave in seconds, >= 0
     * @return a {@code SquareWave} instance with the specified parameters
     */
    public static SquareWave getInstance(double freq, double phase, double amplitude, double duration) {
        int samples = (int) (duration * SAMPLES_PER_SECOND);
        double[] channel = new double[samples];
        double angFreq = 2 * Math.PI * freq;
        double phaseShift = angFreq * phase;
        double angularIncrement = angFreq * SECONDS_PER_SAMPLE;

        for (int i = 0; i < samples; i++) {
            double time = i * SECONDS_PER_SAMPLE;
            double value = Math.sin(angularIncrement * time + phaseShift);
            channel[i] = amplitude * (value > 0 ? 1 : -1);
        }

        return new SquareWave(channel);
    }
}
