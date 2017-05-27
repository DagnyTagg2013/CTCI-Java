package basics;

// lazy initialization
// - uses memory on-demand only
// NOTE:  double-checked locking is deprecated as unreliable
public class Singleton2 {

	    // this needs VOLATILE keyword
	    // * allows predictable state visibility across all threads
	    // * only works with ONE writer thread
	    // OTHERWISE, partial construction race-condition may occur across multiple pre-emptive threads
	    /*
		    Volatile:	
			Get a global lock on the variable	Get a global lock on the monitor
			Update the one variable from main memory	Update all shared variables that have been accessed from main memory
			Process some statements
			Write any change of the one variable back to main memory	Write all shared variables that have been changed back to main memory
			Release the lock	Release the lock
        */
	    private static volatile Singleton2 theOne = null;
	    private static volatile int accessCounter = 0;
	    
		private Singleton2() {}
		
		
		// needs SYNCHRONIZED keyword to force ONE writer thread
	    // * AND SERIALIZE if-condition-access!
		// * locks on PER CLASS basis ACROSS ALL INSTANCES
		// * vs on METHOD which locks on any object instance ACROSS ALL METHODS
		// * vs on BLOCK which locks on specific object instance ACROSS ALL METHODs
		public static synchronized Singleton2 getInstance() {
			if (theOne == null) {
				theOne = new Singleton2();
			}
			return theOne;
			
		}
		
		public static synchronized void incrementAccessCounter() {
			// NOTE:  auto-increment is NOT an atomic operation, so need to use synhronized to prevent race-condition
			// ALSO could use AtomicIntger type instead
			accessCounter++;
		}
}
