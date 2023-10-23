package cpen221.soundwaves;

import java.util.*;

public class SoundWaveSimilarityAlternative {

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

        int maxGroup = 0;
        for (SoundWave signal : audioDataset) {
            partitionedGroups.put(maxGroup, new HashSet<>());
            partitionedGroups.get(maxGroup).add(signal);
            maxGroup++;
        }

        if (numGroups == audioDataset.size()) {
            for (Integer group : partitionedGroups.keySet()) {
                if (partitionedGroups.get(group).contains(baselineWave)) {
                    return partitionedGroups.get(group);
                }
            }
        }

        // Initialize similarity keys mapped to list of pairs
        List<SoundWave> audioDatalist = audioDataset.stream().toList();
        Map<Double, List<SimilarPair>> pairs = new HashMap<>();
        List<Double> similarityKeys = new ArrayList<>();

        for (int i = 0; i < audioDatalist.size() - 1; i++) {
            for (int j = i + 1; j < audioDatalist.size(); j++) {
                double currentSimilarity = audioDatalist.get(i).similarity(audioDatalist.get(j));
                similarityKeys.add(currentSimilarity);
                if (pairs.containsKey(currentSimilarity)) {
                    pairs.put(currentSimilarity, new ArrayList<>());
                }
                pairs.get(currentSimilarity).add(new SimilarPair(audioDatalist.get(i), audioDatalist.get(j)));
            }
        }

        // Sort from highest to lowest similarity key
        Collections.sort(similarityKeys);
        Collections.reverse(similarityKeys);

        while (numGroups < partitionedGroups.size()) {
            double maxSim = similarityKeys.get(0);
            SimilarPair topPair = pairs.get(maxSim).get(0);
            boolean w1In;
            boolean w2In;
            boolean sameGroup = false;
            int w1Pos = -1;
            int w2Pos = -1;
            for (int i = 0; i < maxGroup; i++) {
                w1In = false;
                w2In = false;
                if (partitionedGroups.containsKey(i)) {
                    for (SoundWave signal : partitionedGroups.get(i)) {
                        if (signal.equals(topPair.getWave1())) {
                            w1In = true;
                            w1Pos = i;
                        }
                        if (signal.equals(topPair.getWave2())) {
                            w2In = true;
                            w2Pos = i;
                        }
                        if (w1In && w2In) {
                            similarityKeys.remove(0);
                            if (pairs.get(maxSim).isEmpty()) {
                                pairs.remove(maxSim);
                            } else {
                                pairs.get(maxSim).remove(0);
                            }
                            sameGroup = true;
                            break;
                        }
                    }
                    if (sameGroup) {
                        break;
                    }
                }
            }
            if (!sameGroup) {
                similarityKeys.remove(0);
                for (SoundWave signal : partitionedGroups.get(w2Pos)) {
                    partitionedGroups.get(w1Pos).add(signal);
                }
                partitionedGroups.remove(w2Pos);
            }
        }

        for (int i = 0; i < maxGroup; i++) {
            if (partitionedGroups.containsKey(i)) {
                for (SoundWave signal : partitionedGroups.get(i)) {
                    if (signal.equals(baselineWave)) {
                        return partitionedGroups.get(i);
                    }
                }
            }
        }

        return new HashSet<>();
    }
}
