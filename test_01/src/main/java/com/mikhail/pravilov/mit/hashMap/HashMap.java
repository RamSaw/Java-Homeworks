package com.mikhail.pravilov.mit.hashMap;

import com.mikhail.pravilov.mit.list.List;
import com.mikhail.pravilov.mit.set.Set;
import com.mikhail.pravilov.mit.entry.MyEntry;
import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.Map;

import static java.lang.Math.*;

public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {
    /** Number of elements stored in hashTable */
    private int size = 0;
    /** Array of lists to store elements in hashTable */
    private List<Integer, List<K, V>> hashTable;

    private Set<Entry<K, V>> elementsByAddingOrder;

    /**
     * Method initializes hashTable array by list() constructor.
     * @param length the length of array to allocate
     */
    private void initializeHashTable(int length) {
        hashTable = new List<>();
        for (int i = 0; i < length; i++)
            hashTable.add(i, new List<>(), i);
        elementsByAddingOrder = new Set<>();
    }

    /**
     * Constructor for hashTable class. Sets size to 0
     * and allocates hashTable array of length 1.
     */
    public HashMap() {
        initializeHashTable(1);
    }

    /**
     * Creates hashTable array of more size if current size is more then half of current length.
     * New length is 10 * old length and round up to nearest power of 2.
     */
    private void resize() {
        if ((double)size / hashTable.getSize() < 0.5)
            return;

        List<Integer, List<K, V>> copyHashTable = hashTable;
        initializeHashTable((int)pow(2, ceil(log(copyHashTable.getSize() * 10) / log(2))));
        size = 0;
        for (int i = 0; i < copyHashTable.getSize(); i++) {
            List<K, V> list = copyHashTable.getValueByKey(i);
            int index = 0;
            Pair<K, V> keyValue = list.getPairKeyValue(index);
            while (keyValue != null) {
                put(keyValue.getKey(), keyValue.getValue());
                index++;
                keyValue = list.getPairKeyValue(index);
            }
        }
    }

    /**
     * Get index in hashTable array by key. Uses hash function of string Object.
     * @param key key to calculate hash function
     * @return index in hashTable array(type <code>int</code>)
     */
    private int getIndexByKey(K key) {
        return abs(key.hashCode() % hashTable.getSize());
    }

    @Override
    public V put(K key, V value) {
        int index  = getIndexByKey(key);
        V previousValue = hashTable.getValueByKey(index).delete(key);
        hashTable.getValueByKey(index).add(key, value, 0);
        if (previousValue == null) {
            size++;
            elementsByAddingOrder.add(new MyEntry<>(key, value));
        }

        resize();
        return previousValue;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return elementsByAddingOrder;
    }
}
