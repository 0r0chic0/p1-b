package cpen221.soundwaves;

/**
 *  ComplexNumber represents a mutable complex number.
 */
public class ComplexNumber {
    private double real;
    private double imaginary;

    // The abstraction function is
    //      AF(r) = complex number c such that
    //          c.real = r.real
    //          c.imaginary = r.imaginary

    // The rep invariant is
    //      real is not null
    //      imaginary is not null

    /**
     * Constructs an instance of a complex number.
     *
     * @param real:         the real value of the complex number,
     *                      requires real is not null
     * @param imaginary:    the imaginary value of the complex number,
     *                      requires imaginary is not null
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Gets real part of complex number.
     *
     * @return real value of complex number
     */
    public double getReal() {
        return real;
    }

    /**
     * Gets imaginary part of complex number.
     *
     * @return imaginary value of complex number
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Computes addition of a complex number onto the complex number.
     *
     * @param real:         real part of complex number to add,
     *                      requires real is not null
     * @param imaginary:    imaginary part of complex number to add,
     *                      requires imaginary is not null
     */
    public void add(double real, double imaginary) {
        this.real = real + getReal();
        this.imaginary = imaginary + getImaginary();
    }

    /**
     * Computes addition of a complex number onto the complex number.
     *
     * @param number: complex number to add
     */
    public void add(ComplexNumber number) {
        this.real = getReal() + number.getReal();
        this.imaginary = getImaginary() + number.getImaginary();
    }

    /**
     * Computes the modulus of the complex number.
     *
     * @param number:   complex number to compute modulus of,
     *                  requires that number is not null
     * @return          the modulus of the complex number
     */
    public static double mod(ComplexNumber number) {
        return Math.sqrt(Math.pow(number.getReal(), 2) + Math.pow(number.getImaginary(), 2));
    }

    /**
     * Computes the multiplication of two complex numbers in the form a + bi where a != 0, b != 0.
     *
     * @param cd:   complex number to multiply with,
     *              requires that cd is not null
     * @return      a new {@code ComplexNumber} that is the product of the two complex numbers
     */
    public ComplexNumber multiply(ComplexNumber cd) {
        double a = getReal();
        double b = getImaginary();
        double c = cd.getReal();
        double d = cd.getImaginary();

        double real = (a * c) - (b * d);
        double imaginary = (a * d) + (b * c);

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Computes the multiplication of the complex number by a real number.
     *
     * @param real:     real number to multiply by,
     *                  requires that real is not null
     * @return          a new {@code ComplexNumber} that is the product of the complex number and the real number
     */
    public ComplexNumber multiplyReal(double real) {
        return new ComplexNumber(real * getReal(), real * getImaginary());
    }

    /**
     * Computes angle of complex number in the complex plane.
     *
     * @return the angle between the real axis and the complex vector
     */
    public double getAngle() {
        return Math.atan2(getImaginary(), getReal());
    }
}
