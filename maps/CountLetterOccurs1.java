package maps;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * GIVEN:
 * - input String
 * - any sequence of characters
 * - UNSORTED
 *
 * FIND:
 * - number of occurrences of character
 * - with order of occurrences listed; matching order FIRST found in input string 
 * 
 * NOTE:  conversions back and forth between String and other types
 * Integer.toString(i) or String.valueOf(i)
 */
public class CountLetterOccurs1 {
	
	public Map<String, Integer> countOccurs(String input) {
		
		if ((input == null) || input.isEmpty())
			throw new IllegalArgumentException("input must not be empty string or null.");
		
		// NOTE:  var initialization
		Integer existCount = null;
		String key = null;
		// NOTE:  order-sensitive LinkedHashMap
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (int i=0; i < input.length(); i++) {
			// NOTE: conversion from char to String for use in Collections API!
			key = String.valueOf(input.charAt(i));
			existCount = result.get(key);
			// ATTN:  takes care of FIRST-OCCURRENCE case AND NEXT case!
			if (existCount == null) {
				result.put(key, 1);
			}
			else {
				result.put(key, existCount+1);
			}
			
		}
		return result;
	}
	
	public void printMap(Map<String, Integer> input) {
		
        for (Map.Entry<String,Integer> oneEntry : input.entrySet()) {
        	System.out.println(oneEntry.getKey() + " has num occurences: " + oneEntry.getValue() + ", ");
        }
        
	}
	
	public static void main(String args[]) {
		
		CountLetterOccurs1 counter = new CountLetterOccurs1();
		
		// CASE 1:  
		System.out.println("\nCASE1:  \n");
		String input1 = "cccbba";
		// expected output is MAP ordered c->3, b->2, a->1
		Map<String, Integer> results1 = counter.countOccurs(input1);
		counter.printMap(results1);
		
		// CASE 2: 
		System.out.println("\nCASE2:  \n");
		String input3 = " ,\n";
		// expected output is count of 1 for space, comma, carriage-return char 
		Map<String, Integer> results3 = counter.countOccurs(input3);
		counter.printMap(results3);
		
		// CASE 3:
		System.out.println("\nCASE3:  \n");
		String input2 = "";
		// expected output is IllegalArgumentException
		Map<String, Integer> results2 = counter.countOccurs(input2);
		counter.printMap(results2);
		
	}
}
