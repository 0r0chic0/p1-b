package cpen221.soundwaves;

import java.util.*;
import java.util.stream.Collectors;

public class SoundWaveSimilarity {

    /* ===== TASK 5 ==== */

    /**
     * Gets the group of {@code SoundWave}s that are more similar to the {@code baselineWave} than
     * other {@code SoundWave}s in {@code audioDataset} partitioned into {@code numGroups} groups.
     *
     * @param audioDataset: group of sound waves to partition,
     *                      requires that audioDataset is not null
     * @param numGroups:    number of partitioned groups
     *                      requires 1 <= numGroups <= {@code audioDataset.size()}
     *                      and numGroups is not null
     * @param baselineWave: soundWave that will be in returned group
     *                      requires that baselineWave is in audioDataset
     *                      and baselineWave is not null
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
            for (Integer group : partitionedGroups.keySet()) {
                if (partitionedGroups.get(group).contains(baselineWave)) {
                    return partitionedGroups.get(group);
                }
            }
        }

        // Step 1: Compute similarity for all pairs of audio clips
        Map<HashSet<SoundWave>, Double> finalpairs = new HashMap<>();
        List<SoundWave> audiodatasetconvert = new ArrayList<>(audioDataset);

        for (int j = 0; j < audioDataset.size(); j++) {
            for (int k = j; k < audioDataset.size(); k++) {
                HashSet<SoundWave> pairs = new HashSet<>();
                pairs.add(audiodatasetconvert.get(j));
                pairs.add(audiodatasetconvert.get(k));
                double simValue = new SimilarPair(audiodatasetconvert.get(j), audiodatasetconvert.get(k)).getSimilarity();
                finalpairs.put(pairs, simValue);
            }
        }

        int partitionSize = audioDataset.size();
        while (partitionSize > numGroups)
        {
            // Create a copy of partitions before sorting
            List<HashSet<SoundWave>> finalpairsCopy = new ArrayList<>(finalpairs.keySet());

            // Sort finalpairs by similarity in descending order
            finalpairsCopy.sort((o1, o2) -> Double.compare(finalpairs.get(o2), finalpairs.get(o1)));

            // Get the entry with the highest similarity
            HashSet<SoundWave> maxPair = finalpairsCopy.get(0);

            // Find the partitions containing the audio clips in the maxPair
            int partition1 = -1;
            int partition2 = -1;
            for (Integer partition : partitionedGroups.keySet())
            {
                if (partitionedGroups.get(partition).containsAll(maxPair))
                {
                    finalpairsCopy.remove(maxPair);
                    if (partition1 == -1) {
                        partition1 = partition;
                    }
                    else
                    {
                        partition2 = partition;
                        break;
                    }
                }
            }
            if (partition1 != -1 && partition2 != -1) {
                if (partitionedGroups.get(partition1).contains(maxPair.iterator().next())
                        && partitionedGroups.get(partition2).contains(maxPair.iterator().next())) {
                    partitionedGroups.get(partition1).addAll(partitionedGroups.get(partition2));
                    partitionedGroups.remove(partition2);

                    // Remove maxPair from partitionsCopy
                    finalpairsCopy.remove(maxPair);
                }
            }
            partitionSize--;
        }
        for (HashSet<SoundWave> partition : partitionedGroups.values()) {
            if (partition.contains(baselineWave)) {
                return partition;
            }
        }

        // returns an empty set if baseline is not found
        return new HashSet<>();
    }
}