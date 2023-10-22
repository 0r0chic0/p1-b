package cpen221.soundwaves;

public class SinusoidalWave extends ConcreteSoundWave {
    /**
     * A private constructor
     *
     * @param channel the time series of amplitude values, is not null
     */
    private SinusoidalWave(double[] channel) {
        super(channel, channel);
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
            channel[i] = amplitude * Math.sin(angFreq * time + (phase * angFreq));
        }

        return new SinusoidalWave(channel);
    }
}
