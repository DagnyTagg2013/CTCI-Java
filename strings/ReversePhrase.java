package strings;

import java.util.Arrays;

/* 
 * NOTE:
 * ALGO is:
 * - reverse one word in-place
 * - search on bounds for successive words; then invoke reverse to create reversed string
 * - reverse WHOLE reversed string to get words readable, but in REVERSED order!
 * CHEAT:  
 * - use String.split()! within ReverseMultipleWordsCheat(...) implementation!
 * 
 * NOTE:  attention to StringBuilder 
 * - setCharAt, AND pre-initialize to set space for swap-indexing on word reversal!
 * NOTE:  attention to String
 * - charAt
 * 
 * CAREFUL:
 * - index 0 to (length - 1)!
 * 
 * 
 */

public class ReversePhrase {
	
	// NOTE:  usage of char[] and Arrays.toString() for reversing without modification of input
	// HOWEVER, Arrays.toString will put commas between elements, and square brackets around content
/*
	public String reverseWord(String input) {
		
		char tmp = '\0';
	    char[] output = new char[input.length()];
		for (int i=0, j=(input.length()-1); i < j; i++, j--) {
			// NOTE:  CIRCULAR order of swap assignments so as not to LOSE info!
			tmp = input.charAt(i);
			output[i] = input.charAt(j); 
			output[j] =  tmp;
		}
		return (Arrays.toString(output));
	}
*/
	
   private static final String WORD_SEPARATOR = " ";
   
   public String reverseWord(String input) {
		
		char tmp = '\0';
		// NOTE:  must PREALLOCATE StringBuilder before setting values in-place; AND 
		//        this NEW leaves INPUT intact!!
		StringBuilder output= new StringBuilder(input);
		// System.out.println(String.format("input length %d, then output length %d", input.length(), output.length()));
		for (int i=0, j=(input.length()-1); i <= j; i++, j--) {
			// NOTE:  CIRCULAR order of swap assignments so as not to LOSE info!
			tmp = input.charAt(i);
			// System.out.println(String.format("i %d and j %d", i, j));
			output.setCharAt(i, input.charAt(j)); 
			output.setCharAt(j, tmp);
		}
		return output.toString();
		
	}
   
   public String reversePhrase(String input) {
	   
	   String reversedWords = reverseWord(input);
	   String realWordsBackwards = reverseMultipleWords(reversedWords);
	   
	   return realWordsBackwards;
   }
	
	// NOTE:  using indexOf; AND
	//        loop with buffered incremental values for idxStartCurrentWord, and idxBeforeNextWord;
	public String reverseMultipleWords(String input) {
		
		StringBuilder output = new StringBuilder();
		
		int idxStartCurrentWord = 0;
		// NOTE:  assume one space separation
		int idxBeforeNextWord = input.indexOf(" ", idxStartCurrentWord);
		String reversedWord = null;
		// NOTE:  GUARD condition is against idxBeforeNextWord!
		while (idxBeforeNextWord != -1) {
			reversedWord = reverseWord(input.substring(idxStartCurrentWord, idxBeforeNextWord));
			output.append(reversedWord);
			// NOTE:  need to handle separator here!
			output.append(WORD_SEPARATOR);
			idxStartCurrentWord = idxBeforeNextWord + 1;
			idxBeforeNextWord = input.indexOf(" ", idxStartCurrentWord);
		}
	
		// NOTE:  handle the trailing word AFTER last space to end of input
		if (idxStartCurrentWord < input.length()) {
			reversedWord = reverseWord(input.substring(idxStartCurrentWord, input.length()));
			output.append(reversedWord);
		}
		
		return output.toString();
	}
	
	public String reverseMultipleWordsCheat(String input) {
		
		StringBuilder output = new StringBuilder();
		String allWords[] = input.split(WORD_SEPARATOR);
		for (int i = allWords.length -1; i >= 0; i--) {
			output.append(allWords[i]);
			output.append(WORD_SEPARATOR);
		}
		return output.toString();
		
	}
	
	public static void main(String args[]) {
		
		ReversePhrase reverser = new ReversePhrase();
		
		// TEST1:  single word
		String input1 = "Grizzly";
		String output1 = reverser.reverseWord(input1);
		System.out.println(String.format("For input:  %s", input1));
		System.out.println(String.format("Reversed is:  %s", output1));
		
		// TEST2:  odd number of 3 words
		String input2 = "Grizzlies poop alot";
		String output2 = reverser.reversePhrase(input2);
		System.out.println(String.format("For input:  %s", input2));
		System.out.println(String.format("Reversed is:  %s", output2));
		
		// TEST3:  even number of 2 words
		String input3 = "Grizzlies poop";
		String output3 = reverser.reverseMultipleWordsCheat(input3);
		System.out.println(String.format("For input:  %s", input3));
		System.out.println(String.format("Reversed is:  %s", output3));
		
		// TEST4:  empty string
		String input4 = "";
		String output4 = reverser.reverseMultipleWords(input4);
		System.out.println(String.format("For input:  %s", input4));
		System.out.println(String.format("Reversed is:  %s", output4));

	}
}
