package cpen221.soundwaves;

/**
 *  SimilarPair represents an immutable pair of SoundWaves
 */
public class SimilarPair {
    private final double similarity;
    private final SoundWave wave1;
    private final SoundWave wave2;

    // The abstraction function is
    //      AF(r) = SoundWave pair p such that
    //          p.wave1 = r.wave1
    //          p.wave2 = r.wave2
    //          p.similarity = r.wave1.similarity(r.wave2)

    // The rep invariant is
    //      wave1 is the same SoundWave as initialized
    //      wave2 is the same SoundWave as initialized
    //      similarity is the same value as initialized

    /**
     * Constructs an instance of SimilarPair
     *
     * @param wave1: first {@code SoundWave} of the pair
     * @param wave2: second {@code SoundWave} of the pair
     */
    public SimilarPair(SoundWave wave1, SoundWave wave2) {
        this.wave1 = new ConcreteSoundWave(wave1.getLeftChannel(), wave1.getRightChannel());
        this.wave2 = new ConcreteSoundWave(wave2.getLeftChannel(), wave2.getRightChannel());
        similarity = wave1.similarity(wave2);
    }

    public double getSimilarity() {
        return similarity;
    }

    public SoundWave getWave1() {
        return new ConcreteSoundWave(wave1.getLeftChannel(), wave1.getRightChannel());
    }

    public SoundWave getWave2() {
        return new ConcreteSoundWave(wave2.getLeftChannel(), wave2.getRightChannel());
    }
}
