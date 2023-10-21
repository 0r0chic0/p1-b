package cpen221.soundwaves;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            channel[i] = amplitude * Math.sin(angFreq * time + phase);
        }

        return new SinusoidalWave(channel);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ConcreteSoundWave)) {
            return false;
        }
        ConcreteSoundWave wave = (ConcreteSoundWave) o;

        return Arrays.equals(getRightChannel(), wave.getRightChannel()) && Arrays.equals(getLeftChannel(), wave.getLeftChannel());
    }

    @Override
    public int hashCode() {
        return getRightChannel().length + getLeftChannel().length;
    }

}
