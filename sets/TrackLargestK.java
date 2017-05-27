package sets;

// TODO:  look at PYTHON for this!
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/* 
 * PROBLEM1:  - rolling window filter of K-highest rank-values seen
 *            - don't care about ordering
 *            - don't care about duplicates
 *            - don't care about range between lowest and highest
 *           
 * SOLUTION1:  SORTED SET
 *            - if size() > max K; then compare new input with LOWEST value
 *            - if new input largest than current MIN; EVICT current min, then ADD new input!
 * 
 * PROBLEM2:  - get a stream of clicks on an item
 *            - want top 5 items with most clicks
 *            
 * SOLUTION2:  - MAP1 of item to total clicks
 *             - SCAN-CONVERT MAP1 to MAP2 of total clicks to items
 *               but where MAP2 is SORTED MAP by total clicks (key)
 *             - ITERATE through TOP 5 to get top 5 items by total clicks
 *                
 */
public class TrackLargestK {
	
	private static final int MAX_INTEREST = 5;

	// DEFAULT sorted from SMALLEST to LARGEST Integer ranks
	private SortedSet<Integer> rollingWindow = new TreeSet<Integer>();

	// ATTN:  JDK1.8 collection contents!
	// - http://stackoverflow.com/questions/12455737/how-to-iterate-over-a-set-hashset-without-an-iterator
	public void printSet(Set<Integer> input) {

		System.out.println("***** Current Set Contents *****");
		rollingWindow.forEach(System.out::println);

	}

	// public void process(List<Integer> inputs) {
	public void process(List<Integer> inputs) {

		// NOTE:  simplified for loop with collection RHS, and single element declaration LHS!
	    for (Integer num: inputs) {
	    	// NOTE:  case if cache is not full, then just add this number to initialize the cache with something
	    	if (rollingWindow.size() < MAX_INTEREST) {
	    		rollingWindow.add(num);
	    	} 
	    	else {
	    		// we've found a number that's less than the least one, so we bump out the largest cached value
	    		// to replace it with this number
	    		if (num > rollingWindow.first()) {
	    			rollingWindow.remove(rollingWindow.first());
	    			rollingWindow.add(num);
	    		}
	    		else {
	    			// do nothing
	    		}
	    	}
	    }

	}
	
	public static void main(String[] args) {
		
		// TEST1: input sequence of 10 rankings, with some repetition; then print set of top 5
		int[] stream1 = {1, 5, 2, 3, 0};
		int[] stream2 = {5, 4, 8, 10, 30};

		TrackLargestK tracker = new TrackLargestK();

		/*
		tracker.process((List<Integer>)(Arrays.asList(stream1)));
		tracker.printSet();

		tracker.process((List<Integer>)(Arrays.asList(stream2)));
		tracker.printSet();
        */


	}
	
	

}
