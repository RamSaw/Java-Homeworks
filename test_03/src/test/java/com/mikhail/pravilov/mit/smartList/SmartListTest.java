package com.mikhail.pravilov.mit.smartList;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.*;

public class SmartListTest {
    @Test
    public void testSimple() {
        List<Integer> list = newList();

        assertEquals(Collections.<Integer>emptyList(), list);

        list.add(1);
        assertEquals(Collections.singletonList(1), list);

        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);
    }

    @Test
    public void testGetSet() {
        List<Object> list = newList();

        list.add(1);

        assertEquals(1, list.get(0));
        assertEquals(1, list.set(0, 2));
        assertEquals(2, list.get(0));
        assertEquals(2, list.set(0, 1));

        list.add(2);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));

        assertEquals(1, list.set(0, 2));

        assertEquals(Arrays.asList(2, 2), list);
    }

    @Test
    public void testRemove() throws Exception {
        List<Object> list = newList();

        list.add(1);
        list.remove(0);
        assertEquals(Collections.emptyList(), list);

        list.add(2);
        list.remove((Object) 2);
        assertEquals(Collections.emptyList(), list);

        list.add(1);
        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);

        list.remove(0);
        assertEquals(Collections.singletonList(2), list);

        list.remove(0);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void testIteratorRemove() throws Exception {
        List<Object> list = newList();
        assertFalse(list.iterator().hasNext());

        list.add(1);

        Iterator<Object> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);

        list.addAll(Arrays.asList(1, 2));

        iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertTrue(iterator.hasNext());
        assertEquals(Collections.singletonList(2), list);
        assertEquals(2, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);
    }


    @Test
    public void testCollectionConstructor() throws Exception {
        assertEquals(Collections.emptyList(), newList(Collections.emptyList()));
        assertEquals(
                Collections.singletonList(1),
                newList(Collections.singletonList(1)));

        assertEquals(
                Arrays.asList(1, 2),
                newList(Arrays.asList(1, 2)));
    }

    @Test
    public void testAddManyElementsThenRemove() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 7; i++) {
            list.add(i + 1);
        }

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), list);

        for (int i = 0; i < 7; i++) {
            list.remove(list.size() - 1);
            assertEquals(6 - i, list.size());
        }

        assertEquals(Collections.emptyList(), list);
    }

    private static <T> List<T> newList() {
        try {
            return (List<T>) getListClass().getConstructor().newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> newList(Collection<T> collection) {
        try {
            return (List<T>) getListClass().getConstructor(Collection.class).newInstance(collection);
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getListClass() throws ClassNotFoundException {
        return Class.forName("com.mikhail.pravilov.mit.smartList.SmartList");
    }

    @Test
    public void testComplexCase() throws Exception {
        List<Object> list = newList();

        for (int i = 0; i < 10; i++) {
            list.add("a" + Integer.toString(i));
        }

        assertEquals(Arrays.asList("a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"), list);

        for (int i = 0; i < 10; i++) {
            // Added contains testing
            assertTrue(list.contains("a" + Integer.toString(i)));
            assertEquals("a" + Integer.toString(i), list.get(i));
        }

        for (int i = 0; i < 10; i++) {
            assertEquals("a" + Integer.toString(list.size() - 1), list.remove(list.size() - 1));
            assertEquals(9 - i, list.size());
        }

        assertEquals(Collections.emptyList(), list);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddThrowsIndexOutOfBoundsException() throws Exception {
        List<Object> list = newList();

        for (int i = 0; i < 1; i++) {
            list.add("a" + Integer.toString(i));
        }

        list.add(100, "bad");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetThrowsIndexOutOfBoundsException() throws Exception {
        List<Object> list = newList();

        for (int i = 0; i < 4; i++) {
            list.add("a" + Integer.toString(i));
        }

        list.get(100);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveThrowsIndexOutOfBoundsException() throws Exception {
        List<Object> list = newList();

        for (int i = 0; i < 10; i++) {
            list.add("a" + Integer.toString(i));
        }

        list.remove(100);
    }
}