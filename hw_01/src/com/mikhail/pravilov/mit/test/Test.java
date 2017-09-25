package com.mikhail.pravilov.mit.test;

import com.mikhail.pravilov.mit.hashTable.HashTable;
import com.mikhail.pravilov.mit.list.List;
import javafx.util.Pair;

/**
 * Class for test methods. It tests List and HashTable classes.
 * Also it has main function.
 */
public class Test {
    /**
     * Main function. It runs tests for List and HashTable classes
     * @param args arguments received from terminal
     */
    public static void main(String[] args) {
        testList();
        testHashTable();
    }

    /**
     * Tests for List class
     */
    private static void testList() {
        List list = new List();
        list.add("el1", "kek", 0);
        list.add("el2", "kok", 1);
        list.add("el3", "kik", 1);
        list.add("el4", "kuk", 3);
        list.add("el5", "kak", 0);

        String a;
        a = list.getKey(0);
        assert a.equals("el5");
        a = list.getKey(1);
        assert a.equals("el1");
        a = list.getKey(2);
        assert a.equals("el3");
        a = list.getKey(3);
        assert a.equals("el2");
        a = list.getKey(4);
        assert a.equals("el4");
        a = list.getKey(-1);
        assert a == null;
        a = list.getKey(5);
        assert a == null;

        a = list.getValue(0);
        assert a.equals("kak");
        a = list.getValue(1);
        assert a.equals("kek");
        a = list.getValue(2);
        assert a.equals("kik");
        a = list.getValue(3);
        assert a.equals("kok");
        a = list.getValue(4);
        assert a.equals("kuk");
        a = list.getValue(-1);
        assert a == null;
        a = list.getValue(5);
        assert a == null;

        a = list.getValueByKey("el5");
        assert a.equals("kak");
        a = list.getValueByKey("el1");
        assert a.equals("kek");
        a = list.getValueByKey("el2");
        assert a.equals("kok");
        a = list.getValueByKey("el3");
        assert a.equals("kik");
        a = list.getValueByKey("el4");
        assert a.equals("kuk");
        a = list.getValueByKey("sad");
        assert a == null;

        Pair<String, String> p;
        p = list.getPairKeyValue(0);
        assert p.equals(new Pair<>("el5", "kak"));
        p = list.getPairKeyValue(1);
        assert p.equals(new Pair<>("el1", "kek"));
        p = list.getPairKeyValue(2);
        assert p.equals(new Pair<>("el3", "kik"));
        p = list.getPairKeyValue(3);
        assert p.equals(new Pair<>("el2", "kok"));
        p = list.getPairKeyValue(4);
        assert p.equals(new Pair<>("el4", "kuk"));
        p = list.getPairKeyValue(-1);
        assert p == null;
        p = list.getPairKeyValue(5);
        assert p == null;

        list.deleteByIndex(1);
        assert list.getPairKeyValue(4) == null;
        list.delete("el4");
        assert list.getValueByKey("el4") == null;
        list.deleteByIndex(0);
        assert list.getPairKeyValue(2) == null;
        list.deleteByIndex(1);
        assert list.getPairKeyValue(1) == null;
        list.deleteByIndex(0);
        assert list.getPairKeyValue(0) == null;

        boolean raisedException = false;
        try {
            list.deleteByIndex(0);
        }
        catch (IndexOutOfBoundsException e) {
            raisedException = true;
        }
        assert raisedException;
        raisedException = false;
        try {
            list.add("a", "b", -1);
        }
        catch (IndexOutOfBoundsException e) {
            raisedException = true;
        }
        assert raisedException;
    }

    /**
     * test for HashTable class
     */
    private static void testHashTable() {
        HashTable hashTable = new HashTable();

        hashTable.put("el1", "kek");
        assert hashTable.size() == 1;
        assert hashTable.contains("el1");
        assert hashTable.get("el1").equals("kek");
        assert hashTable.get("el2") == null;

        hashTable.put("el2", "kok");
        assert hashTable.size() == 2;
        assert hashTable.contains("el2");
        assert hashTable.get("el1").equals("kek");
        assert hashTable.get("el2").equals("kok");

        hashTable.put("el3", "kik");
        assert hashTable.size() == 3;
        assert hashTable.contains("el3");
        assert !hashTable.contains("el4");
        assert hashTable.get("el1").equals("kek");
        assert hashTable.get("el2").equals("kok");
        assert hashTable.get("el3").equals("kik");

        hashTable.put("el4", "kuk");
        assert hashTable.size() == 4;
        assert hashTable.contains("el4");
        assert hashTable.get("el1").equals("kek");
        assert hashTable.get("el2").equals("kok");
        assert hashTable.get("el3").equals("kik");
        assert hashTable.get("el4").equals("kuk");

        String removedValue;
        removedValue = hashTable.remove("el2");
        assert removedValue.equals("kok");
        assert hashTable.size() == 3;
        assert !hashTable.contains("el2");

        removedValue = hashTable.remove("el5");
        assert removedValue == null;
        assert hashTable.size() == 3;
        assert !hashTable.contains("el5");

        hashTable.clear();
        assert hashTable.size() == 0;
        assert !hashTable.contains("el1");

        for (int i = 0; i < 1000; i++)
            hashTable.put("el" + i, "value" + i);
        String previousValue = hashTable.put("el" + 10, "value" + 10);
        assert previousValue.equals("value" + 10);

        for (int i = 0; i < 1000; i++) {
            removedValue = hashTable.remove("el" + i);
            assert removedValue.equals("value" + i);
        }

        /* test on putting existed key */
        hashTable.clear();
        hashTable.put("Existed", "value1");
        assert hashTable.get("Existed").equals("value1");
        assert hashTable.size() == 1;
        hashTable.put("Existed", "value2");
        assert hashTable.get("Existed").equals("value2");
        assert hashTable.size() == 1;
        hashTable.put("NotExisted", "value2");
        assert hashTable.get("NotExisted").equals("value2");
        assert hashTable.size() == 2;
    }
}
