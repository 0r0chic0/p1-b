package cpen221.soundwaves;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task5Tests {

    @Test
    public void testGrouping1() {
        var sounds = new ArrayList<SoundWave>();
        for (var i = 0; i < 5; i++) {
            var l = randomSignal();
            var r = randomSignal();
            sounds.add(new ConcreteSoundWave(l, r));
            sounds.add(new ConcreteSoundWave(randomNoise(l), randomNoise(r)));
            sounds.add(new ConcreteSoundWave(randomNoise(l), randomNoise(r)));
        }

        var set = new HashSet<>(sounds);

        /* Verify the set we get back are the original three we added noise to. */
        var firstSet = (new SoundWaveSimilarity()).getSimilarSounds(set, 5, sounds.get(0));
        assert firstSet != null;
        assertTrue(firstSet.containsAll(sounds.subList(0, 3)));
    }

    @Test
    public void testGrouping2() {
        var sounds = new ArrayList<SoundWave>();
        for (var i = 0; i < 5; i++) {
            var l = randomSignal();
            var r = randomSignal();
            sounds.add(new ConcreteSoundWave(l, r));
        }

        var set = new HashSet<>(sounds);

        var firstSet = (new SoundWaveSimilarity()).getSimilarSounds(set, 5, sounds.get(0));
        assert firstSet != null;
        assertTrue(firstSet.containsAll(sounds.subList(0, 1)));
    }

    @Test
    public void testGrouping3() {
        var sounds = new ArrayList<SoundWave>();
        for (var i = 0; i < 5; i++) {
            var l = randomSignal();
            var r = randomSignal();
            sounds.add(new ConcreteSoundWave(l, r));
        }

        var set = new HashSet<>(sounds);

        var firstSet = (new SoundWaveSimilarity()).getSimilarSounds(set, 1, sounds.get(0));
        assert firstSet != null;
        assertTrue(firstSet.containsAll(sounds.subList(0, 5)));
    }

    /**
     * Generate a random signal of length 100.
     *
     */
    private double[] randomSignal() {
        return new Random().doubles(100).map(x -> -1.0 + (x * 2)).toArray();
    }

    /**
     * Corrupt a signal slightly, adding +/- 0.1 to every sample.
     */
    private double[] randomNoise(double[] original) {
        var rand = new Random();
        return Arrays.stream(original).map(x -> x + rand.nextDouble() * 0.2 - 0.1).toArray();
    }
}
