package recursion;

/**
 * Created by daphneeng on 2/8/17.
 */
public class LinsearchIter {

    public LinsearchIter() {}

    public int search(int[] data, int valueToFind) {

        int foundIdx = -1;
        int scanIdx = 0;

        while (scanIdx < data.length) {

              if (valueToFind == data[scanIdx]) {
                foundIdx = scanIdx;
              }

              scanIdx++;
        }

        return foundIdx;
    }

    public static void main(String[] args) {

        LinsearchIter searcher = new LinsearchIter();

        // CASE 1:  find value
        int[] data = {3, 4, 5, 6};
        int startIdx = 0;
        int valueToFind = 5;
        int foundIdx1 =  searcher.search(data, valueToFind);
        System.out.println(String.format("FOUND value 5 at index %d", foundIdx1));

        // CASE 2:  no value found
        startIdx = 0;
        valueToFind = 2;
        int foundIdx2 =  searcher.search(data, valueToFind);
        System.out.println(String.format("NOT FOUND value 2 at index %d", foundIdx2));

        // CASE 3:  empty data
        int [] data3 = {};
        startIdx = 0;
        valueToFind = 5;
        int foundIdx3 =  searcher.search(data3, valueToFind);
        System.out.println(String.format("NOT FOUND value 2 in EMPTY data at index %d", foundIdx3));


    }
}
