package maps;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * GIVEN:
 * - input String
 * - any sequence of characters
 * - UNSORTED
 *
 * FIND:
 * - number of occurrences of character
 * - with order of occurrences listed ALPHABETICALLY
 *
 * *** FASTER
 * - drive with TEST-CASES
 * - GENERATE-PIPELINE data rather than ACCUMULATE to member data
 * - use String and *.charAt() to get requisite chars!
 * - Map against Object Types
 * - EntrySet and LAMBDA - de-packaging params!
 *
 */
public class LetterOccurs {

    // ATTN:  Use MAP for fast lookup for COUNTER; and RETURN it for least side-effects!
    public Map<Character, Integer>  accumLetterOccurrences(String input) {

        Map<Character, Integer> countMap = new HashMap<Character, Integer>();

        Character aChar = null;
        Integer aCount = 0;
        for (int i=0; i < input.length(); i++) {

            aChar = Character.toLowerCase(input.charAt(i));
            aCount = countMap.get(aChar);
            if (aCount == null) {
                countMap.put(aChar, 1);

            }
            else {
                countMap.put(aChar, ++aCount);
            }

        }

        return countMap;
    }

    // ATTN:  use Alpha String to drive output ORDERING, then pass IN the accumulation Map!
    public void printOrderedLetterOccurrences(Map<Character, Integer> countMap) {

        String alphaLower = "abcdefghijklmnopqrstuvwxyz";
        Character aChar = null;
        for (int i = 0; i < alphaLower.length(); i++) {
            aChar = alphaLower.charAt(i);
            System.out.println(String.format("Letter %c has %d Occurrences.", aChar, countMap.get(aChar)));
        }
    }

    // ATTN:  Use Map.entrySet() and Map.forEach!
    public void printUnOrderedLetterOccurrences(Map<Character, Integer> countMap) {

        // STANDARD
        System.out.println("OLD-SCHOOL-ENTRY\n");
        for (Map.Entry<Character, Integer> entry:  countMap.entrySet()) {
            System.out.println(String.format("Letter %c has %d Occurrences.", entry.getKey(), entry.getValue()));
        }

        // JDK1.8
        System.out.println("\nNEW-SCHOOL-LAMBDA\n");
        countMap.forEach((K,V) -> {
            System.out.println(String.format("Letter %c has %d Occurrences.", K, V));
        });

    }

    static public void main(String[] args) {

        // ATTN:  use object INSTANCE to invoke on GENERATED STATE without shared internal data side-effects!
        LetterOccurs driver = new LetterOccurs();

        // ATTN: TEST1 - STANDARD
        System.out.println("\n***** TEST 1: kowaabunngaa!\n");
        String test1 = "Kowaabunngaa!";
        Map<Character, Integer> countMap1 = driver.accumLetterOccurrences(test1);
        driver.printOrderedLetterOccurrences(countMap1);

        // ATTN:  TEST2 - EMPTY
        System.out.println("\n***** TEST 2: EMPTY!\n");
        String test2 = "";
        Map<Character, Integer> countMap2 = driver.accumLetterOccurrences(test2);
        driver.printOrderedLetterOccurrences(countMap2);

        // ATTN:  TEST3 - CASE
        System.out.println("\n***** TEST 3: MIXED-CASE!\n");
        String test3 = "ItsyBitsY";
        Map<Character, Integer> countMap3 = driver.accumLetterOccurrences(test3);
        driver.printOrderedLetterOccurrences(countMap3);


        // ATTN:  TEST4 - UNORDERED MAP ITERATION
        // JDK1.8 version of Map iteration and printing!
        System.out.println("\n***** TEST 4: MIXED-CASE!\n");
        driver.printUnOrderedLetterOccurrences(countMap1);

    }
}
