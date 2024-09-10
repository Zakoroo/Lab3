import java.math.BigInteger;

/**
 * @author Hussein Hafid
 * @author Najib Alkaddour
 * @since 2024-09-10
 */
class RatNum
{
    private BigInteger numerator;
    private BigInteger denominator;

    // TODO: Add doc string
    private RatNum(BigInteger a, BigInteger b) {
        switch(b.compareTo(BigInteger.ZERO)) {
            case 0:
                throw new NumberFormatException("Denominator = 0");
            case -1:
                a = a.negate();
                b = b.negate();
                break;
        }
        
        BigInteger gcd = a.gcd(b);
        this.numerator = a.divide(gcd);
        this.denominator = b.divide(gcd);
    }

    /**
     * <b>Constructor</b>
     * <p>Initializes the numerator to 0 and the denomintor to 1.</p>
     * @return this functions has no return values
     */
    public RatNum() {
        this(BigInteger.ZERO);
    }

    /**
     * <b>Constructor</b>
     * <p>Initializes the numerator to what value is given as a paramter and the denomintor to 1.</p>
     * @param a of type int
     * @return this functions has no return values
     */
    public RatNum(int a) {
        this(BigInteger.valueOf(a));
    }

    // TODO: Add doc string
    private RatNum(BigInteger a) {
        this.numerator = a;
        this.denominator = BigInteger.ONE;
    }

    /**
     * <b>Constructor</b>
     * <p>Initializes the numerator and denominator according to what values are provided.</p>
     * @param a of type int
     * @param b of type int
     * @return this functions has no return values
     */
    public RatNum(int a, int b) {
        this(BigInteger.valueOf(a), BigInteger.valueOf(b));
    }

    /**
     * <b>Constructor</b>
     * <p>Creates a new RatNum object with the same values of the inputed RatNum r.</p>
     * @param r of type RatNum
     * @return this functions has no return values
     */
    public RatNum(RatNum r) {
        this.numerator = r.numerator;
        this.denominator = r.denominator;
    }
    
    /**
     * <b>Constructor</b>
     * <p>Creates a new RatNum object with the same values of the inputed string representation "a/b" or "a"</p>
     * @param  s of type string
     * @return this functions has no return values
     */
    public RatNum(String s) {
        this(parse(s));
    }

    /**
     * <p>Returns the value of the numerator as an integer value.</p>
     * @return numerator of type int
     */
    public int getNumerator() {
        return this.numerator.intValue();
    }

    /**
     * <p>Returns the value of the denominator as an integer value.</p>
     * @return denominator of type int
     */
    public int getDenominator() {
        return this.denominator.intValue();
    }

    /**
     * <p>This method returns the string representation of the rational number.</p>
     * @return string representing a rational number in the format "a/b"
     */
    @Override
    public String toString() {
        return String.format("%s/%s", this.numerator.toString(), this.denominator.toString());
    }

    /**
     * <p>This parses a string to a RatNum object.</p>
     * @param s of type string representing a rational number in the format "a/b" or "a"
     * @return object of type RatNum
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
     * <p>This method checks wether the given rational number equals the current rational number
     * based on their numerators and denominators.</p>
     * @param r of type RatNum
     * @return boolean
     */
    public boolean equals(RatNum r) {
        return this.numerator.compareTo(r.numerator) == 0 && this.denominator.compareTo(r.denominator) == 0;
    }

    /**
     * <p>This method checks if the given rational number is less than the current rational
     * number</p>
     * @param r of type RatNum
     * @return boolean
     */
    public boolean lessThan(RatNum r) {
        return this.numerator.multiply(r.denominator).compareTo(r.numerator.multiply(this.denominator)) == -1;
    }

    /**
     * <p>This method mathematically adds the current rational number to the given rational number</p>
     * @param r of type RatNum
     * @return the sum of the current rational number and the inputed one
     */
    public RatNum add(RatNum r) {
        BigInteger a = this.numerator.multiply(r.denominator).add(this.denominator.multiply(r.numerator));
        BigInteger b = this.denominator.multiply(r.denominator);
        return new RatNum(a, b);
    }

    /**
     * <p>This method mathematically subtracts the given rational number from the current rational
     * number</p>
     * @param r of type RatNum
     * @return the difference between the current rational number and the inputed one
     */
    public RatNum sub(RatNum r) {
        BigInteger a = this.numerator.multiply(r.denominator).subtract(this.denominator.multiply(r.numerator));
        BigInteger b = this.denominator.multiply(r.denominator);
        return new RatNum(a, b);
    }

    /**
     * <p>This method mathematically muliplicates the given rational number and the current rational
     * number</p>
     * @param r of type RatNum
     * @return the product of the current rational number and the inputed one
     */
    public RatNum mul(RatNum r) {
        BigInteger a = this.numerator.multiply(r.numerator);
        BigInteger b = this.denominator.multiply(r.denominator);
        return new RatNum(a, b);
    }

    /**
     * <p>This method mathematically divides the current rational number by the given rational
     * number</p>
     * @param r of type RatNum
     * @return the quotient of the current rational number and the inputed one
     */
    public RatNum div(RatNum r) {
        BigInteger a = this.numerator.multiply(r.denominator);
        BigInteger b = this.denominator.multiply(r.numerator);
        return new RatNum(a, b);
    }

    public RatNum pow(int n) {
        RatNum res = new RatNum(this);
        if(n == 0) {
            return new RatNum(1);
        }
        else if(n < 0) {
            return new RatNum(this.denominator, this.numerator).pow(-n);
        }
        for(; n > 1; --n) {
            res.numerator = res.numerator.multiply(res.numerator);
            res.denominator = res.denominator.multiply(res.denominator);
        }
        return new RatNum(res.numerator, res.denominator);
    }

    /**
     * <p>This method returns the integer value of the current rational number in from of a
     * string.</p>
     * @return s of type String
     */
    public String toIntString() {
        return this.numerator.divide(this.denominator).toString();
    }
    
    /**
     * <p>This method computes the greatest common divisor "gcd" of two given numbers a and b.
     * This method implements the Euclidean algorithm. For more info visit
     * <a href="https://en.wikipedia.org/wiki/Greatest_common_divisor#Euclidean_algorithm">
     * Wikipedia</a>.</p>
     * @param a of type int
     * @param b of type int
     * @return gcd of type int
     */
    public static int gcd(int a, int b) {
        b = Math.abs(b);
        a = Math.abs(a);
        int temp;
        if (a == 0 && b == 0)
            throw new IllegalArgumentException();

        if (b == 0 || a == 0) {
            if (b == 0)
                return a;
            return b;
        }

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

    public static void main(String[] args) {
        RatNum r = new RatNum("10/3");

        r = r.pow(4);

        System.out.println("");
        System.out.println(r);
    }
}