/**
 * @author Hussein Hafid
 * @author Najib Alkaddour
 * @since 2024-09-17
 */
public class Pi
{
    public static void main(String[] args) {
        // check if some value was entered
        int decimalCount = 70;
        if (args.length > 0) {
            decimalCount = Integer.valueOf(args[0]);
        }

        // solution: start
        RatNum res = new RatNum(0,1);
        RatNum pow1by16 = new RatNum(1);

        for(int k = 0; k < decimalCount; ++k) {
            RatNum tmp1 = new RatNum(4, 8 * k + 1);
            RatNum tmp2 = new RatNum(2, 8 * k + 4);
            RatNum tmp3 = new RatNum(1, 8 * k + 5);
            RatNum tmp4 = new RatNum(1, 8 * k + 6);

            tmp1 = tmp1.sub(tmp2).sub(tmp3).sub(tmp4);

            tmp1 = pow1by16.mul(tmp1);
            res = res.add(tmp1);
            
            pow1by16 = pow1by16.mul(new RatNum(1, 16));
        }
        // solution: end

        // display result
        RatNum m = new RatNum(1,1);
        RatNum ten = new RatNum(10,1);
        for (int k=0; k < decimalCount; k++) {
            m = m.mul(ten);
        }

        System.out.print("forbidden pi = ");
        String intPart = res.toIntString();
        System.out.print(intPart);
        System.out.print(".");
        RatNum digits = res.sub(RatNum.parse(intPart));
        System.out.print(digits.mul(m).toIntString());
        System.out.println("...");
    }
}