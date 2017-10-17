package com.mikhail.pravilov.mit.hashMap;

import com.mikhail.pravilov.mit.set.Set;
import org.junit.Test;

import java.util.Map.Entry;

import static org.junit.Assert.*;

public class HashMapTest {
    @Test
    public void entrySet() throws Exception {
        HashMap<Integer, String> integerStringHashMap = new HashMap<>();
        integerStringHashMap.put(0, "value0");
        integerStringHashMap.put(1, "value1");
        integerStringHashMap.put(2, "value2");
        integerStringHashMap.put(3, "value3");
        Set<Entry<Integer, String>> myEntrySetIntegerString = integerStringHashMap.entrySet();
        int i = 0;
        for (Entry<Integer, String> entry : myEntrySetIntegerString) {
            Integer expectedKey = i;
            assertEquals(expectedKey, entry.getKey());
            String expectedValue = "value" + i;
            assertEquals(expectedValue, entry.getValue());
            i++;
        }
        assertTrue(integerStringHashMap.containsKey(0));
        assertTrue(integerStringHashMap.containsKey(1));
        assertTrue(integerStringHashMap.containsKey(2));
        assertTrue(integerStringHashMap.containsKey(3));
        assertTrue(integerStringHashMap.containsValue("value0"));
        assertTrue(integerStringHashMap.containsValue("value1"));
        assertTrue(integerStringHashMap.containsValue("value2"));
        assertTrue(integerStringHashMap.containsValue("value3"));
    }
}