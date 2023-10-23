package cpen221.soundwaves;

import java.util.*;

public class similarity_test {

    /* ===== TASK 5 ==== */
    //
    // You should write the spec for this method
    public Set<SoundWave> getSimilarSounds(Set<SoundWave> audioDataset,
                                           int numGroups,
                                           SoundWave baselineWave) {


        TreeMap<Double,SoundWave[]>  treeMap = new TreeMap<>(new Comparator<Double>() {

            @Override
            public int compare(Double o1, Double o2) {
                if(o2-o1>0){
                    return 1;
                }else if(o2-o1<0){
                    return -1;
                }
                return 0;
            }
        });
        for(SoundWave sw1:audioDataset){
            for(SoundWave sw2 : audioDataset){
                if(sw1!=sw2){
                    double s = sw1.similarity(sw2);
                    treeMap.put(s,new SoundWave[]{sw1,sw2});
                }
            }
        }

//        for(Double s : treeMap.keySet()){
//            System.out.println(s+","+ System.identityHashCode(treeMap.get(s)[0])+","+ System.identityHashCode(treeMap.get(s)[1]));
//        }

        ArrayList<HashSet<SoundWave>>  list = new ArrayList<>();
        for(int i=0;i<audioDataset.size();i++){
            list.add(new HashSet<>());
        }
        int index = 0;
        for (SoundWave soundWave : audioDataset) {
            list.get(index++).add(soundWave);
        }

//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }

        for (Double sim : treeMap.keySet()) {

            SoundWave[] soundWaves = treeMap.get(sim);
            int sw1Index = getSoundWaveIndex(soundWaves[0],list);
            int sw2Index = getSoundWaveIndex(soundWaves[1],list);

            if(sw1Index!=sw2Index){
                list.get(sw1Index).addAll(list.get(sw2Index));
                list.remove(sw2Index);

                if(list.size()==numGroups){
                    break;
                }
            }
        }

        // System.out.println(list.size());
//       for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
        return list.get(getSoundWaveIndex(baselineWave,list));
    }


    private int getSoundWaveIndex(SoundWave soundWave, ArrayList<HashSet<SoundWave>> list) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).contains(soundWave)){
                return i;
            }
        }
        return -1;

    }


}
