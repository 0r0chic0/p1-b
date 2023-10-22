package cpen221.soundwaves;

public class TriangleWave extends ConcreteSoundWave {
    /**
     * A private constructor that creates an instance of {@code ConcreteSoundWave}.
     *
     * @param channel the time series of amplitude values, is not null
     */
    private TriangleWave(double[] channel) {
        super(channel, channel);
    }

    /**
     * Obtain a new {@code TriangleWave} instance.
     *
     * @param freq      the frequency of the wave in Hz > 0
     * @param phase     the phase of the wave in seconds, >= 0
     * @param amplitude the amplitude of the wave, is in (0, 1]
     * @param duration  the duration of the wave in seconds, >= 0
     * @return a {@code TriangleWave} instance with the specified parameters
     */
    public static TriangleWave getInstance(double freq, double phase, double amplitude,
                                           double duration) {
        int samples = (int) (duration * SAMPLES_PER_SECOND);
        double[] channel = new double[samples];
        double angFreq = 2 * Math.PI * freq;
        double time = 0;
        int exponent = 10;
        double factor = (Math.pow(2, 3) / Math.pow(Math.PI, 2));
        double N = Math.pow(2, exponent);


        for (int i = 0; i < samples; i++) {
            double sum = 0;
            for (int j = 0; j < N - 1; j++) {
                int n = (2 * j) + 1;
                int constant = -1;
                int constant2 = -2;
                sum += Math.pow(constant, j) * Math.pow(n, constant2) * Math.sin((angFreq * (time + phase) * n));
            }
            time += SECONDS_PER_SAMPLE;
            channel[i] = amplitude * factor * sum;
        }

        return new TriangleWave(channel);
    }
}
