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
     * @param phase     the phase of the wave, >= 0
     * @param amplitude the amplitude of the wave, is in (0, 1]
     * @param duration  the duration of the wave, >= 0
     * @return a {@code SinusoidalWave} instance with the specified parameters
     */
    public static SinusoidalWave getInstance(double freq, double phase, double amplitude,
                                             double duration) {
        // TODO: Implement this method
        // Some code has been completed to give you a sense of what to do
        double[] channel = null;
        return new SinusoidalWave(channel);
    }

}
