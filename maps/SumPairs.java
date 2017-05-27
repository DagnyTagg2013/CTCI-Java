package maps;

import java.util.HashMap;
import java.util.Map;

// GIVEN:  array of integers 
// FIND:  missing pair integer that sums to 10

public class SumPairs {
	
	Integer TOTAL = 10;
	
	Map<Integer, Integer> getPair(int[] input) {
		Map<Integer, Integer> foundPair = new HashMap<Integer, Integer>();
	    for (int i=0; i < input.length; i++) {
	    	foundPair.put(new Integer(input[i]), new Integer(TOTAL-input[i]));
	    }
		return foundPair;
	}
	
	public static void main(String args[]) {
		
		SumPairs sumPairs = new SumPairs();
		
		// TEST1
		System.out.println("Pair values found for input are as follows:  \n");
		int[] input1 = {-1, 2, 5, 0, 7, 11};
		Map<Integer, Integer> result = sumPairs.getPair(input1);
		// NOTE:  can get SET of entries via entrySet() to ITERATE thru!
        for (Map.Entry<Integer,Integer> oneEntry : result.entrySet()) {
        	System.out.println(oneEntry.getKey() + " pairs with: " + oneEntry.getValue() + ", ");
        }
        
	}

}
