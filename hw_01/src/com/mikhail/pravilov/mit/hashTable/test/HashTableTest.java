package com.mikhail.pravilov.mit.hashTable.test;

import com.mikhail.pravilov.mit.hashTable.HashTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    private HashTable hashTable = new HashTable();

    @Test
    void putSimpleTest() {
        hashTable.put("el1", "kek");
        assertEquals(1, hashTable.size());
        assertEquals(true, hashTable.contains("el1"));
        assertEquals("kek", hashTable.get("el1"));
        assertEquals(null, hashTable.get("el2"));

        hashTable.put("el2", "kok");
        assertEquals(2, hashTable.size());
        assertEquals(true, hashTable.contains("el2"));
        assertEquals("kek", hashTable.get("el1"));
        assertEquals("kok", hashTable.get("el2"));

        hashTable.put("el3", "kik");
        assertEquals(3, hashTable.size());
        assertEquals(true, hashTable.contains("el3"));
        assertEquals(false, hashTable.contains("el4"));
        assertEquals("kek", hashTable.get("el1"));
        assertEquals("kik", hashTable.get("el3"));

        hashTable.put("el4", "kuk");
        assertEquals(4, hashTable.size());
        assertEquals(true, hashTable.contains("el4"));
        assertEquals("kek", hashTable.get("el1"));
        assertEquals("kok", hashTable.get("el2"));
        assertEquals("kik", hashTable.get("el3"));
        assertEquals("kuk", hashTable.get("el4"));
    }

    @Test
    void putExistedKey() {
        hashTable.put("Existed", "value1");
        assertEquals("value1", hashTable.get("Existed"));
        assertEquals(1, hashTable.size());
        hashTable.put("Existed", "value2");
        assertEquals("value2", hashTable.get("Existed"));
        assertEquals(1, hashTable.size());
        hashTable.put("NotExisted", "value2");
        assertEquals("value2", hashTable.get("Existed"));
        assertEquals(2, hashTable.size());
    }

    @Test
    void remove() {
        hashTable.put("el1", "kek");
        hashTable.put("el2", "kok");
        hashTable.put("el3", "kik");
        hashTable.put("el4", "kuk");

        String removedValue;

        removedValue = hashTable.remove("el2");
        assertEquals("kok", removedValue);
        assertEquals(3, hashTable.size());
        assertEquals(false, hashTable.contains("el2"));

        removedValue = hashTable.remove("el5");
        assertEquals(null, removedValue);
        assertEquals(3, hashTable.size());
        assertEquals(false, hashTable.contains("el5"));
    }

    @Test
    void clear() {
        hashTable.put("el1", "kek");
        hashTable.put("el2", "kok");
        hashTable.put("el3", "kik");
        hashTable.put("el4", "kuk");

        hashTable.clear();
        assertEquals(0, hashTable.size());
        assertEquals(false, hashTable.contains("el1"));
    }

    @Test
    void putRemoveManyElements() {
        String removedValue;

        for (int i = 0; i < 1000; i++)
            hashTable.put("el" + i, "value" + i);
        String previousValue = hashTable.put("el" + 10, "value" + 10);
        assertEquals("value" + 10, previousValue);

        for (int i = 0; i < 1000; i++) {
            removedValue = hashTable.remove("el" + i);
            assertEquals("value" + i, removedValue);
        }
    }

    @Test
    void collisions() {
        String key1 = "AaAa", key2 = "BBBB";
        assertEquals(key1.hashCode(), key2.hashCode());
        hashTable.put(key1, "value1");
        hashTable.put(key2, "value2");
        assertEquals("value2", hashTable.get(key2));
        assertEquals("value1", hashTable.get(key1));
    }
}