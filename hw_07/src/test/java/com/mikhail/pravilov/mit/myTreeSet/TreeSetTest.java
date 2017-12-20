package com.mikhail.pravilov.mit.myTreeSet;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TreeSetTest {
    private MyTreeSetImplementation<Integer> integers;
    private TreeSet<Integer> treeSetIntegers;
    private ArrayList<Integer> testIntegerValues;

    private MyTreeSetImplementation<String> strings;
    private TreeSet<String> treeSetStrings;
    private List<String> testStringValues = Arrays.asList("", "aa", "abc", "b", "dcba", "w");

    @Before
    public void setUp() {
        integers = new MyTreeSetImplementation<>((e1, e2) -> e2 - e1);
        treeSetIntegers = new TreeSet<>((e1, e2) -> e2 - e1);
        integers.add(10);
        treeSetIntegers.add(10);
        integers.add(2);
        treeSetIntegers.add(2);
        integers.add(15);
        treeSetIntegers.add(15);
        integers.add(8);
        treeSetIntegers.add(8);
        integers.add(9);
        treeSetIntegers.add(9);
        integers.add(14);
        treeSetIntegers.add(14);

        strings = new MyTreeSetImplementation<>();
        treeSetStrings = new TreeSet<>();
        strings.add("abcd");
        treeSetStrings.add("abcd");
        strings.add("abc");
        treeSetStrings.add("abc");
        strings.add("dcba");
        treeSetStrings.add("dcba");
        strings.add("a");
        treeSetStrings.add("a");
        strings.add("b");
        treeSetStrings.add("b");

        testIntegerValues = new ArrayList<>();
        for (int i = 0; i < 30; i++)
            testIntegerValues.add(i);
    }

    @Test
    public void addIntegers() {
        MyTreeSetImplementation<Integer> integerSet = new MyTreeSetImplementation<>();
        assertFalse(integerSet.contains(0));
        assertEquals(0, integerSet.size());
        assertTrue(integerSet.isEmpty());

        assertTrue(integerSet.add(5));
        assertFalse(integerSet.contains(0));
        assertTrue(integerSet.contains(5));
        assertEquals(1, integerSet.size());
        assertFalse(integerSet.isEmpty());

        assertFalse(integerSet.add(5));
        assertFalse(integerSet.contains(0));
        assertTrue(integerSet.contains(5));
        assertEquals(1, integerSet.size());
        assertFalse(integerSet.isEmpty());

        assertTrue(integerSet.add(10));
        assertFalse(integerSet.contains(-1));
        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(10));
        assertEquals(2, integerSet.size());
        assertFalse(integerSet.isEmpty());

        assertFalse(integerSet.add(10));
        assertFalse(integerSet.contains(-10));
        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(10));
        assertEquals(2, integerSet.size());
        assertFalse(integerSet.isEmpty());

        assertTrue(integerSet.add(-10));
        assertFalse(integerSet.contains(0));
        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(10));
        assertTrue(integerSet.contains(-10));
        assertEquals(3, integerSet.size());
        assertFalse(integerSet.isEmpty());

        assertFalse(integerSet.add(-10));
        assertFalse(integerSet.contains(0));
        assertTrue(integerSet.contains(5));
        assertTrue(integerSet.contains(10));
        assertTrue(integerSet.contains(-10));
        assertEquals(3, integerSet.size());
        assertFalse(integerSet.isEmpty());

        MyTreeSetImplementation<Integer> manyIntegers = new MyTreeSetImplementation<>();
        int expectedSize = 0;
        for (int i = -100; i <= 100; i++) {
            assertEquals(expectedSize, manyIntegers.size());
            assertTrue(manyIntegers.add(i));
            expectedSize++;
        }

        assertEquals(expectedSize, manyIntegers.size());
        for (int i = -100; i <= 100; i++) {
            assertTrue(manyIntegers.contains(i));
        }
    }

    @Test
    public void addStrings() {
        MyTreeSetImplementation<String> stringSet = new MyTreeSetImplementation<>();
        assertFalse(stringSet.contains("ass"));
        assertEquals(0, stringSet.size());
        assertTrue(stringSet.isEmpty());

        assertTrue(stringSet.add("kek"));
        assertFalse(stringSet.contains("ass"));
        assertTrue(stringSet.contains("kek"));
        assertEquals(1, stringSet.size());
        assertFalse(stringSet.isEmpty());

        assertFalse(stringSet.add("kek"));
        assertFalse(stringSet.contains("ass"));
        assertTrue(stringSet.contains("kek"));
        assertEquals(1, stringSet.size());
        assertFalse(stringSet.isEmpty());

        assertTrue(stringSet.add("kik"));
        assertFalse(stringSet.contains("no element"));
        assertTrue(stringSet.contains("kek"));
        assertTrue(stringSet.contains("kik"));
        assertEquals(2, stringSet.size());
        assertFalse(stringSet.isEmpty());

        assertFalse(stringSet.add("kik"));
        assertFalse(stringSet.contains("da du da du da"));
        assertTrue(stringSet.contains("kek"));
        assertTrue(stringSet.contains("kik"));
        assertEquals(2, stringSet.size());
        assertFalse(stringSet.isEmpty());

        assertTrue(stringSet.add("da du da du da"));
        assertFalse(stringSet.contains("ass"));
        assertTrue(stringSet.contains("kek"));
        assertTrue(stringSet.contains("kik"));
        assertTrue(stringSet.contains("da du da du da"));
        assertEquals(3, stringSet.size());
        assertFalse(stringSet.isEmpty());

        assertFalse(stringSet.add("da du da du da"));
        assertFalse(stringSet.contains("ass"));
        assertTrue(stringSet.contains("kek"));
        assertTrue(stringSet.contains("kik"));
        assertTrue(stringSet.contains("da du da du da"));
        assertEquals(3, stringSet.size());
        assertFalse(stringSet.isEmpty());

        MyTreeSetImplementation<String> manyStrings = new MyTreeSetImplementation<>();
        int expectedSize = 0;
        for (int i = -100; i <= 100; i++) {
            assertEquals(expectedSize, manyStrings.size());
            assertTrue(manyStrings.add("value " + i));
            expectedSize++;
        }

        assertEquals(expectedSize, manyStrings.size());
        for (int i = -100; i <= 100; i++) {
            assertTrue(manyStrings.contains("value " + i));
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void firstNoSuchElementException() throws Exception {
        MyTreeSetImplementation<Integer> treeSet = new MyTreeSetImplementation<>();
        treeSet.first();
    }

    @Test
    public void first() throws Exception {
        assertEquals(integers.first(), treeSetIntegers.first());
        assertEquals(strings.first(), treeSetStrings.first());
    }

    @Test(expected = NoSuchElementException.class)
    public void lastNoSuchElementException() throws Exception {
        MyTreeSetImplementation<Integer> treeSet = new MyTreeSetImplementation<>();
        treeSet.last();
    }

    @Test
    public void last() throws Exception {
        assertEquals(integers.last(), treeSetIntegers.last());
        assertEquals(strings.last(), treeSetStrings.last());
    }

    @Test(expected = NullPointerException.class)
    public void lowerNullPointerException() throws Exception {
        treeSetStrings.lower(null);
    }

    @Test
    public void lower() throws Exception {
        for (Integer testIntegerValue : testIntegerValues) {
            assertEquals(treeSetIntegers.lower(testIntegerValue), integers.lower(testIntegerValue));
        }

        for (String testStringValue : testStringValues) {
            assertEquals(treeSetStrings.lower(testStringValue), strings.lower(testStringValue));
        }
    }

    @Test(expected = NullPointerException.class)
    public void floorNullPointerException() throws Exception {
        treeSetStrings.floor(null);
    }

    @Test
    public void floor() throws Exception {
        for (Integer testIntegerValue : testIntegerValues) {
            assertEquals(treeSetIntegers.floor(testIntegerValue), integers.floor(testIntegerValue));
        }

        for (String testStringValue : testStringValues) {
            assertEquals(treeSetStrings.floor(testStringValue), strings.floor(testStringValue));
        }
    }

    @Test(expected = NullPointerException.class)
    public void ceilingNullPointerException() throws Exception {
        treeSetStrings.ceiling(null);
    }

    @Test
    public void ceiling() throws Exception {
        for (Integer testIntegerValue : testIntegerValues) {
            assertEquals(treeSetIntegers.ceiling(testIntegerValue), integers.ceiling(testIntegerValue));
        }

        for (String testStringValue : testStringValues) {
            assertEquals(treeSetStrings.ceiling(testStringValue), strings.ceiling(testStringValue));
        }
    }

    @Test
    public void higher() throws Exception {
        for (Integer testIntegerValue : testIntegerValues) {
            assertEquals(treeSetIntegers.higher(testIntegerValue), integers.higher(testIntegerValue));
        }

        for (String testStringValue : testStringValues) {
            assertEquals(treeSetStrings.higher(testStringValue), strings.higher(testStringValue));
        }
    }

    @Test(expected = NullPointerException.class)
    public void higherNullPointerException() throws Exception {
        treeSetStrings.higher(null);
    }

    @Test
    public void iterator() throws Exception {
        Iterator<Integer> integerIterator = integers.iterator();
        Iterator<Integer> treeSetIntegerDescendingIterator = treeSetIntegers.iterator();
        while (integerIterator.hasNext()) {
            assertEquals(integerIterator.next(), treeSetIntegerDescendingIterator.next());
        }

        Iterator<String> stringsIterator = strings.iterator();
        Iterator<String> treeSetStringDescendingIterator = treeSetStrings.iterator();
        while (stringsIterator.hasNext()) {
            assertEquals(stringsIterator.next(), treeSetStringDescendingIterator.next());
        }
    }

    @Test
    public void descendingIterator() throws Exception {
        Iterator<Integer> integerDescendingIterator = integers.descendingIterator();
        Iterator<Integer> treeSetIntegerDescendingIterator = treeSetIntegers.descendingIterator();
        while (integerDescendingIterator.hasNext()) {
            assertEquals(integerDescendingIterator.next(), treeSetIntegerDescendingIterator.next());
        }

        Iterator<String> stringsDescendingIterator = strings.descendingIterator();
        Iterator<String> treeSetStringDescendingIterator = treeSetStrings.descendingIterator();
        while (stringsDescendingIterator.hasNext()) {
            assertEquals(stringsDescendingIterator.next(), treeSetStringDescendingIterator.next());
        }
    }

    @Test
    public void descendingSet() throws Exception {
        MyTreeSet<Integer> descendingIntegers = integers.descendingSet();
        assertEquals(descendingIntegers.first(), integers.last());
        assertEquals(descendingIntegers.last(), integers.first());

        Iterator<Integer> integerIterator = integers.descendingIterator();
        Iterator<Integer> descendingIntegersIterator = descendingIntegers.iterator();
        while (integerIterator.hasNext()) {
            assertEquals(integerIterator.next(), descendingIntegersIterator.next());
        }

        MyTreeSet<String> descendingStrings = strings.descendingSet();
        assertEquals(descendingStrings.first(), strings.last());
        assertEquals(descendingStrings.last(), strings.first());

        Iterator<String> stringsIterator = strings.descendingIterator();
        Iterator<String> descendingStringsIterator = descendingStrings.iterator();
        while (stringsIterator.hasNext()) {
            assertEquals(stringsIterator.next(), descendingStringsIterator.next());
        }
    }
}