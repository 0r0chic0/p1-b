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
     * @author 0r0chic0
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

        Map<HashSet<SoundWave>, Double> finalpairs = new HashMap<>();
        List<SoundWave> audiodatasetconvert = new ArrayList<>();
        audiodatasetconvert = audioDataset.stream().collect(Collectors.toList());

        for (int j = 0; j < audioDataset.size() - 1; j++) {
            for (int k = j + 1; k < audioDataset.size(); k++) {
                HashSet<SoundWave> pairs = new HashSet<>();
                pairs.add(audiodatasetconvert.get(j));
                pairs.add(audiodatasetconvert.get(k));
                double simValue = new SimilarPair(audiodatasetconvert.get(j), audiodatasetconvert.get(k)).getSimilarity();
                finalpairs.put(pairs, simValue);
            }
        }
        int partitionSize = audioDataset.size();
        while (partitionSize > numGroups) {

            List<HashSet<SoundWave>> finalpairsCopy = new ArrayList<>(finalpairs.keySet());


            finalpairsCopy.sort((o1, o2) -> Double.compare(finalpairs.get(o2), finalpairs.get(o1)));


            HashSet<SoundWave> mp = finalpairsCopy.get(0);

            List<SoundWave> maxPair = mp.stream().collect(Collectors.toList());



            int partition1 = -1;
            int partition2 = -1;
            for (Integer partition : partitionedGroups.keySet()) {
                if (partitionedGroups.get(partition).containsAll(maxPair)) {
                    finalpairsCopy.remove(maxPair);
                    if (partition1 == -1) {
                        partition1 = partition;
                    } else {
                        partition2 = partition;
                        break;
                    }
                }
            }
            int position = 0;
            if (partition1 == -1 && partition2 == -1) {
                for (int position1 = 0; position1 < finalpairs.size() - 1; position1++) {
                    for (int position2 = position1 + 1; position2 < finalpairs.size(); position2++) {
                        int size1 = partitionedGroups.get(position1).size();
                        int size2 = partitionedGroups.get(position2).size();
                        Set<SoundWave> maxPair1 = new HashSet<>();
                        maxPair1.add(maxPair.get(0));
                        Set<SoundWave> maxPair2 = new HashSet<>();
                        maxPair2.add(maxPair.get(1));
                        if (size1 == 1 && size2 == 1) {
                            if (partitionedGroups.get(position1).equals(maxPair1) && partitionedGroups.get(position2).equals(maxPair2) || partitionedGroups.get(position1).equals(maxPair1) && partitionedGroups.get(position2).equals(maxPair2)) {
                                partitionedGroups.get(position1).addAll(partitionedGroups.get(position2));
                                partitionedGroups.remove(position2);

                                finalpairsCopy.remove(maxPair);
                                finalpairs.keySet().remove(maxPair);
                                partitionSize--;
                            }
                        } else {
                            if (partitionedGroups.get(position1).contains(mp) && partitionedGroups.get(position2).contains(mp) || partitionedGroups.get(position1).equals(maxPair.get(1)) && partitionedGroups.get(position2).equals(maxPair.get(0))) {
                                partitionedGroups.get(position1).addAll(partitionedGroups.get(position2));
                                partitionedGroups.remove(position2);

                                // Remove maxPair from partitionsCopy
                                finalpairsCopy.remove(maxPair);
                                finalpairs.keySet().remove(maxPair);
                                partitionSize--;
                            }
                        }
                    }
                }
                for (HashSet<SoundWave> partition : partitionedGroups.values()) {
                    if (partition.contains(baselineWave)) {
                        return partition;
                    }
                }

            }
        }return new HashSet<>();
    }
}


