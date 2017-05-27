package maps;

/**
 * Created by daphneeng on 1/24/17.
 */
// LinkedList via Node
// ATTN:  GENERICS, NEXT pointer
// ATTN:  contains KEY as save distinction between this and HashCode; AND need Key to MATCH-EXACT for collision-overwrite!
public class HashNode<K, V> {

    K key;
    V value;

    // ATTN: Reference to next node
    HashNode<K, V> next;

    // ATTN:  save distinct key, value here!
    // Constructor
    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
        // CORRECTION, and note this type is just a REFERENCE to NEXT hashnode!
        this.next = null;
    }

    /************ ATTN:  Use Code GENERATOR to generate getters, setters, hashcode, equals!  ***********************/
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public HashNode<K, V> getNext() {
        return next;
    }

    public void setNext(HashNode<K, V> next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashNode<?, ?> hashNode = (HashNode<?, ?>) o;

        if (!key.equals(hashNode.key)) return false;
        if (!value.equals(hashNode.value)) return false;
        return next.equals(hashNode.next);

    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + next.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "HashNode{" +
                "value=" + value +
                ", key=" + key +
                '}';
    }
}