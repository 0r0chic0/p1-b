package cpen221.soundwaves;

public class DFT {
    /**
     * Performs the discrete Fourier transform on a sequence of samples.
     * Frequency resolution of terms is 44100 / N
     * Any frequency above the Nyquist limit can be ignored (44100 / 2)
     *
     * @param channel:  channel to perform DFT on
     * @return sequence transformed by DFT
     */
    public static ComplexNumber[] performDFT(double[] channel) {
        int N = channel.length;
        ComplexNumber[] transformedSequence = new ComplexNumber[N];
        for (int k = 0; k < N; k++) {
            ComplexNumber sumTerm = new ComplexNumber(0,0);
            for (int t = 0; t < N; t++) {
                sumTerm.add(channel[t] * Math.cos(2 * Math.PI * k * t / N),channel[t] * -Math.sin(2 * Math.PI * k * t / N));
            }
            transformedSequence[k] = new ComplexNumber(sumTerm.getReal(), sumTerm.getImaginary());
        }
        return transformedSequence;
    }

    public static double[] turnDFTReal(ComplexNumber[] sequence) {
        int N = sequence.length;
        double[] realSequence = new double[N];
        for (int t = 0; t < N; t++) {
            realSequence[t] = ComplexNumber.mod(sequence[t]);
        }
        return realSequence;
    }

    /**
     * Computes inverse discrete Fourier transform on a transformed sequence.
     *
     * @param seqDFT: transformed sequence to be inversed
     * @return the inversed DFT sequence
     */
    public static ComplexNumber[] inverseDFT(ComplexNumber[] seqDFT) {
        int N = seqDFT.length;
        ComplexNumber[] inverseDFT = new ComplexNumber[N];

        for (int t = 0; t < N; t++) {
            ComplexNumber sumTerm = new ComplexNumber(0, 0);
            for (int k = 0; k < N; k++) {
                sumTerm.add(seqDFT[k].multiply(new ComplexNumber(Math.cos(2 * Math.PI * k * t / (double) N), -Math.sin(2 * Math.PI * k * t / (double) N))));
            }
            inverseDFT[t] = sumTerm.multiplyReal(1.0 / (double) N);
        }

        return inverseDFT;
    }

    /**
     * Computes sound wave from waveInfo
     *
     * @param waveInfo: wave information to compute sound wave with
     * @return discrete points of computed wave
     */
    public static double[] turnIntoWave(ComplexNumber[] waveInfo) {
        ComplexNumber[] complexChannel = (DFT.inverseDFT(waveInfo));
        double[] wave = new double[waveInfo.length];

        for (int t = 0; t < waveInfo.length; t++) {
            wave[t] = ComplexNumber.mod(complexChannel[t]) * Math.sin(2 * Math.PI * t + complexChannel[t].getAngle());
        }

        return wave;
    }
}
