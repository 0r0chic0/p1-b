package cpen221.soundwaves;

import cpen221.soundwaves.soundutils.FilterType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Tests {

    /**
     * Make sure a 0-wave has a frequency of 0 Hz.
     */
    @Test
    public void highFreqZeroWave() {
        SoundWave sw = new ConcreteSoundWave(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        assertEquals(sw.highestAmplitudeFrequencyComponent(), 0.0, 0.001);
    }

    @Test
    public void highFrequencyComponentTest1() {
        int numSamples = SoundWave.SAMPLES_PER_SECOND / 10;
        var data = new double[numSamples];

        for (var i = 0; i < numSamples; i++) {
            data[i] = (0.4 * Math.cos(2.0 * Math.PI * i * 880 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.5 * Math.cos(2.0 * Math.PI * i * 440 / SoundWave.SAMPLES_PER_SECOND));
        }

        SoundWave sw = new ConcreteSoundWave(data, data);

        assertEquals(440, sw.highestAmplitudeFrequencyComponent(), 10);
    }

    @Test
    public void highFrequencyComponentTest2() {
        int numSamples = SoundWave.SAMPLES_PER_SECOND / 10;
        var data = new double[numSamples];

        for (var i = 0; i < numSamples; i++) {
            data[i] = (0.2 * Math.cos(2.0 * Math.PI * i * 200 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.3 * Math.cos(2.0 * Math.PI * i * 400 / SoundWave.SAMPLES_PER_SECOND));
        }

        SoundWave sw = new ConcreteSoundWave(data, data);

        assertEquals(400, sw.highestAmplitudeFrequencyComponent(), 10);
    }

    @Test
    public void highFrequencyComponentTest3() {
        int numSamples = SoundWave.SAMPLES_PER_SECOND / 10;
        var data = new double[numSamples];

        for (var i = 0; i < numSamples; i++) {
            data[i] = (0.2 * Math.cos(2.0 * Math.PI * i * 200 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.3 * Math.cos(2.0 * Math.PI * i * 400 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.7 * Math.cos(2.0 * Math.PI * i * 10 / SoundWave.SAMPLES_PER_SECOND));
        }

        SoundWave sw = new ConcreteSoundWave(data, data);

        assertEquals(10, sw.highestAmplitudeFrequencyComponent(), 10);
    }

    @Test
    public void lowPassFilter() {
        int numSamples = SoundWave.SAMPLES_PER_SECOND / 10;
        var data1 = new double[numSamples];
        var data2 = new double[numSamples];

        for (var i = 0; i < numSamples; i++) {
            data1[i] = (0.2 * Math.cos(2.0 * Math.PI * i * 200 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.3 * Math.cos(2.0 * Math.PI * i * 400 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.7 * Math.cos(2.0 * Math.PI * i * 10 / SoundWave.SAMPLES_PER_SECOND));
            data2[i] = (0.2 * Math.cos(2.0 * Math.PI * i * 200 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.7 * Math.cos(2.0 * Math.PI * i * 10 / SoundWave.SAMPLES_PER_SECOND));
        }

        SoundWave sw1 = new ConcreteSoundWave(data1, data1);
        SoundWave sw2 = new ConcreteSoundWave(data2, data2);

        assertTrue(sw2.contains(sw1.filter(FilterType.LOWPASS, 300.0)));
    }

    @Test
    public void highPassFilter() {
        int numSamples = SoundWave.SAMPLES_PER_SECOND / 10;
        var data1 = new double[numSamples];
        var data2 = new double[numSamples];

        for (var i = 0; i < numSamples; i++) {
            data1[i] = (0.2 * Math.cos(2.0 * Math.PI * i * 200 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.3 * Math.cos(2.0 * Math.PI * i * 400 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.7 * Math.cos(2.0 * Math.PI * i * 10 / SoundWave.SAMPLES_PER_SECOND));
            data2[i] = (0.2 * Math.cos(2.0 * Math.PI * i * 200 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.3 * Math.cos(2.0 * Math.PI * i * 400/ SoundWave.SAMPLES_PER_SECOND));
        }

        SoundWave sw1 = new ConcreteSoundWave(data1, data1);
        SoundWave sw2 = new ConcreteSoundWave(data2, data2);

        assertTrue(sw2.contains(sw1.filter(FilterType.HIGHPASS, 30.0)));
    }

    @Test
    public void bandPassFilter() {
        int numSamples = SoundWave.SAMPLES_PER_SECOND / 10;
        var data1 = new double[numSamples];
        var data2 = new double[numSamples];

        for (var i = 0; i < numSamples; i++) {
            data1[i] = (0.2 * Math.cos(2.0 * Math.PI * i * 200 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.3 * Math.cos(2.0 * Math.PI * i * 400 / SoundWave.SAMPLES_PER_SECOND)
                    + 0.7 * Math.cos(2.0 * Math.PI * i * 10 / SoundWave.SAMPLES_PER_SECOND));
            data2[i] = (0.2 * Math.cos(2.0 * Math.PI * i * 200 / SoundWave.SAMPLES_PER_SECOND));
        }

        SoundWave sw1 = new ConcreteSoundWave(data1, data1);
        SoundWave sw2 = new ConcreteSoundWave(data2, data2);

        assertTrue(sw2.contains(sw1.filter(FilterType.BANDPASS, 30.0, 300.0)));
    }

}
