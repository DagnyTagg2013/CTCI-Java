
/*

    HOMIEMAP is a home-grown hashmap implementation

    * WTF:  HashCode that doesn't blow up Array index range

    * ALWAYs override equals, then hashcode for DEEP comparisions;
    * - MISS hashcode means can add to HashMap, but NOT retrieve with MATCHING query object data that has UNIQUE address
    * - MISS equals means can NOT retrieve within collision list
    * BOTH DEFAULT to testing on SHALLOW object references rather than internal details!

    * ATTN:  with LIST, test for EMPTY, HEAD-single, non-Empty many elements, begin-end-middle

    * PREFER java.collections List over primitive ARRAY
    for generics type-erasure occurs (after) COMPILETIME, and (before) RUNTIME;
    and we WANT to typecheck at COMPILE-time!
    - http://efectivejava.blogspot.com/2013/07/item-25-prefer-lists-to-arrays.html
    - http://code.stephenmorley.org/articles/java-generics-type-erasure/
    e.g.
    Object[] objectArray = new Integer[1];
    objectArray[0] = "str"; // RUNTIME failure
    vs
    List<Object> ol = new ArrayList<Integer>();
    ol.add("str");  // COMPILE-TIME failure

    * Generic Collections cannot be SUBCLASSED; but, you CAN allow them to work on related generic type elements via:
    i.e  List<TypeA> is neither a subtype nor a supertype of List<TypeB>
    e.g. List<String> is not a subtype of List<Object> since you cannot put Strings into List<Object>
    HOWEVER, say we want for generic STACK DATA STRUCTURE
    => CONSUMER: EXTENDS type T, if works on T, then also ACCEPTS SUBCLASS EXTENSIONs!
    Stack<Number> numStack = new Stack<Number>();
    Iterable<Integer> integers = ...;
    numberStack.pushAll(integers);
    => to NOT get a compile-time type error:
    public void pushAll(Iterable<? extends E> src) ... not just <E>
    => PRODUCER: SUPER type T, if works on T, than also ASSIGNABLE to SUPER
    Stack<Number> numberStack = new Stack<Number>();
    Collection<Object> objects = ...;
    numberStack.popAll(objects);
    => to NOT get a compile-time type error:
    public void popAll(Collection<? super E> dst) ... not just <E>

    PRODUCE can produce SUPER  of ideal input class X  to ASSIGN TO
    CONSUME can accept EXTENDs of ideal input class X to OPRATE ON   since only cares about BASE class!

    * HOWTO:  using a type-checked storage Array without type erasure-related compile-time exceptions
    http://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java

    - can suppress compiler warnings; but then enforce at runtime since type is ERASED at
    runtime by JVM so it can work with other libraries
    e.g.  must be OBJECT, not E or ERROR on generic array creation
    => E[] arr = (E[])new Object[INITIAL_ARRAY_LENGTH];
    Should you need to return an array of a generic type to other code,
    the reflection Array class you mention is the right way to go.
    => public class GenSet(Class<E> c, int s) {   ...
    -  E get(int i) { return a[i]; }
    * ATTN:  create instances of array of type only known at runtime!
    => CTOR public GenSet(Class<E> c, int s) {
        @SuppressWarnings("unchecked")
        final E[] a = (E[]) Array.newInstance(c, s);
        this.a = a;
    }
    - private E[] a;

    * TODO:  Equals/HashCode for Element type
    * TODO:  contrast with Consistent Hashing
    * TODO:  SORTING with Comparable implementation
    * TODO:  Reflection vs Introspection
    * TODO:  concurrent Collections
    * TODO:  polymorphism vs inheritance test; WHICH overrides!
    * TODO:  template vs strategy test
    * TODO:  JDK 1.8 Lambdas and List Iterator syntax from DZONE!, AND Date type or 5 most important developments!
    * TODO:  Syncrhonize, Wait, Notify
    * TODO:  Thread lifecycle and Callable vs Runnable
    * TODO:  Futures and Async Futures in Java

    * SORTING/ORDERING
      - by CompareTo natural ordering; and using JDK1.8!
        or Comparable function for customized ordering
      - https://praveer09.github.io/technology/2016/06/21/writing-comparators-the-java8-way/
      - https://www.tutorialspoint.com/java/java_using_comparator.htm
      - http://stackoverflow.com/questions/1463284/hashset-vs-treeset

 */
package maps;

// ATTN:  ArrayList instead of primitive Array to support GENERICS type

import java.util.ArrayList;
import java.util.Comparator;


// ATTN:  need to genericize implementation!
public class HomieMapImpl<K, V> implements IHomieMap<K, V> {

    // Array of HashBuckets, with each HashBucket containing a List of Elements
    private ArrayList<HashNode<K, V>> bucketArray;

    private final int INIT_NUM_BUCKETS = 10;

    // LOAD is % of allocated storage actually used to store elements!
    private final double LOAD_FACTOR = 0.7;
    private final int EXPANSION_FACTOR = 2;

    // Current MAX capacity of Buckets allocated
    // ATTN:  this is DYNAMIC!
    private int numBuckets;
    // Current number of ACTUAL Data Elements stored across buckets!
    private int numElements;

    public HomieMapImpl() {
        bucketArray = new ArrayList<HashNode<K, V>>();
        numBuckets = INIT_NUM_BUCKETS;
        numElements = 0;

        // ATTN, initialize to EMPTY chains at each bucket;
        //         to SIMULATE STATIC allocate memory PLACEHOLDER while
        //         implementing with ArrayList!
        for (int i = 0; i < numBuckets; i++) {
            bucketArray.add(null);
        }
    }

    public int size() {
        return numElements;
    }

    public boolean isEmpty() {
        return numElements == 0;
    }

    // ATTN:  hash function to find index to hash bucket for a given input key
    //        note DISTINCTION between key and hashcode!
    private int getBucketHashIndex(K key) {
        // ATTN:  initialize numBuckets!
        // ATTN:  use hashCode for Primitives; otherwise need to implement for OTHER types!
        // GOTCHA -- CANNOT simply accept hashcode as OVERFLOW can occur to cyle into NEGATIVE int range, out-of-bounds for Array index!
        // - http://stackoverflow.com/questions/9249983/hashcode-giving-negative-values
        // int hashCode = key.hashCode();

        // START with PRIME Number, then ADD multiples of 31 to OFFSET!
        int hashCode = 17;
        hashCode = 31 * hashCode + key.hashCode();

        // ATTN to correct for overflow
        hashCode = Math.abs(hashCode);
        // ATTN:  for UNIFORM DISTRIBUTION; so MOD places it within buckets!
        int index = hashCode % numBuckets;
        return index;
    }

    // METHOD to ADD an element, which will OVERWRITE any pre-existing value at the SAME key!
    // ATTN:  4 cases:
    // CASE 1:  BUCKET ARRAY lookup hashcode has NO list
    // - SET a NEW HEAD node on buckets at the hashIndex
    // CASE 2:  SCAN of List finds EQUAL KEY-COLLISION
    // - OVERWITE value
    // CASE 3:  NO COLLISION b/c key NOT found in this bucket
    // - PREPEND to head of value List for bucket
    // CASE 4:
    // - CHECK for LOAD to TRIGGER EXPANSION of BUCKET ARRAY and RE-HASH of old elements into NEW-EXPANSION
    public void add(K key, V value) {

        // FIRST, get bucket index
        int bucketHashIndex = getBucketHashIndex(key);

        // ATTN:  retrieve HEAD of list at that bucket; and save to use to reset scan ptr later!
        HashNode<K, V> head = bucketArray.get(bucketHashIndex);

        // CASE 1: head == null because NO PRE-EXISTING Value stored in this bucket; so store at the HEAD of a new List!
        if (head == null) {
            HashNode<K, V> newNode = new HashNode(key, value);
            bucketArray.set(bucketHashIndex, newNode);
            numElements++;
            // EXIT after updating counter of number elements!
            return;
        }

        // ATTN:  cache list HEAD and re-initialize scan pointer based on HEAD
        // CASE 2:  COLLISION -- pre-existing values stored for bucket; so iterate thru to list find a Key match, tjem UPDATE if found
        HashNode<K, V> scan = head;
        while (scan != null) {

            // ATTN:  DEEP equals rather than SHALLOW reference comparison!
            // Handles UPDATE here
            if (scan.key.equals(key)) {
                // CASE 2:  COLLISION, so OVERWRITE any prior value! NOTE that NO need to UPDATE element counter in this case!
                scan.value = value;
                break;
            }

            scan = scan.next;
        }

        // CASE 3:  NO COLLISION because Key NOT found within this Bucket BUT,
        //          there's a pre-exisiting list to append/prepend to!
        // TRICKY:  EXPENSIVE to APPEND, need pointer to TAIL element, need SCAN to find LAST element to modify!
        //          so MODIFY to PREPEND since order doesn't matter for us here!
        if (scan == null) {

            // iterate thru List to find TAIL
            // NOW, just add NEW node to list, and update associated counter!
            HashNode<K, V> newNode = new HashNode(key, value);
            newNode.next = head;
            // ATTN:  reset HEAD of collision list for bucket!
            this.bucketArray.set(bucketHashIndex, newNode);
            // prior.next = newNode;
            numElements++;
        }

        // GOTCHA: (AFTER) all cases accounted for; test for final TRIGGER for expanding table!
        if (isTriggeredHashBucketExtension()) {
            extendHashTable();
        }

    }

    private boolean isTriggeredHashBucketExtension() {

        // if ratio of number of stored elements to number of existing Buckets is near loadFactor of 0.7,
        // then go ahead and create a NEW LARGER SET of Hashbuckets; BUT copy over all PRIOR contents to NEW Hashbuckets.
        // i.e. NOT AS EFFICIENT as consistent hashing
        if (numElements / numBuckets > LOAD_FACTOR) {
            return true;
        } else {
            return false;
        }

    }

    // ATTENTION!
    //- once LoadFactor is reached; underlying data structure is EXTENDED by 2x!
    private void extendHashTable() {

        // FIRST, cache OLD values
        ArrayList<HashNode<K, V>> priorBucketArray = this.bucketArray;
        int priorNumBuckets = this.numBuckets;
        int priorNumElements = this.numElements;

        // THEN allocate NEW extended storage based on CURRENT variables!
        this.bucketArray = new ArrayList<HashNode<K, V>>();
        numBuckets = priorNumBuckets * EXPANSION_FACTOR;
        numElements = 0;
        // ATTN:   initialize to EMPTY chains at each bucket;
        //         to SIMULATE STATIC allocate memory PLACEHOLDER while
        //         implementing with ArrayList!
        for (int i = 0; i < numBuckets; i++) {
            bucketArray.add(null);
        }

        // NOW, REHASH and COPY ELEMENTS OVER
        // TODO:  JDK 1.8 Stream iterator syntax!
        // ATTN:  handle case where list element MISSING in bucket (via FIRST initialization to NULL placeholder!)
        //          THUS, essentially EMPTY list INSTEAD of DUMMY first HashNode!
        //          IMPORTANT to tell difference between ONE-element list vs NOTHING!
        // ATTN:  ArrayList is more usable than primitive ARRAY for HashBuckets, but is DYNAMIC,
        //          so need to initialize NULL PLACEHOLDERS ABOVE to simulate STATIC/
        //          Array storage!
        // CASE 1:  aBucket NULL -- so copy nothing
        // CASE 2:  aBucket contains non-empty LIST, so iterate through THAT list and add each element one-at-a-time!
        //          where KEY can REHASH to a DIFFERENT HASH Storage location in NEW EXPANDED table!
        // TODO:  TEST to iterate through undordered MAP elements!
        for (HashNode<K, V> aBucket : priorBucketArray) {

            if (aBucket != null) {

                // TRICKY, handle SUB-STRUCTURE NESTED List also!
                HashNode<K, V> scanListInBucket = aBucket;
                while (scanListInBucket != null) {

                    // ATTN:  reuse of pre-existing function to update correlated counter data elements!
                    //        with EMBEDDED RE-HASH to NEW hash Bucket and List location
                    // TRICKY: not JUST a matter of copying over OLD BucketLists to new Buckets!
                    this.add(scanListInBucket.key, scanListInBucket.value);
                    scanListInBucket = scanListInBucket.next;
                }

            } // end of handling non-null Bucket entry  ie, there ARE elements to copy over!

            // SO, DO-NOTHING on null Bucket Entry

        }  // end loop thru EXISTING Bucket entries.

    }

    // METHOD to GET an element
    // ATTN:  returns null if NOT FOUND
    // ATTN:  3 STEPs
    // - get hashcode
    // - lookup bucket v
    // - scan list for value
    public V get(K key) {

        V foundValue = null;

        int bucketHashIndex = getBucketHashIndex(key);

        HashNode<K, V> scan = this.bucketArray.get(bucketHashIndex);

        while (scan != null) {

            if (scan.key == key) {
                foundValue = scan.value;
                break;
            }

            // ATTN!  ITERATE!
            scan = scan.next;
        }

        return foundValue;
    }

    // ATTN:  3 cases
    // CASE 1: no MATCH key found, so do nothing
    public V remove(K key) {

        int bucketHashIndex = getBucketHashIndex(key);

        HashNode<K, V> scan = this.bucketArray.get(bucketHashIndex);

        // ATTN: initialize PREVIOUS as dependency for single-linked-list item removal;
        //       BUT scan on CURRENT!
        // NOTE:  cannot reuse get as having to track PREV pointer!
        HashNode<K, V> prev = null;
        while (scan != null) {

            // on FOUND, do the SKIP-REMOVAL
            if (scan.key.equals(key))
                // FOUND
                break;

            // ATTN:  CACHE PREV on getting NEXT!
            prev = scan;
            scan = scan.next;
        }

        // CASE 1: NOT FOUND
        // - scanned to END of hashcode-collisions list
        // - OR NULL list at Bucket
        // ATTN:  return immediately if found!
        if (scan == null)
            return null;

        // CASE 2:  FOUND as HEAD of BucketList, so set BUCKET to SET HeAD as NEXT to SKIP FOUND node!
        if (prev == null)
            bucketArray.set(bucketHashIndex, scan.next);
        else
            // CASE 3: FOUND in MIDDLE of BucketList
            // set point to SKIP OVER CURRENT!
            prev.next = scan.next;

        // reduce elements count
        numElements--;

        // returns value FOUND
        return scan.value;
    }

    // ATTN:  print UNORDERED COLLECTION HASHMAP Map entries in KEY order
    // - http://stackoverflow.com/questions/3605237/how-print-out-the-contents-of-a-hashmapstring-string-in-ascending-order-based
    // v.s. rebuilding that same functionality:
    // - https://dzone.com/articles/sorting-java-arraylist
    // - http://stackoverflow.com/questions/23701943/sorting-arraylist-with-lambda-in-java-8
    // - http://stackoverflow.com/questions/31432948/print-list-items-with-java-8-api
    // - http://www.adam-bien.com/roller/abien/entry/java_8_from_a_for
    // - https://www.mkyong.com/java8/java-8-lambda-comparator-example/
    // - http://stackoverflow.com/questions/24436871/very-confused-by-java-8-comparator-type-inference for compound
    // TODO:  make this work for GENERIC type K-comparator!
    public void printContents(Comparator<K> comparator) {

    // TODO:  Review print from API for Java collection for HashMap
        /*
        this.bucketArray.entrySet()
        .stream()
        .sorted(Comparator.comparing(Map.Entry::getKey)
        .forEach(System.out::println);
        */

        // ATTN:  Simulating our home-grown version

        // FIRST:  EXTRACT NON-NULL unordered KEYs
        ArrayList<K> keys  = new ArrayList<K>();
        for (HashNode<K, V> aHashBucket:this.bucketArray) {
            // ATTN:  Need to scan LIST WITHIN bucket
            HashNode<K, V> scan = aHashBucket;
            // SKIPs if NULL encountered
            while (scan != null) {
                // save key
                keys.add(scan.key);
                scan = scan.next;
            }
        }

        ArrayList<K> undorderedKeysList = keys;
        // JDK1.8 LAMBDA to PRINTOUT unordered keys list!
        System.out.println("***** Unordered Keys List! *****");
        undorderedKeysList
                .stream()
                .forEach( (oneEntry) ->  {
                            System.out.println(String.format("Lambda Print Key {%s}", oneEntry.toString()));
                        }
                );


        // SORTING via JDK1.8 LAMBDAS!
        keys.sort(comparator);

        // vs COPY to sortedKeyList and OVERRIDING compare!
        /*
        ArrayList<K> sortedKeyList =  (ArrayList<K>) keys.subList(0, keys.size());
        // NOTE: in-place Sort
        Collections.sort(sortedKeyList,
                         new Comparator <String> {
                            @Override
                            public int compare(String lhs, String rhs) {
                                return (lhs.compareTo(rhs));
                            }
                         });
        */


        // NOW LOOKUP in KEY-ORDERED FORMAT and APPEND to ArrayList to construct orderedEntryList!
        ArrayList<HashNode<K,V>> orderedEntryList = new ArrayList<HashNode<K, V>>();
        for (K aKey: keys) {
            // REUSE get() operation, which translates Key to hashcode, then MATCHES on element in Collision-List!
            V aValue = this.get(aKey);
            if (aValue != null) {
                // ATTN:  syntax for creating new HashNode according to ctor input types!
                orderedEntryList.add(new HashNode<>(aKey, aValue));
            }
        }

        // GOTCHA:  ALTERNATIVELY, construct-add to a TreeSet and extract elements in-order!
        // or N log N construction!

        // JDK1.8 LAMBDA to PRINTOUT ORDERED ENTRY LIST!
        System.out.println("***** Ordered Entries List! *****");
        orderedEntryList
                .stream()
                .forEach( (oneEntry) ->  {
                                            System.out.println(String.format("Lambda Print Entry {%s}", oneEntry.toString()));
                                         }
                        );

        // Just dumping UNORDERED Entry values below!
        /*
        for (HashNode<K, V> aHashBucket:this.bucketArray) {
            HashNode<K, V> scan = aHashBucket;
            while (scan != null) {
                System.out.println(String.format("KEY-VAL:  {0} - {1}", scan.key, scan.value));
                scan = scan.next;
            }
        }
        */

    }

    // TODO: TEST THIS!
    // Driver method to test Map class
    public static void main(String[] args)
    {

        System.out.println(args);

        System.out.println("***** HashMap Testing! *****");

        System.out.println("===== TEST SET 1:  Primitive Key-Values");


        /* TODO:  CASES
                  - create empty,
                    one,
                    several elements
                  - remove from empty,
                    one,
                    several
        */

        // ===== CASE 1.0:  Initialize Hashmap with values
        IHomieMap<String, Integer> hmap = new HomieMapImpl<String, Integer>();
        // ADDs FIRST ELEMENT, no pre-exist HashBucket collision list
        hmap.add("Dagobah", 1 );
        // ADDs SECOND ELEMENT, no pre-exist HashBucket collision list; initialize that list
        hmap.add("Tatooine", 2 );
        // ADDs THIRD ELEMENT, hashcode collision; PREPEND to collision list
        hmap.add("Alderaan", 3 );
        // ADDs FOURTH element, no pre-exist HashBucket collision list; initialize that list
        hmap.add("Hoth", 4 );
        hmap.add("Naboo", 5);
        System.out.println(String.format("CASE 1.1 => Initial Map size should be 5, with KEY-SORTED contents:  {%d}\n",
                                         hmap.size()));
        Comparator<String> stringComparator = Comparator.naturalOrder();
        hmap.printContents(stringComparator);

        // ===== CASE 2.0:  Remove values

        // ===== CASE 2.0:  Remove from empty
        IHomieMap<String, Integer> empty = new HomieMapImpl<String, Integer>();
        Integer valueHoth1 = empty.remove("Hoth");
        System.out.println(String.format("CASE 2.1 => Map size should be unchanged from empty, and is actually:  {%d}\n", hmap.size()));
        System.out.println(String.format("CASE 2.1 => No value was found to remove; and returned value is actually null:  {%b}\n", (valueHoth1 == null)));


        Integer valueHoth2 = hmap.remove("Hoth");
        Integer valueNaboo = hmap.remove("Naboo");
        System.out.println(String.format("CASE 2.2 => Reduced Map size should be 3; and is actually:  {%d}\n", hmap.size()));
        System.out.println(String.format("CASE 2.2 => Value of Naboo should be 5; and is actually:  {%d}\n", valueNaboo));


        // TODO: test EXPANSION trigger
        // ===== CASE 3.0:  EXPAND on HITTING BOUND!
        System.out.println("===== TEST SET 2:  Structure Key-Values");

    }
}
