package recursion;

public class LinsearchRec {

    public int search(int[] data, int valToFind) {

        return rsearch(data, valToFind, 0);

    }

    private int rsearch(int[] data, int valToFind, int scanIdx) {

        // BASE CASE 1:  at END of data and value NOT FOUND
        if (scanIdx >= data.length) {
            return -1;
        }

        // BASE CASE 2: FOUND
        if (valToFind == data[scanIdx]) {
            return scanIdx;
        }

        // ITERATION CASE with TAIL recursion!
        scanIdx++;
        return rsearch(data, valToFind, scanIdx);

    }

    public static void main(String[] args) {

        LinsearchRec searcher = new LinsearchRec();

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
