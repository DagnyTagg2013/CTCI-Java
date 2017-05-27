package recursion;

/**
 * EXPONENTIAL complexity!
 */
public class FibonacciRecur {


    public static int genSequence(int n) {

        // BASE-TERM CASE 0
        if (n == 0) {
            System.out.println("1,");
            return 1;
        }

        if (n == 1) {
            System.out.println("1,");
            return 1;
        }

        int result = genSequence(n-1) + genSequence(n-2);

        System.out.println(String.format("%d", result));

        return result;
    }

    public static void main(String[] args) {

        // CASE 1: generate a sequence!
        System.out.println("\nONE:  ");
        FibonacciIter.genSequence(1);

        // CASE 2: boundary cases
        System.out.println("\nTWO:  ");
        FibonacciIter.genSequence(2);

        // CASE 3: boundary cases
        System.out.println("\nFIVE:  ");
        FibonacciIter.genSequence(5);


    }


}
