package recursion;

/*

    CALCULATE:
    1) f(n) = f(n - 1) + f(n - 2)

 */


/*
 * Index:  0 1 2 3 4 5
 * Value:  0 1 1 2 3 5
 */


/**
 *  ITERATIVE complexity!
 */

public class FibonacciIter {


    public static void genSequence(int n) {

        int f0 = 1;
        int f1 = 1;

        // ATTN:  INITIALIZE FIRST TWO values to 1; then set index to START at 2,
        //        init fib result ALSO
        int i = 2;
        int fib = -1;

        System.out.print(String.format("%d,", f0));
        System.out.print(String.format("%d,", f1));

        while (i < n) {

            fib = f0 + f1;
            System.out.print(String.format("%d,", fib));

            /*
                    - ATTN  PROGRESS the lookback values
                    - where what WAS f1 is now f0,
                    - pushing back LATEST cached result
                    CAREFUL spacing
            */
            f0 = f1;
            f1 = fib;

            i++;

        }

    }

    public static void main(String[] args) {

        // CASE 1: generate a sequence!
        System.out.println("\nFIVE:  ");
        FibonacciIter.genSequence(7);

        // CASE 2: boundary case
        System.out.println("\nONE:  ");
        FibonacciIter.genSequence(1);

    }


}
