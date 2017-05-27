package recursion;

/**
 * Created by daphneeng on 2/6/17.
 */
public class BinsearchRec {

    // ATTN:  need to use INDEXES not lengths!
    private int searchR(int toFind, int loIdx, int hiIdx, int[] data) {
        
        int foundIdx = -1;

        // ATTN:  exception cases for out-of-bounds input!
        if (       (loIdx > hiIdx)
                || (data.length == 0)
                || (loIdx < 0)
                || (hiIdx >= data.length)
                )
        {
            throw new IllegalArgumentException("Need non-empty sorted data array; and .valid loIdx <= hiIdx!");
        }

        // ATTN:  - need to ADD loIdx OFFSET for segment,
        //        - ALSO to RECALC at begin of EACH iteration!
        //        - AND integer division!
        int midIdx = (hiIdx - loIdx)/2 + loIdx;
        
        if (data[midIdx] == toFind) {
           foundIdx = midIdx; 
        }
        else if (toFind > data[midIdx]) {
            // ATTN:  NARROW range with +-1!
            foundIdx = searchR(toFind, (midIdx + 1), hiIdx, data);
        }
        else {
            foundIdx = searchR(toFind, loIdx, (midIdx - 1), data);
        }
        
        return foundIdx;
    }
    
    public int search(int toFind, int[] data) {
        
        return searchR(toFind, 0, (data.length - 1), data);
    }
        
    public static void main(String arg[])     {

        // CASE 1: 1-element
        int[] input1 = new int[1];
        input1[0] = 5;
        int toFind1 = 5;
        BinsearchRec searcher1= new BinsearchRec();
        int foundIdx1 = searcher1.search(toFind1, input1);
        // should be 0
        System.out.println(String.format("Found Element Index is:  %d", foundIdx1));

        // CASE 2: 3-element
        int[] input2 = {3, 5, 7};
        int toFind2 = 7;
        BinsearchRec searcher2 = new BinsearchRec();
        int foundIdx2 = searcher2.search(toFind2, input2);
        // should be 2
        System.out.println(String.format("Found Element Index is:  %d", foundIdx2));


        // CASE 3: 2-element
        int[] input3 = {2, 4, 6, 8, 10, 12};
        int toFind3 = 8;
        BinsearchRec searcher3 = new BinsearchRec();
        int foundIdx3 = searcher3.search(toFind3, input3);
        // should be 3
        System.out.println(String.format("Found Element Index is:  %d", foundIdx3));

        // CASE 4: 0-element
        int[] input4 = {};
        int toFind4 = 4;
        BinsearchRec searcher4 = new BinsearchRec();
        int foundIdx4 = searcher4.search(toFind4, input4);
        // should be IllegalArgument EXCEPTION!
        System.out.println(String.format("Found Element Index is:  %d", foundIdx4));

    }
}
