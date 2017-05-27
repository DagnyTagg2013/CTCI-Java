package strings;

/*
    ATTN KEY POINTS:
         - use StringBuilder for IN-PLACE modification based on START/END parameterized indices!
         - use WHILE loop to ADVANCE pointers for Start, and Next!  (ATTN to set start/end bounds BEFORE START LOOP then AFTER END LOOP cases!)
         - REVERSE WHOLE String; then REVERSE segments!

 */

public class ReversePhrase2 {

    // ATTN: IN-PLACE MUTABLE StringBuilder; reversing in-place BY index!
    public static StringBuilder reverseInPlaceByIdx(StringBuilder aString, int startIdx, int endIdx) {

        // ATTN:  mutable structure, and tmp var!
        char tmp = '\0';
        for (int fwd = startIdx, back = endIdx; fwd <= back; fwd++, back--) {
            tmp = aString.charAt(fwd);
            aString.setCharAt(fwd, aString.charAt(back));
            aString.setCharAt(back, tmp);
        }

        return aString;
    }

    // ATTN:  find word break FROM given index
    public static StringBuilder reverseOnWordBreaks(StringBuilder aString, String separ) {

        // ATTN:  ITERATIVELY MOVE BOUNDARY WINDOW!
        int currStartIdx = 0;
        int nextSepIdx = aString.indexOf(separ, currStartIdx);
        // ATTN:  check for NEXT!
        while (nextSepIdx != -1) {
            reverseInPlaceByIdx(aString, currStartIdx, (nextSepIdx - 1));
            // ATTN:  iterate to NEXT word!
            currStartIdx = (nextSepIdx + 1);
            nextSepIdx = aString.indexOf(separ, currStartIdx);
        }
        // ATTN: handle trailing case
        reverseInPlaceByIdx(aString, currStartIdx, aString.length() - 1);

        return aString;
    }

    public static String reverseSentence(String aString, String separ) {

        StringBuilder aStringBuilder = new StringBuilder(aString);
        StringBuilder fullSentenceReverse = reverseInPlaceByIdx(aStringBuilder, 0, (aString.length() - 1));
        StringBuilder wordsReverse = reverseOnWordBreaks(fullSentenceReverse, " ");
        return wordsReverse.toString();

    }

    public static void main (String args[]) {

            String test1 = "Fox";
            StringBuilder buffer = new StringBuilder(test1);
            StringBuilder res1 = ReversePhrase2.reverseInPlaceByIdx(buffer, 0, buffer.length() - 1);
            System.out.println(res1);

            String test2 = "The Quick";
            StringBuilder buffer2 = new StringBuilder(test2);
            StringBuilder res2 = ReversePhrase2.reverseOnWordBreaks(buffer2, " ");
            System.out.println(res2);

            String test3 = "The Quick Brown Fox";
            String res3 = ReversePhrase2.reverseSentence(test3, " ");
            System.out.println(res3);

    }

}
