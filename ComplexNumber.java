import java.util.Locale;

public class ComplexNumber {
    private double re;
    private double im;

    public ComplexNumber() {
        this.re = 0.0;
        this.im = 0.0;
    }

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public ComplexNumber negate() {
    return new ComplexNumber(-re, -im);
   }

    public double getReal() {
        return re;
    }

    public double getImag() {
        return im;
    }

    public ComplexNumber add(ComplexNumber other) {
        double realPart = this.re + other.re;
        double imagPart = this.im + other.im;

        return new ComplexNumber(realPart, imagPart);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        double realPart = this.re - other.re;
        double imagPart = this.im - other.im;

        return new ComplexNumber(realPart, imagPart);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        double a = this.re;
        double b = this.im;
        double c = other.re;
        double d = other.im;

        double realPart = a * c - b * d;
        double imagPart = a * d + b * c;

        return new ComplexNumber(realPart, imagPart);
    }

    public ComplexNumber divide(ComplexNumber other) {
        double a = this.re;
        double b = this.im;
        double c = other.re;
        double d = other.im;

        double denom = c * c + d * d;
        double realPart = (a * c + b * d) / denom;
        double imagPart = (b * c - a * d) / denom;

        return new ComplexNumber(realPart, imagPart);
    }

    @Override
    public String toString() {
        if (im >= 0) {
            return String.format(Locale.US, "%.2f + %.2fi", re, im);
        } else {
            return String.format(Locale.US, "%.2f - %.2fi", re, -im);
        }
    }
}
