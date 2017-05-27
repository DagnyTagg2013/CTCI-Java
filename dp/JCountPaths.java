package dp;

// import java.util.Arrays;

import java.util.Arrays;

/**
 * Created 11/8/16.
 *
 * PROBLEM:
 * - Count ways for Child to run up the stairs when one can hop from:
 * (n-1), (n-2), (n-3)
 * - go BACKwards with DEGENERATE case at 0!
 *
 * APPROACH:
 * - hold State-Building MEMO of PRIOR RESULTs on STACK variable, so BYPASS OVERLAP-PROCESSING
 *   via multi-recursion calls!
 * - solve BACKWARDs from END to degenerate EXIT case at the beginning!  e.g. to get to LAST step, had to get there via a 2-hop, 1-hop or 3-hop
 *
 *
 */

// EXPONENTIAL BAD Way!
public class JCountPaths {

    // ATTN:  Use n as 0-indexed CURRENT step number!
    public static int recurseCount(int n) {

        // ATTN:  easy way to handle BOUNDARY START case!
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } else {
            return recurseCount(n - 3) + recurseCount(n - 2) + recurseCount(n - 1);
        }
    }

    // ATTN:  Use n as 0-indexed CURRENT step number; assumes initialized map to values of -1!
    //        this DETERMINES if CACHED result already for this iterative STEP!
    public static int memoedCount(int n, int[] map) {

        System.out.println(String.format("***CALL for index %d", n));

        // ATTN:  DEGENERATE cases!
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
            // BIG DEALIO OPTIMIZATION TO STOP EXPONENTIAL complexity!:  return result if ALREADY CACHED!
        } else if (map[n] > -1) {
            return map[n];
        } else {
            // ATTN:  build ITERATIVE result built from prior!
            map[n] = memoedCount(n - 1, map) +
                     memoedCount(n - 2, map) +
                     memoedCount(n - 3, map);
            // ATTN:  ALT + Enter  to autoimport!
            System.out.println(Arrays.toString(map));
            return map[n];
        }
    }


    // ATTN:  Initialize memos map for given FULL_PATH_SIZE
    // TODO:  CAN return Array, not necessarily heap-allocated ArrayList!
    public static int[] initPath(int fullPathSize) {


        if (fullPathSize < 0) {
            int[] emptyPath = new int[0];
            emptyPath[0] = -1;
            return emptyPath;
        }

        // ATTN:  initialize memoedCount to -1
        // TODO:  find out CURRENT easier way to do this!
        // ATTN:  INDEX is 0-indexed to SIZE-1; and n represents INDEX!
        // ATTN:  test EQUALS case for LAST index!
        int[] memoed = new int[fullPathSize];
        for (int i = 0; i <= (fullPathSize - 1); i++) {
            memoed[i] = -1;
        }

        // TODO:  remember if possible to return array from method anyway!
        return memoed;

    }

    public static void main(String[] args) {

        // ********************
        // ATTN:  Input n represents 0-INDEXING up to DIMENSION N - 1

        // TEST 1.1:  RECURSION, start with INDEX of -1 on 1-D path  size of 0!
        int numPaths1 = JCountPaths.recurseCount(-1);
        System.out.println(String.format("TEST1 Number of paths counted via recursion is:  %d\n", numPaths1));

        // TEST 1.2:  RECURSION, start with INDEX of 1 on 1-D path size of 2!
        int numPaths2 = JCountPaths.recurseCount(1);
        System.out.println(String.format("TEST2 Number of paths counted via recursion is:  %d\n", numPaths2));

        // TEST 1.3:  RECURSION, start with INDEX of 3 on 1-D path size of 4!
        int numPaths3 = JCountPaths.recurseCount(3);
        System.out.println(String.format("TEST3 Number of paths counted via recursion is:  %d\n", numPaths3));

        // ********************
        // ATTN:  Input n represents 0-INDEXING up to N - 1

        // TEST 2.1:  MEMOED LOOP, start with index 0!
        System.out.println("TEST 2.1!");
        int [] memoed1 = initPath(2);
        int numPaths21 = JCountPaths.memoedCount(1, memoed1);
        System.out.println(String.format("TEST21 Number of paths counted via memo is:  %d\n", numPaths21));

        // TEST 2.2:  MEMOED LOOP, start with index 0!
        System.out.println("TEST 2.2!");
        int [] memoed2 = initPath(3);
        int numPaths22 = JCountPaths.memoedCount(2, memoed2);
        System.out.println(String.format("TEST22 Number of paths counted via memo is:  %d\n", numPaths22));

        // TEST 2.3:  MEMOED LOOP, start with index 0!
        System.out.println("TEST 2.2!");
        int [] memoed3 = initPath(4);
        int numPaths23 = JCountPaths.memoedCount(3, memoed3);
        System.out.println(String.format("TEST23 Number of paths counted via memo is:  %d\n", numPaths23));

    }

}
