package maps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created on 11/16/16
 *
 * ALGO: - create MAP for letter occurrences in each WORD
 *       - DEEP EQUALS on hashmap to see if they are palindromes!
 */
public class CheckAnagrams {

    public static Map<Character,Integer> normWord(String aWord) {

            Map<Character, Integer> charFreq = new HashMap<Character, Integer>();
            for (int i=0; i < aWord.length(); i++) {
                Character thisChar = Character.toLowerCase(aWord.charAt(i));
                Integer charCount = charFreq.get(thisChar);
                // ATTN:  CAREFUL to check prior entry; then INITIALIZE COUNT if not found!
                if (charCount ==  null)
                    charFreq.put(thisChar, 1);
                else
                    charFreq.put(thisChar, (charCount + 1));
            }
        return charFreq;
    }

    public static void main(String[] args) {

        List<String> testList1 = Arrays.asList("Line", "Elin");
        Map<Character, Integer> normWord1 = CheckAnagrams.normWord(testList1.get(0));
        Map<Character, Integer> normWord2 = CheckAnagrams.normWord(testList1.get(1));

        // CAREFUL to use EQUALS for DEEP comparison vs SHALLOW!
        if (normWord1.equals(normWord2))
            System.out.println("TEST CASE 1 Words are PALINDROMES!");

        else
            System.out.println("TEST CASE 1 Words are NOT PALINDROMES!");


        List<String> testList2 = Arrays.asList("Bogus", "Nope");
        normWord1 = CheckAnagrams.normWord(testList2.get(0));
        normWord2 = CheckAnagrams.normWord(testList2.get(1));

        if (normWord1.equals(normWord2))
            System.out.println("TEST CASE 2 Words are PALINDROMES!");
        else
            System.out.println("TEST CASE 2 Words are NOT PALINDROMES!");


    }
}
