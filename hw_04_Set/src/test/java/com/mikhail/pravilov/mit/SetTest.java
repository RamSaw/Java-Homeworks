package com.mikhail.pravilov.mit;

import org.junit.Test;

import static org.junit.Assert.*;

public class SetTest {
    @Test
    public void addIntegers() {
        Set<Integer> integerSet = new Set<>();
        assertFalse(integerSet.contains(0));
        assertEquals(0, integerSet.size());

        integerSet.add(5);
        assertFalse(integerSet.contains(0));
        assertTrue(integerSet.contains(5));
        assertEquals(1, integerSet.size());

        integerSet.add(10);
        assertFalse(integerSet.contains(-1));
        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(10));
        assertEquals(2, integerSet.size());

        integerSet.add(10);
        assertFalse(integerSet.contains(-10));
        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(10));
        assertEquals(2, integerSet.size());

        integerSet.add(-10);
        assertFalse(integerSet.contains(0));
        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(10));
        assertTrue(integerSet.contains(-10));
        assertEquals(3, integerSet.size());

        Set<Integer> manyIntegers = new Set<>();
        int expectedSize = 0;
        for (int i = -100; i <= 100; i++) {
            assertEquals(expectedSize, manyIntegers.size());
            manyIntegers.add(i);
            expectedSize++;
        }

        assertEquals(expectedSize, manyIntegers.size());
        for (int i = -100; i <= 100; i++) {
            assertTrue(manyIntegers.contains(i));
        }
    }

    @Test
    public void addStrings() {
        Set<String> stringSet = new Set<>();
        assertFalse(stringSet.contains("ass"));
        assertEquals(0, stringSet.size());

        stringSet.add("kek");
        assertFalse(stringSet.contains("ass"));
        assertTrue(stringSet.contains("kek"));
        assertEquals(1, stringSet.size());

        stringSet.add("kik");
        assertFalse(stringSet.contains("no element"));
        assertTrue(stringSet.contains("kek"));
        assertTrue(stringSet.contains("kik"));
        assertEquals(2, stringSet.size());

        stringSet.add("kik");
        assertFalse(stringSet.contains("da du da du da"));
        assertTrue(stringSet.contains("kek"));
        assertTrue(stringSet.contains("kik"));
        assertEquals(2, stringSet.size());

        stringSet.add("da du da du da");
        assertFalse(stringSet.contains("ass"));
        assertTrue(stringSet.contains("kek"));
        assertTrue(stringSet.contains("kik"));
        assertTrue(stringSet.contains("da du da du da"));
        assertEquals(3, stringSet.size());

        Set<String> manyStrings = new Set<>();
        int expectedSize = 0;
        for (int i = -100; i <= 100; i++) {
            assertEquals(expectedSize, manyStrings.size());
            manyStrings.add("value " + i);
            expectedSize++;
        }

        assertEquals(expectedSize, manyStrings.size());
        for (int i = -100; i <= 100; i++) {
            assertTrue(manyStrings.contains("value " + i));
        }
    }
}