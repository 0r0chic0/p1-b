package cpen221.soundwaves;

import ca.ubc.ece.cpen221.mp1.utils.MP3Player;
import cpen221.soundwaves.soundutils.AudioFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MP3Wave extends ConcreteSoundWave {
    /**
     * A private constructor that creates an instance of {@code ConcreteSoundWave}.
     *
     * @param channel the time series of amplitude values, is not null
     */
    private MP3Wave(double[] channel) {
        super(channel, channel);
    }


    /**
     * Obtain a new {@code MP3Wave} instance.
     *
     * @param filename: file name or file path of mp3 file,
     *                  requires filename have .mp3 at the end,
     *                  and be not null
     * @return a {@code MP3Wave} instance from provided mp3 file
     * @throws IllegalArgumentException if file denoted from filename does not exist
     */
    public static MP3Wave getInstance(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist!");
        }

        AudioFile af1 = new AudioFile(filename);
        List<Double> channelList = new ArrayList<>();

        while (!af1.isEmpty()) {
            double[] samples = af1.readNext();
            for (double sample : samples) {
                channelList.add(sample);
            }
        }

        double[] channel = new double[channelList.size()];
        for (int i = 0; i < channelList.size(); i++) {
            channel[i] = channelList.get(i);
        }

        return new MP3Wave(channel);
    }
}
