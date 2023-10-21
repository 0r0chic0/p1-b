package cpen221.soundwaves;

import java.util.*;

public class SoundWaveSimilarity {

    /* ===== TASK 5 ==== */

    /**
     * Gets the group of {@code SoundWave}s that are more similar to the {@code baselineWave} than
     * other {@code SoundWave}s in {@code audioDataset} partitioned into {@code numGroups} groups.
     *
     * @param audioDataset: group of sound waves to partition
     * @param numGroups: number of partitioned groups
     *                   requires 1 <= numGroups <= {@code audioDataset.size()}
     * @param baselineWave: soundWave that will be in returned group
     *                      requires that baselineWave is in audioDataset
     * @return the group of {@code SoundWave}s that are more similar to baseLineWave
     */
    public Set<SoundWave> getSimilarSounds(Set<SoundWave> audioDataset,
                                           int numGroups,
                                           SoundWave baselineWave) {
        if (numGroups == 1) {
            return audioDataset;
        }

        Map<Integer, HashSet<SoundWave>> partitionedGroups = new HashMap<>();

        int i = 0;
        for (SoundWave signal : audioDataset) {
            partitionedGroups.put(i, new HashSet<>());
            partitionedGroups.get(i).add(signal);
            i++;
        }

        if (numGroups == audioDataset.size()) {
            for (Integer group: partitionedGroups.keySet()) {
                if (partitionedGroups.get(group).contains(baselineWave)) {
                    return partitionedGroups.get(group);
                }
            }
        }

        List<SoundWave> audioList = audioDataset.stream().toList();


        return null;
    }

}
