package recursion;

/**
 * Created on 2/6/17.
 */
public class BinsearchIter {


    public int search(int toFind, int[] data) {

        // ATTN:  exception cases for out-of-bounds input!
        if (    //   (loIdx > hiIdx)
                (data.length == 0)
                //|| (loIdx < 0)
                // || (hiIdx >= data.length)
                )
        {
            throw new IllegalArgumentException("Need non-empty sorted data array; and valid loIdx <= hiIdx!");
        }

        // ATTN:  must initialize RANGE to START!
        int midIdx = -1;
        int loIdx = 0;
        int hiIdx = data.length - 1;

        // ATTN:  need to handle = case where element is the one element exactly AT FRONT or BACK EDGE of data sequence!
        //        OR 1-element input data!
        // REPLACING recursive calls this way!
        while (loIdx <= hiIdx) {

            midIdx = (hiIdx - loIdx)/2 + loIdx; // ATTN:  - need to ADD loIdx OFFSET for segment,
                                                //        - ALSO to RECALC at begin of EACH iteration!
                                                //        - AND integer division!
            if (data[midIdx] == toFind) {
                return midIdx;
            }
            else if (toFind > data[midIdx]) {
                // ATTN:  discards LOWER half!
                loIdx = midIdx + 1;      // ATTN:  need +1 to NARROW range beyond midIdx; can PERMIT usage of hiIdx!
            }
            else {
                // ATTN:  discards UPPER half!
                hiIdx = midIdx - 1;      // ATTN:  need -1 to NARROW range below midIdx; can PERMIT reuse of loIdx!
            }
        }

        // If we got here; we were unable to find the value!
        return  -1;
    }

    public static void main(String arg[]) {

        // CASE 1: 1-element
        int[] input1 = new int[1];
        input1[0] = 5;
        int toFind1 = 5;
        BinsearchIter searcher1 = new BinsearchIter();
        int foundIdx1 = searcher1.search(toFind1, input1);
        System.out.println(String.format("Found Element Index is:  %d", foundIdx1));

        // CASE 2: 3-element
        int[] input2 = {3, 5, 7};
        int toFind2 = 7;
        BinsearchIter searcher2 = new BinsearchIter();
        int foundIdx2 = searcher2.search(toFind2, input2);
        System.out.println(String.format("Found Element Index is:  %d", foundIdx2));


        // CASE 3: 2-element
        int[] input3 = {2, 4, 6, 8, 10, 12};
        int toFind3 = 8;
        BinsearchIter searcher3 = new BinsearchIter();
        int foundIdx3 = searcher3.search(toFind3, input3);
        System.out.println(String.format("Found Element Index is:  %d", foundIdx3));

        // CASE 4: 0-element
        int[] input4 = {};
        int toFind4 = 4;
        BinsearchIter searcher4 = new BinsearchIter();
        int foundIdx4 = searcher4.search(toFind4, input4);
        // should be IllegalArgument EXCEPTION!
        System.out.println(String.format("Found Element Index is:  %d", foundIdx4));


    }
}
