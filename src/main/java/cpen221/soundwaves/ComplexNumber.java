package cpen221.soundwaves;

public class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void add(double real, double imaginary) {
        this.real = real + getReal();
        this.imaginary = imaginary + getImaginary();
    }

    public static double mod(ComplexNumber number) {
        return Math.sqrt(Math.pow(number.getReal(), 2) + Math.pow(number.getImaginary(), 2));
    }

}
