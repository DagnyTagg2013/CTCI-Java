package maps;

import java.util.Comparator;

/**
 * Created by daphneeng on 1/24/17.
 */
// ATTN: default public interface!
interface IHomieMap <K,V> {

    int size();
    boolean isEmpty();
    void add(K key, V value);
    V remove(K key);

    void printContents(Comparator<K> comparator);

}
