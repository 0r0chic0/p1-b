package cpen221.soundwaves;

import ca.ubc.ece.cpen221.mp1.utils.MP3Player;
import cpen221.soundwaves.soundutils.Audio;
import cpen221.soundwaves.soundutils.AudioFile;
import cpen221.soundwaves.soundutils.SoundWaveChart;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;


import static cpen221.soundwaves.soundutils.FilterType.*;

/**
 * <p>Simple <code>Main</code> class to demonstrate how
 * to play and visualize sound waves.</p>
 *
 * <p>
 *     There are four examples:
 *     <ul>
 *         <li>{@link #examplePlayMP3file()};</li>
 *         <li>{@link #examplePlayWAVfile()};</li>
 *         <li>{@link #examplePlayAndDraw()};</li>
 *         <li>{@link #exampleSineWaveWithChart()}.</li>
 *     </ul>
 * </p>
 */


public class Main {
    private static double[] randomSignal() {
        return new Random().doubles(100).map(x -> -1.0 + (x * 2)).toArray();
    }
    private static double[] randomNoise(double[] original) {
        var rand = new Random();
        return Arrays.stream(original).map(x -> x + rand.nextDouble() * 0.2 - 0.1).toArray();
    }

    public static void main(String[] args) {
        SoundWave test1 = MP3Wave.getInstance("samples/force.mp3");
        System.out.println(test1.getLeftChannel());

        double[] left = {1, 1, 1, 1};
        double[] right = {1, 1, 1, 1};
        double[] left2 = {-0.5, 1, 1, 1};
        double[] right2 = {1, 1, 1, 1};
        double[] left3 = {1, 1, 1, 1};
        double[] right3 = {1, 1, 1, 1};
        double[] left4 = {1, 1, 1, 1};
        double[] right4 = {1, 1, 1, 1};
        double[] left5 = {1, 1, 1, 1};
        double[] right5 = {1, 1, 1, 1};
        double[] left6 = {1, 1, 1, 1};
        double[] right6 = {1, 1, 1, 1};

        SoundWave w1 = new ConcreteSoundWave(left, right);
        SoundWave w2 = new ConcreteSoundWave(left2, right2);
        SoundWave w3 = new ConcreteSoundWave(left2, right2);
        SoundWave w4 = new ConcreteSoundWave(left2, right2);
        SoundWave w5 = new ConcreteSoundWave(left2, right2);
        SoundWave w6 = new ConcreteSoundWave(left2, right2);
        examplePlayMP3file();
        //examplePlayWAVfile();
        //exampleSineWaveWithChart();
        //examplePlayAndDraw();
    }

    private static void examplePlayMP3file() {
        AudioFile af1 = new AudioFile("samples/force.mp3");

        while (!af1.isEmpty()) {
            double[] samples = af1.readNext();
            MP3Player.playWave(samples, samples);
        }
    }

    private static void examplePlayWAVfile() {
        AudioFile af1 = new AudioFile("samples/SnareDrum.wav");

        while (!af1.isEmpty()) {
            double[] samples = af1.readNext();
            Audio.play(samples);
        }
    }

    private static void examplePlayAndDraw() {
        AudioFile af1 = new AudioFile("samples/pearlharbor.mp3");
        SoundWaveChart chart = new SoundWaveChart();
        while (!af1.isEmpty()) {
            double[] samples = af1.readNext();
            chart.updateDrawing(samples);
            Audio.play(samples);
        }
    }

    // To try this method, one should implement SinusoidalWave first
    private static void exampleSineWaveWithChart() {
        ConcreteSoundWave sw = SinusoidalWave.getInstance(6, 0.5, 1, 10);

        SoundWaveChart chart = new SoundWaveChart();
        double[] allSamples = sw.getLeftChannel();

        // Let us play & update the chart on a per-second basis
        int duration = allSamples.length / (SoundWave.SAMPLES_PER_SECOND);
        for (int t = 0; t < duration; t++) {
            double[] samples = Arrays.copyOfRange(allSamples, t * SoundWave.SAMPLES_PER_SECOND, (t + 1) * SoundWave.SAMPLES_PER_SECOND);
            chart.updateDrawing(samples);

            try {
                Thread.sleep(1000); // Can increase if getting Index 0 out of bounds for length 0 exception
            } catch (InterruptedException e) {
                System.err.println("Delay was interrupted...");
            }
//            Audio.play(samples);
        }
    }

}
