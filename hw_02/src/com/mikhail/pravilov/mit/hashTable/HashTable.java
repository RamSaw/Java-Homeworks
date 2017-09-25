package com.mikhail.pravilov.mit.hashTable;

import com.mikhail.pravilov.mit.list.List;
import javafx.util.Pair;

import static java.lang.Math.*;

/** Class for realization hash table with separate chaining data structure. */
public class HashTable {
    /**
     * Number of elements stored in hashTable
     */
    private int size = 0;
    /**
     * Array of lists to store elements in hashTable
     */
    private List[] hashTable;

    /**
     * Method initializes hashTable array by list() constructor.
     * @param length the length of array to allocate
     */
    private void initializeHashTable(int length) {
        hashTable = new List[length];
        for (int i = 0; i < hashTable.length; i++)
            hashTable[i] = new List();
    }

    /**
     * Constructor for hashTable class. Sets size to 0
     * and allocates hashTable array of length 1.
     */
    public HashTable() {
        initializeHashTable(1);
    }

    /**
     * Creates hashTable array of more size if current size is more then half of current length.
     * New length is 10 * old length and round up to nearest power of 2.
     */
    private void resize() {
        if ((double)size / hashTable.length < 0.5)
            return;

        List[] copyHashTable = hashTable;
        initializeHashTable((int)pow(2, ceil(log(copyHashTable.length * 10) / log(2))));
        size = 0;
        for (List list : copyHashTable) {
            int index = 0;
            Pair<String, String> keyValue = list.getPairKeyValue(index);
            while (keyValue != null) {
                put(keyValue.getKey(), keyValue.getValue());
                index++;
                keyValue = list.getPairKeyValue(index);
            }
        }
    }

    /**
     * Method to get size field
     * @return size field
     */
    public int size() {
        return size;
    }

    /**
     * Get index in hashTable array by key. Uses hash function of string Object.
     * @param key key to calculate hash function
     * @return index in hashTable array(type <code>int</code>)
     */
    private int getIndexByKey(String key) {
        return abs(key.hashCode() % hashTable.length);
    }

    /**
     * Checks if element with given key exists in hash table
     * @param key key to check for existence
     * @return <code>true</code> if exists, <code>false</code> if not(type <code>boolean</code>)
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * Get value by key.
     * @param key key of which to get value
     * @return value if key exists, if not <code>null</code>.
     */
    public String get(String key) {
        return hashTable[getIndexByKey(key)].getValueByKey(key);
    }

    /**
     * Puts pair key, value to hash table, if key exists then it deletes previous value.
     * Then performs resize of hashTable if needed.
     * @param key key to put
     * @param value value to put
     * @return value that was stored before adding new value, if none was stored then <code>null</code> returns.
     */
    public String put(String key, String value) {
        int index  = getIndexByKey(key);
        String previousValue = hashTable[index].delete(key);
        hashTable[index].add(key, value, 0);
        if (previousValue == null)
            size++;

        resize();
        return previousValue;
    }

    /**
     * Removes key and value if key exists.
     * @param key key to remove
     * @return removed value, if no key exists returns <code>null</code>.
     */
    public String remove(String key) {
        String removedValue = hashTable[getIndexByKey(key)].delete(key);
        if (removedValue != null)
            size--;
        return removedValue;
    }

    /**
     * Removes all elements in hash table.
     */
    public void clear() {
        for (List list : hashTable)
            list.clear();
        size = 0;
    }
}
