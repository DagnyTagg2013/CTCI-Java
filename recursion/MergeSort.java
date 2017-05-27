package recursion;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;


/*
 * TIPS
 *   List<Integer> recurseSortByList(List<Integer> data) because of need to insert and partition
 * - MID-PIVOT; SPLIT NEW left, right; and INSERT elements RELATIVE to PIVOT; RECURSE on each part to sort; THEN MERGE SORTED (left + PIVOT + right)
 * - implement with LINKEDLIST, since need to allocate NEW, and can be of VARIABLE size, AND need to append to END; so BETTER than primitive Array!
 * - use private HELPER interfaces for RECURSIVE implementations; PUBLIC for the non-recursive ones!
 * - IFF using Array as interface, pass ORIGINAL on the stack; and check for LENGTH, then DUMMY-EAGER-INITIALIZED values; 
 *   BUT a BLOCKER for stack allocation!
 * - BEWARE: Arrays.asList(...) NOT working as expected; since Object[] gets returned INSTEAD! 
 */
public class MergeSort {
	
	static public void main(String args[]) {
		
		// CASE1:
		int [] input1 = {5};
		//int [] sorted1 = MergeSort.recurseSortByArr(input1, input1.length); 
		int [] sorted1 = MergeSort.sortByList(input1); 
		
		//System.out.println(String.format("Output1 %s\n", Arrays.toString(sorted1)));
		
		// CASE2:  
		int [] input2 = {5, 2};
		// int [] sorted2 = MergeSort.recurseSortByArr(input2, input2.length);
		int[] sorted2 = MergeSort.sortByList(input2); 
		System.out.println(String.format("Output2 %s\n", Arrays.toString(sorted2)));
		
		// CASE3:
		int [] input3 = {5, 2, 8};
		// int [] sorted3 = MergeSort.recurseSortByArr(input3, input3.length);
		int[] sorted3 = MergeSort.sortByList(input3); 
		System.out.println(String.format("Output3 %s\n", Arrays.toString(sorted3)));
	
		// CASE4:
		int [] input4 = {5, 2, 8, 1};
		// int [] sorted4 = MergeSort.recurseSortByArr(input4, input4.length);
		int[] sorted4 = MergeSort.sortByList(input4); 
		System.out.println(String.format("Output4 %s\n", Arrays.toString(sorted4)));
			
	}
	
	public static int[] sortByList(int[] input) {
		
		List<Integer> inputList = new LinkedList<Integer>();
		for (int i= 0; i< input.length; i++) {
			inputList.add(new Integer(input[i]));
		}
		
		List<Integer> results = recurseSortByList(inputList);
				
		int[] resultArr = new int[input.length];
		for (int i=0; i < results.size(); i++) {
			resultArr[i] = results.get(i);
		}
		
		return resultArr;
		
	}
	
	private static List<Integer> recurseSortByList(List<Integer> data) {
		
		// SIMPLEST CASE to EXIT recursion from
		if (data.size() <= 1) {
			// TODO: CHECK if reference COPY gets popped off stack and is NO longer applicable at call;
			//       in which case need to COPY array and return reference to NEWLY-created one;
			//       OR invoke with a LIST parameter, modify, and return reference to THAT
			return data;
		}
		
		// NOW: figure PIVOT item; and then SPLIT lengths
		int midIdx = data.size()/2;
		Integer pivotItem = data.get(midIdx);
		
		// NOW:  create NEW LEFT and RIGHT sublists of VARIABLE length
		//       and BUILD such that LHS < MID; and RHS > MID
		List<Integer> lowerSplit = new LinkedList<Integer>();
		List<Integer> higherSplit = new LinkedList<Integer>();
		Integer scanItem = null;
	
		for (int i=0; i < data.size(); i++) {
			
			scanItem = data.get(i);
			if (scanItem < pivotItem) {
				lowerSplit.add(scanItem);
			} else if (scanItem > pivotItem){
				higherSplit.add(scanItem);
			} else {
				// do nothing, as this is equal to pivot item
				// CAREFUL!  Need this to REDUCE partition size EACH time!
			}
			
		}
		
		// NOW, RECURSE to ensure sublists are SORTED WITHIN each partition
		List<Integer> sortedLowerSplit = recurseSortByList(lowerSplit);
		List<Integer> sortedHigherSplit = recurseSortByList(higherSplit);
		
		// NOW, MERGE lists; ASSUMING subLists are SORTED, then RETURN RESULT
		List<Integer> result = new LinkedList<Integer>();
		// NOTE:  MERGE-JOIN PARTS HERE!!!
		result.addAll(sortedLowerSplit);
		result.add(pivotItem);
		result.addAll(sortedHigherSplit);
		
		return result;		
    }
	

	// ASSUMPTION:  left and right are each sorted arrays, not necessarily with contiguous values 
	/* TRICKS:  
	 * - remember to handle TRAILING case, to append the REMAINDER
	 * - track SEPARATE indices into the PRE-ALLOCATED result array of size which is the SUM size of left and right partitions! 
	 * - use WHILE loop and increment relevant indices  
	 * - CAREFUL, if value in part not initialized, then SKIP it!     
	 */
	public static int[] joinPartsByArr(int left[], int right[]) {
		
		int l = 0;
		int r = 0;
		int x = 0;
		
		//NOTE: eager-allocate values HERE, and setting DUMMY FILLER values to 0
		int result[] = new int[(left.length + right.length)];
		
		for (x=0; x<result.length; x++) {
						
				// IFF BOTH LEFT AND RIGHT HAVE VALID DATA, then MERGE-INTERLEAVE THEM; AND SKIP DUMMY VALUES!
				if ( (l < left.length) && (r < right.length) ) {
					
					if (left[l] < right[r]) {
						if (left[l] != 0) {
							result[x] = left[l];
							x++;
						}
						l++;
					} else {
						if(right[r] != 0) {
							result[x] = right[r];
							x++;
						}
						r++; 
					}
					
				}
				
				// IF DONE with left, and only right has data, then APPEND right data RELATIVE to where RIGHT is NOW
				if ( (l >= left.length) && (r < right.length)) {
					
					// NO NEED to initialize r, as it's already known
					for (; r < right.length; r++) {
						if (right[r] != 0) {
							result[x] = right[r];
							x++;
						}
					}
					
				}
			   
				// ATTENTION!   should NEVER reach case where RIGHT is scanned thru PRIOR to left,
				//              since ASSUMPTION is that all values on RIGHT are ALL higher than values on LEFT, as handled in a PRIOR step
	/*	
				// done with RIGHT, so copy LEFT
				if (l == right.length) {
					for (l = 0; l < left.length; l++) {
						result[x] = left[l];
						x++;
					}
				}
	*/
				
		} // end while
		
		return result;
	}
	
	public static int[] recurseSortByArr(int[] data, int length) {
		
		// END recursion
		if (length <= 1) { return data; }
		
		// PARTITION to lower, higher
		int partLen = (length/2);
		int midVal = data[partLen];
		
		// CAREFUL; for ODD number items, need to add EXTRA space to LOWER, or PART where value EQUAL to midval gets stored; 
		//          AND need to account for MAX-LEN if inputs INORDER, and MAX-SKEWs to ONE PARTITION!	

// BLOCKER:  MAX-SKEW MAY result in BLOW STACK for RECURSING on a LARGE NUMBER COLLECTION; SO INSTEAD GO WITH LIST; THEN TRANSFORM BACK TO ARRAYs for API!
		int [] lower = new int[partLen + 1];
		int [] higher = new int[partLen];
		
		// CAREFUL -- need to dummy-fill and SKIP those values on JOIN; so SAY 0 and WORSE than LIST for VARIABLE-length!
		int l = 0;
		int h = 0;
		for (int i=0; i < data.length; i++) {
		
			// CAREFUL: use or equals to pickup on case for EVEN number of items in input; to INCLUDE the midVal!
			if (data[i] <= midVal) {
				lower[l] = data[i];
				l++;
			}
			else if (data[i] > midVal){
				higher[h] = data[i];
				h++;
			}
		}
		
		// RECURSE to sort partitions
		lower = recurseSortByArr(lower, partLen);
		higher = recurseSortByArr(higher, partLen);
		
		// JOIN partitions for FUL sorted result
		int [] result = new int[data.length];

		result = joinPartsByArr(lower, higher);	
		return result;
		
	}

} // end class!



/*

function merge_sort(list m)
// if list size is 1, consider it sorted and return it
if length(m) <= 1
    return m
// else list size is > 1, so split the list into two sublists
var list left, right
var integer middle = length(m) / 2
for each x in m before middle
     add x to left
for each x in m after or equal middle
     add x to right
// recursively call merge_sort() to further split each sublist
// until sublist size is 1
left = merge_sort(left)
right = merge_sort(right)
// merge the sublists returned from prior calls to merge_sort()
// and return the resulting merged sublist
return merge(left, right)

*/
