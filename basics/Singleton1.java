package basics;

/*
 *
 * 
 * PROBLEMS with:
 * - global resource locator; hides explicit object dependency instantiation order
 * - does NOT follow constructor composition dependency-injection for order-sensitive creation
 * - does NOT provide clear construction dependency interfaces that can be easily test-mocked for unit=testing
 * - multiple classloaders may exist in a single JVM, so no longer true singleton
 * - no control of memory management
 * - no control of startup/shutdown behavior
 * - if implements serializeable with read-write, 
 *   then need to implement readResolve(...) to ensure NOT creating a separate instance, 
 *   but to resolve to the singleton one!
 *   
 * ARTICLES:
 * - http://misko.hevery.com/2008/08/17/singletons-are-pathological-liars/
 * - http://googletesting.blogspot.com/2008/08/where-have-all-singletons-gone.html
 *   
 * PREFER
 * - dedicated configuration service to query info from
 *
 * EAGER-instantiation
 * - consumes memory even if not referenced later
 */

/*
 * This implementation is thread-safe; with no issues of race conditions on partial construction
 * This is because ClassLoader instantiates STATIC member BEFORE threads are started
 * AND no need to do while/conditional condition checks!
 */
public class Singleton1 {

	private static volatile Singleton1 theOne = new Singleton1();

	private Singleton1() {
	}

	// NOTE:  in STATIC method, the CLASS is the MONITOR object lock!
	private static Singleton1 getInstance() {
		return theOne;
	}
}

