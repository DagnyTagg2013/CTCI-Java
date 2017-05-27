package basics;

import java.util.ArrayList;
import java.util.Collections;



     // NOTE:  on RETURN, function-internal b instance is POPPED from stack
     //        so b[0] was set on DEREFERENCING calling parameter b; and should now be 15
	 public class ArraysNRefs {
		   public static void main(String[] argv){
		     int[] b = new int[1];
		     b[0] = 10;
		     ArraysNRefs.doStuff(b);
		     System.out.println("b[0] is " + b[0]);
		   }
		 
		   public static void doStuff(int[] b){
		     b[0] = 15;
		     b = new int[1];
		     b[0] = 20;
		   }
		 }


	 
	 