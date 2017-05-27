package basics;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
JAVA - to simulate Pythonic multi-arg returned TUPLES;
       pass in ARRAY or STRUCTURE argument on call-stack; then DEREFERENCE and MODIFY WITHIN method
       OR pass OUT ARRAY or List STRUCTURE argument on NEW-ALLOCATED structure WITHIN method
 */
public class ReferenceArguments {
	
	static void changeString(String s) {
		// s = "Hi";
		s = new String("Hi");
	}
	
	static void changeInteger(Integer p) {
		p = new Integer(100);
	}
	
	static void changePoint(Point p) {
			p.x = 38;
			p.y =97;
	}
	
	static Point changePointAgain(Point p)   {
		p = new Point(10, 10);
		return p;
	}
	
	static void changeOrigArray(int[] nums) {
		nums[0] = 8;
		nums[1] = 8;
	}
	
	static void changeOrigRef(Integer num) {
		num = 88; // NEW integer instance gets created by AUTOBOXING, and is LOST on popping STACK!
	}
	
	static void changeCollectionRef(List<Integer> list) {
		list.set(0, new Integer(88));
	}
	
	public static void main(String args[]) {	
		    
			Point p1 = new Point(0, 0);
			Integer i = new Integer(10);
			String s = "Hello";
		    Point p2 = null;
			
			ReferenceArguments.changeString(s); 
			ReferenceArguments.changeInteger(i);  
			ReferenceArguments.changePoint(p1);  

		    // remains SAME as CALLER String -- as INTERNAL to METHOD is referencing COPY of CALLER String!
			System.out.println(String.format("String after changeString %s", s));  

		    // SAME as above, where NEW-created reference is POPPED on returning back from function!
			System.out.println(String.format("Integer after changeInteger %d", i));

		    // CHANGES as again DEREFERENCING into ORIGINAL OBJECT via '.' on REFERENCE within function to change ACTUAL data
			System.out.println(String.format("Point after changePoint %s", p1));


		    p1 = new Point(0, 0);
			p2 = ReferenceArguments.changePointAgain(p1);
		    // CHANGES ONLY NEW VAR, as RETURNs NEWLY-ALLOCATED point as RETURN VARIABLE!
			System.out.println(String.format("Old Point after changePointAgain %s", p1));
			System.out.println(String.format("New Point after changePointAgain %s", p2));
			
			int[] nums = {-1, -1};
			ReferenceArguments.changeOrigArray(nums);
		    // CHANGES as DEREFERENCING into ORIGINAL OBJECT via '[]' on REFERENCE within function to change ACTUAL data
			System.out.println(String.format("Array contents after changeOrigArray %s", Arrays.toString(nums)));
			
			Integer origInt = new Integer(-1);
			ReferenceArguments.changeOrigRef(origInt);
		    // NO CHANGES, as NOT DEREFERENCING copy OF REFERENCE TO ORIGINAL CALLING INTEGER!
			System.out.println(String.format("Integer contents after changeOrigRef  %d", origInt));
			
			List<Integer> numList = new ArrayList<Integer>();
			numList.add(new Integer(-1));
			ReferenceArguments.changeCollectionRef(numList);
		    // CHANGES, as DEREFERENCING into ORIGINAL OBJECT via 'set' method on Collection!
			System.out.println(String.format("List contents after changeCollectionRef: %d", numList.get(0)));
				
	}

}
