package cpen221.soundwaves;

public class DFT {
    /**
     * Performs the discrete Fourier transform on a sequence of real samples.
     * Converts sequence of samples from time domain to frequency domain.
     * The transform will only return a meaningful sequence if provided channel is periodic.
     *
     * @param channel to perform DFT on
     *                requires that channel is not null
     * @return sequence transformed by DFT that contains same number of elements as provided channel
     */
    public static ComplexNumber[] performDFT(double[] channel) {
        int N = channel.length;
        ComplexNumber[] transformedSequence = new ComplexNumber[N];
        for (int k = 0; k < N; k++) {
            ComplexNumber sumTerm = new ComplexNumber(0, 0);
            for (int t = 0; t < N; t++) {
                sumTerm.add(channel[t] * Math.cos(2 * Math.PI * k * t / N),
                        channel[t] * -Math.sin(2 * Math.PI * k * t / N));
            }
            transformedSequence[k] = new ComplexNumber(sumTerm.getReal(), sumTerm.getImaginary());
        }
        return transformedSequence;
    }

    /**
     * Computes modulus of all {@code ComplexNumbers}in sequence.
     *
     * @param sequence: sequence to be computed on,
     *                  requires that sequence is not null
     * @return  the sequence of moduli of original sequence
     *          that contains the same number of elements as provided sequence
     */
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
     * Inverse transformed sequence will be a sequence of {@code ComplexNumber}s
     * Such that the modulus of an element represents the amplitude at that time
     * And the angle represents the phase at that point.
     *
     * @param seqDFT: transformed sequence to perform IDFT on
     * @return the inversed DFT sequence that contains the same number of elements as provided sequence
     */
    public static ComplexNumber[] inverseDFT(ComplexNumber[] seqDFT) {
        int N = seqDFT.length;
        ComplexNumber[] inverseDFT = new ComplexNumber[N];

        for (int t = 0; t < N; t++) {
            ComplexNumber sumTerm = new ComplexNumber(0, 0);
            for (int k = 0; k < N; k++) {
                sumTerm.add(seqDFT[k].multiply(new ComplexNumber(Math.cos(2 * Math.PI * k * t / (double) N),
                        -Math.sin(2 * Math.PI * k * t / (double) N))));
            }
            inverseDFT[t] = sumTerm.multiplyReal(1.0 / (double) N);
        }

        return inverseDFT;
    }

    /**
     * Computes a periodic {@code SoundWave}
     * Given wave information from an inverse discrete Fourier Transformed sequence.
     *
     * @param waveInfo: single channel wave information to compute sound wave with
     * @return  sequence that represents a single channel periodic wave
     *          and contains same number of elements as waveInfo
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
