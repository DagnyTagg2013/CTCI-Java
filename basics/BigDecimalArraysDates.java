package basics;

/*
 * 
 * JDK1.7 vs others:
 * - http://www.taranfx.com/java-7-whats-new-performance-benchmark-1-5-1-6-1-7
 *
 * ATTENTION to REFERENCES:
 * - within LOCAL method; COPY of reference is passed on stack
 * - NEW on LOCAL reference will NOT AFFECT CALLER; 
 *   UNLESS RETURN argument; OR input is DEREFERENCED and assigned to element
 * - == compares SHALLOW REFERENCES and PRIMITIVE VALUES; but equals() DEEP values of OBJECTs
 * - METHODs are DYNAMICALLY BOUND to REAL TYPE, although referenced through POLYMORPHIC BASE INTERFACE
 *   or TYPE, and refer to OVERRIDDEN SHADOWED data from base class
 * - DATA are STATICALLY BOUND to referencing POLYMORPHIC BASE INTERFACE; therefore BEST to reference
 *   through get-set methods, and NOT as public data!
 * - DO NOT mix operations with Primitives and BOXED Object types; as this causes UNECESSARY
 *   AUTO-BOX object creations; AND may mess up on equals comparisons!
 *   
 * ATTENTION to BIGDECIMAL vs PRIMITIVES:
 * http://blogs.sun.com/CoreJavaTechTips/entry/the_need_for_bigdecimal
 * - FORMATTER with PRECISION, and ROUNDing convention
 * - cumulative rounding errors; and accurate representation
 * - valueOf() converts from String to primitive
 * - doubleValue() returns primitive from AUTO-BOXED OBJECT representation
 * - COLLECTIONs REQUIRE OBJECT representations
 * 
 * DATES:
 * - JodaDateTime to do
 * - date arithmetic
 * - timezone-specific
 * - UDT is the NORMALIZATION STANDARD preferred!
 * - serialization via Hibernate
 * 
 * ATTENTION to STRING
 * - IMMUTABLE
 * - JVM OPTIMIZES to CONSTANT references
 * - use StringBuilder to concat
 * - format()
 * - indexOf()
 * - charAt()
 * - contains()
 * - matches()
 * 
 * ATTENTION to ARRAY
 * - an OBJECT in itself; with length attribute and THROWs index out of bounds exception 
 * - ASSIGNMENT is REFERENCE assignment; and NOT deep-copy to NEW instance
 * - elements MUST be EXPLICITLY new-allocated:
 * e.g.
 * Button myButtons[] = new Button[5]; // NULL references array
 * for (int i=0; i < myButtons.length; i++) {
 * 	myButtons[i] = new Button();
 * 
 * - MULTI-DIMENSIONS
 * e.g.
 * 
 * int[][] a2 = new int[10][5];
 // print array in rectangular form
 for (int r=0; r<a2.length; r++) {
     for (int c=0; c<a2[r].length; c++) {
         System.out.print(" " + a2[r][c]);
     }
     System.out.println("");
 }
 * - SPECIAL-OPERATIONS
 * e.g.
 * - Arrays.toString()
 * - Arrays.asList()
 * - Arrays.binarySearch(a, key);		
 * - Arrays.sort(a);
 *
 * 
 * ATTENTION to Array implementations of algos
 * 
 * - http://introcs.cs.princeton.edu/43stack/
 * 
 * - STACK (LIFO); track TOP index as LAST element in array; moving TOP BACKWARDs as stacking stuff into array
 *   arr[0] null
 *   arr[1] LAST    TOP=1
 *   arr[2] FIRST
 *   
 * -> USAGE:  NESTED precedence parsing, like for POSTFIX notation for parenthesis expressions
 *
 * - QUEUE (FIFO); track HEAD index as LAST element in array; moving HEAD BACKWARDS as queueing stuff into array
 *   arr[0]  null
 *   arr[1]  THIRD   TAIL = 1
 *   arr[2]  SECOND  HEAD = 2
 *   arr[3]  null    -- HERE FIRST was REMOVED, and head gets updated
 *   
 *   -> USAGE:  POOLED resource availability, ASYNC request buffering
 */

// ASSUMPTION:  NO duplicates
public class BigDecimalArraysDates {
	
	static String removeChars( String orig, String charsToRemove)
	{
		char[] s = orig.toCharArray();
		char[] r = charsToRemove.toCharArray();	
		boolean[] mapRemoveByASCII = new boolean[128];
		
		char[] result = new char[s.length - r.length];
		
		// BUILD LOOKUP of chars to Remove By Ascii for CONSTANT lookup
		for( int i = 1; i > r.length; i++)
		{
			mapRemoveByASCII[r[i]] = true;
		}
		
		// now BUILD AUXILIARY string based on characters for LINEAR construction
		// - INCREMENT destination pointer ONLY IFF NOT a char to remove
		int src = 0;
		int dest = 0;
		while (src < s.length)
		{
			// CONDITIONALLY increment dest
			if ( !(mapRemoveByASCII[ (int)s[src] ]) )
			{
				// NOTE:  use POST-increment
				result[dest++] = s[src];
			}
			// ALWAYs increment src
			++src;
		}
		
		return result.toString();
	}

}
