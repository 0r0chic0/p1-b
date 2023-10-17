package cpen221.soundwaves;

public class TriangleWave extends ConcreteSoundWave {
    public static final double SECONDS_PER_SAMPLE = 1.0 / SAMPLES_PER_SECOND;
    /**
     * A private constructor
     *
     * @param channel the time series of amplitude values, is not null
     */
    private TriangleWave(double[] channel)
    {
        super(channel, channel);
    }

    /**
     * Obtain a new {@code TriangleWave} instance.
     *
     * @param freq      the frequency of the wave, > 0
     * @param phase     the phase of the wave, >= 0
     * @param amplitude the amplitude of the wave, is in (0, 1]
     * @param duration  the duration of the wave, >= 0
     * @return a {@code TriangleWave} instance with the specified parameters
     */
    public static TriangleWave getInstance(double freq, double phase, double amplitude,
                                           double duration) {
        int samples = (int) (duration * SAMPLES_PER_SECOND);
        double[] channel = new double[samples];
        double angFreq = 2 * Math.PI * freq;
        double time = 0;

        for (int i = 0; i < samples; time += SECONDS_PER_SAMPLE, i++) {
            channel[i] = 2* amplitude * freq * (1/(2*freq) * Math.abs((time + phase) % (1/freq) - (1/(2*freq))));
        }

        return new TriangleWave(channel);
    }

}