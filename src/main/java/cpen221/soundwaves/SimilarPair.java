package cpen221.soundwaves;

/**
 *  SimilarPair represents an immutable pair of SoundWaves
 */
public class SimilarPair {
    public final double similarity;
    public final SoundWave wave1;
    public final SoundWave wave2;

    // The abstraction function is
    //      AF(r) = SoundWave pair p such that
    //          p.wave1 = r.wave1
    //          p.wave2 = r.wave2
    //          p.similarity = r.wave1.similarity(r.wave2)

    // The rep invariant is
    //      wave1 does not change
    //      wave2 does not change
    //      similarity does not change

    /**
     * Constructs an instance of SimilarPair
     *
     * @param wave1: first {@code SoundWave} of the pair
     * @param wave2: second {@code SoundWave} of the pair
     */
    public SimilarPair (SoundWave wave1, SoundWave wave2) {
        this.wave1 = new ConcreteSoundWave(wave1.getLeftChannel(), wave1.getRightChannel());
        this.wave2 = new ConcreteSoundWave(wave2.getLeftChannel(), wave2.getRightChannel());
        similarity = wave1.similarity(wave2);
    }
}
