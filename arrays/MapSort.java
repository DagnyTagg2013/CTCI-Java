package arrays;

import java.util.Arrays;


/*
 * GIVEN:  small array of UNIQUE,
 *         unsorted integer VALUES from 0 to 9,
 *         of length <= 10
 *         with REPITITION OK
 * FIND:   array of UNIQUE sorted integers
 * INT:  FIXED length input usually translates to array output with index EQUAL TO input values!
 */
public class MapSort {
	
	private static final int MAX_LENGTH = 10;
	
	public int[] sort(int[] unsorted) {
		// basic input validation
		if (unsorted.length > MAX_LENGTH) {
			throw new IllegalArgumentException("Input must be <= 10 in length.\n");
		}
		int[] sorted = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		for (int i=0; i< unsorted.length; i++)
			// KEY TRICK:  use MAP!
			sorted[unsorted[i]] = unsorted[i]; 
	    return sorted;
	}
	
	public static void main(String args[]) {
		
		MapSort sorter = new MapSort();
	
		// TEST1:  all 10 possible integers
		System.out.println("TEST1");
		int[] unsorted1 = {8,7,0,6,9,2,1,3,5,4};
		int[] sorted1 = sorter.sort(unsorted1);
		System.out.println("For input unsorted array:  ");
		// NOTE:  for nested arrays, can ALSO do Arrays.deepToString()
		System.out.println(Arrays.toString(unsorted1));
		System.out.println("Sorted array output is:  ");
		System.out.println(Arrays.toString(sorted1));
		
		// TEST2: some missing integers
		System.out.println("\n\nTEST2");
		int[] unsorted2 = {8,7,0,6};
		int[] sorted2 = sorter.sort(unsorted2);
		System.out.println("For input unsorted array:  ");
		// NOTE:  for nested arrays, can ALSO do Arrays.deepToString()
		System.out.println(Arrays.toString(unsorted2));
		System.out.println("Sorted array output is:  ");
		System.out.println(Arrays.toString(sorted2));
		
		// TEST3: some repeated integers  (REPEATs NOT taken!)
		System.out.println("\n\nTEST3");
		int[] unsorted3 = {8,7,0,6,6,0,7,9,5,8};
		int[] sorted3 = sorter.sort(unsorted3);
		System.out.println("For input unsorted array:  ");
		// NOTE:  for nested arrays, can ALSO do Arrays.deepToString()
		System.out.println(Arrays.toString(unsorted3));
		System.out.println("Sorted array output is:  ");
		System.out.println(Arrays.toString(sorted3));
				
		
	}
	
		
}
