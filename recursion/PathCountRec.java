package recursion;

/*
 * - origin at TOP, LHS (0,0)
 * - x moves positive RIGHT
 * - y moves positive DOWN
 * 
 * - ith 'flattened' position count is = ROW * MAX_ROW_LEN + COL
 *
 *
 *  PROBLEM:
 *  Count number paths from ORIGIN TOP LHS to FINAL point on BOTTOM RHS of grid with m rows, n columns
 *   - get recursive decision-tree modeled with recursion; where at each node we decide which cell to move
 *   to ... all the way down to leaf nodes on tree!
 *   
 *  TRICK:
 *  Use ARRAY for stack-frame cumulative argument!
 *
 */
public class PathCountRec {
	
	    static int ROWS;
        static int COLUMNS;
        
        public PathCountRec(int rows, int columns) {
        	ROWS = rows;
        	COLUMNS = columns;
        }
	    
	    // NOTE:  using ARRAY to store cumulative return value on STACK
		public void recurseCountPathsFromOriginToPoint(int x, int y, int[] cumulativeCount) {
	
			if (x < (ROWS-1)) {
				// ie count ways we can CHOOSE to branch a different path!
				(cumulativeCount[0])++;
				recurseCountPathsFromOriginToPoint(++x, y, cumulativeCount);
			}
			else if (y < (COLUMNS-1)) {
				// ie count ways we can CHOOSE to branch a different path!
				(cumulativeCount[0])++;
				recurseCountPathsFromOriginToPoint(x, ++y, cumulativeCount);
			}
			else {
				// ie hit END path;
				// and  PRIOR to entering this point, ALREADY counted path!
				// SO, no count necessary!
				// (cumulativeCount[0])++;
			}
			return;
		
		}
		
		public static void main(String args[]) {
			
			int[] cumulativeCount = {0}; 
			int currentX = 0;
			int currentY = 0;
			
			// FIRST TEST:  1 element
			PathCountRec matrix1 = new PathCountRec(1, 1);
			matrix1.recurseCountPathsFromOriginToPoint(currentX, currentY, cumulativeCount);
			System.out.printf("COUNT OF PATHS is:  %d\n", cumulativeCount[0]);
			
			// SECOND TEST:  2x2
			PathCountRec matrix2 = new PathCountRec(2, 2);
			cumulativeCount[0] = 0;
			matrix2.recurseCountPathsFromOriginToPoint(currentX, currentY, cumulativeCount);
			System.out.printf("COUNT OF PATHS is:  %d\n", cumulativeCount[0]);
			
			// THIRD TEST:  2 x 3 matrix
			PathCountRec matrix3 = new PathCountRec(2, 3);
			cumulativeCount[0] = 0;
			matrix3.recurseCountPathsFromOriginToPoint(currentX, currentY, cumulativeCount);
			System.out.printf("COUNT OF PATHS is:  %d\n", cumulativeCount[0]);
			
		}
}
