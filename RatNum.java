import java.math.BigInteger;

/**
 * @author Hussein Hafid
 * @author Najib Alkaddour
 * @since 2024-09-17
 */
class RatNum
{
    private BigInteger numerator;
    private BigInteger denominator;

    /**
     * <b>Private constructor</b>
     * <p>Initializes the numerator and denominator according to the specified values.</p>
     * @param a The numerator
     * @param b The denominator
     */
    private RatNum(BigInteger a, BigInteger b) {
        switch(b.compareTo(BigInteger.ZERO)) {
            case 0 -> throw new NumberFormatException("Denominator = 0");
            case -1 -> {
                a = a.negate();
                b = b.negate();
            }
        }
        
        BigInteger gcd = a.gcd(b);
        this.numerator = a.divide(gcd);
        this.denominator = b.divide(gcd);
    }

    /**
     * <b>Constructor</b>
     * <p>Initializes the numerator to 0 and the denominator to 1.</p>
     */
    public RatNum() {
        this(BigInteger.ZERO);
    }

    /**
     * <b>Constructor</b>
     * <p>Initializes the numerator to the specified value and the denominator to 1.</p>
     * @param a The numerator
     */
    public RatNum(int a) {
        this(BigInteger.valueOf(a));
    }

    /**
     * <b>Private constructor</b>
     * <p>Initializes the numerator to the specified value and the denominator to 1.</p>
     * @param a The numerator
     */
    private RatNum(BigInteger a) {
        this.numerator = a;
        this.denominator = BigInteger.ONE;
    }

    /**
     * <b>Constructor</b>
     * <p>Initializes the numerator and denominator according to the specified values.</p>
     * @param a The numerator
     * @param b The denominator
     */
    public RatNum(int a, int b) {
        this(BigInteger.valueOf(a), BigInteger.valueOf(b));
    }

    /**
     * <b>Constructor</b>
     * <p>Initializes the numerator and denominator according to the same values of the provided RatNum object.</p>
     * @param r The rational number object
     */
    public RatNum(RatNum r) {
        this.numerator = r.numerator;
        this.denominator = r.denominator;
    }
    
    /**
     * <b>Constructor</b>
     * <p>Initializes the numerator and denominator from a string formatted as "a" or "a/b".</p>
     * @param  s A string representing a rational number
     */
    public RatNum(String s) {
        this(parse(s));
    }

    /**
     * <p>Returns the value of the numerator as an integer value.</p>
     * @return The numerator to this rational number
     */
    public int getNumerator() {
        return this.numerator.intValue();
    }

    /**
     * <p>Returns the value of the denominator as an integer value.</p>
     * @return The denominator to this rational number
     */
    public int getDenominator() {
        return this.denominator.intValue();
    }

    /**
     * <p>Returns the string representation of the rational number.</p>
     * @return A string representing a rational number in the format "a/b"
     */
    @Override
    public String toString() {
        return String.format("%s/%s", this.numerator.toString(), this.denominator.toString());
    }

    /**
     * <p>Parses a string to a RatNum object.</p>
     * @param s A string representing a rational number in the format "a" or "a/b"
     * @return The rational number object corresponding to the inputted string
     */
    public static RatNum parse(String s) {
        if (!s.contains("/")) {
            return new RatNum(new BigInteger(s));
        }
        else {
            String[] tokens = s.split("/");
            if(tokens.length == 2) {
                return new RatNum(new BigInteger(tokens[0]), new BigInteger(tokens[1]));
            }
            else {
                throw new NumberFormatException();
            }
        }
    }

    /**
     * <p>Checks whether the given object equals the this rational number.</p>
     * @param r The object to compare
     * @return True if the rational numbers are equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        else if(obj.getClass() != this.getClass())
            return false;
        else if(obj == this)
            return true;
        else {
            RatNum r = (RatNum) obj;
            return this.numerator.compareTo(r.numerator) == 0 && this.denominator.compareTo(r.denominator) == 0;
        }
    }

    /**
     * <p>Checks if the given rational number is less than the current rational number.</p>
     * @param r The rational number to compare
     * @return True if the given rational number is less than this, otherwise false
     */
    public boolean lessThan(RatNum r) {
        return this.numerator.multiply(r.denominator).compareTo(r.numerator.multiply(this.denominator)) == -1;
    }

    /**
     * <p>Adds this rational number to the given rational number.</p>
     * @param r The rational number to be added to this
     * @return The sum of this and the given rational number
     */
    public RatNum add(RatNum r) {
        BigInteger a = this.numerator.multiply(r.denominator).add(this.denominator.multiply(r.numerator));
        BigInteger b = this.denominator.multiply(r.denominator);
        return new RatNum(a, b);
    }

    /**
     * <p>Subtracts the given rational number from this.</p>
     * @param r The rational number to be subtracted from this
     * @return This difference between this and the inputted rational number
     */
    public RatNum sub(RatNum r) {
        BigInteger a = this.numerator.multiply(r.denominator).subtract(this.denominator.multiply(r.numerator));
        BigInteger b = this.denominator.multiply(r.denominator);
        return new RatNum(a, b);
    }

    /**
     * <p>Multiplies the given rational number with this.</p>
     * @param r The rational number to multiplicate this with
     * @return The product of this and the inputted rational number
     */
    public RatNum mul(RatNum r) {
        BigInteger a = this.numerator.multiply(r.numerator);
        BigInteger b = this.denominator.multiply(r.denominator);
        return new RatNum(a, b);
    }

    /**
     * <p>Divides this by the given rational number.</p>
     * @param r The rational number this gets divided by
     * @return The quotient of this and the inputted rational number
     */
    public RatNum div(RatNum r) {
        BigInteger a = this.numerator.multiply(r.denominator);
        BigInteger b = this.denominator.multiply(r.numerator);
        return new RatNum(a, b);
    }

    /**
     * <p>Raises this rational number to the power of inputted value.</p>
     * @param n The exponent to which this will be raised
     * @return The result of the exponentiation
     */
    public RatNum pow(int n) {
        if(n == 0)
            return new RatNum(1);
        else if(n < 0)
            return new RatNum(this.numerator.pow(-n), this.denominator.pow(-n));
        else
            return new RatNum(this.numerator.pow(n), this.denominator.pow(n));
    }

    /**
     * <p>Returns the integer value of the current rational number in from of a string.</p>
     * @return A string representing the integer division of this rational number
     */
    public String toIntString() {
        return this.numerator.divide(this.denominator).toString();
    }
    
    /**
     * <p>This method computes the greatest common divisor "gcd" of two given integers a and b.
     * This method implements the Euclidean algorithm. For more info visit
     * <a href="https://en.wikipedia.org/wiki/Greatest_common_divisor#Euclidean_algorithm">
     * Wikipedia</a>.</p>
     * @param a The first value
     * @param b The second value
     * @return The greatest common divisor of a and b
     */
    public static int gcd(int a, int b) {
        if(a == 0 && b == 0)
            throw new IllegalArgumentException();
        if(b == 0 || a == 0) {
            return b == 0 ? a : b;
        }
        
        b = Math.abs(b);
        a = Math.abs(a);

        int temp;
        if (b > a) {
            temp = b;
            b = a;
            a = temp;
        }

        if (a > b) {
            while (a % b != 0) {
                temp = a % b;
                a = b;
                b = temp;
            }
        }
        return b;
    }
}