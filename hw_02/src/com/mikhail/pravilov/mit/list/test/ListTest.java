package com.mikhail.pravilov.mit.list.test;

import com.mikhail.pravilov.mit.list.List;
import javafx.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {
    private List list = new List();

    @org.junit.jupiter.api.BeforeEach
    void addElements() {
        list.add("el1", "kek", 0);
        list.add("el2", "kok", 1);
        list.add("el3", "kik", 1);
        list.add("el4", "kuk", 3);
        list.add("el5", "kak", 0);
    }

    @org.junit.jupiter.api.Test
    void add() {
        assertThrows(IndexOutOfBoundsException.class, () -> {list.add("a", "b", -1);});
    }

    @org.junit.jupiter.api.Test
    void deleteByIndex() {
        list.deleteByIndex(1);
        assertEquals(null, list.getPairKeyValue(4));
        list.deleteByIndex(0);
        assertEquals(null, list.getPairKeyValue(3));
        list.deleteByIndex(2);
        assertEquals(null, list.getPairKeyValue(2));
        list.deleteByIndex(1);
        assertEquals(null, list.getPairKeyValue(1));
        list.deleteByIndex(0);
        assertEquals(null, list.getPairKeyValue(0));

        assertThrows(IndexOutOfBoundsException.class, () -> {list.deleteByIndex(0);});
    }

    @org.junit.jupiter.api.Test
    void delete() {
        String value;

        value = list.delete("el4");
        assertEquals(null, list.getValueByKey("el4"));
        assertEquals("kuk", value);
        value = list.delete("el6");
        assertEquals(null, list.getValueByKey("el4"));
        assertEquals(null, value);
    }

    @org.junit.jupiter.api.Test
    void clear() {
    }

    @org.junit.jupiter.api.Test
    void getKey() {
        String key;

        key = list.getKey(0);
        assertEquals("el5", key);
        key = list.getKey(1);
        assertEquals("el1", key);
        key = list.getKey(2);
        assertEquals("el3", key);
        key = list.getKey(3);
        assertEquals("el2", key);
        key = list.getKey(4);
        assertEquals("el4", key);
        key = list.getKey(-1);
        assertEquals(null, key);
        key = list.getKey(5);
        assertEquals(null, key);
    }

    @org.junit.jupiter.api.Test
    void getValue() {
        String value;

        value = list.getValue(0);
        assertEquals("kak", value);
        value = list.getValue(1);
        assertEquals("kek", value);
        value = list.getValue(2);
        assertEquals("kik", value);
        value = list.getValue(3);
        assertEquals("kok", value);
        value = list.getValue(4);
        assertEquals("kuk", value);
        value = list.getValue(-1);
        assertEquals(null, value);
        value = list.getValue(5);
        assertEquals(null, value);
    }

    @org.junit.jupiter.api.Test
    void getPairKeyValue() {
        Pair<String, String> keyValue;

        keyValue = list.getPairKeyValue(0);
        assertEquals(new Pair<>("el5", "kak"), keyValue);
        keyValue = list.getPairKeyValue(1);
        assertEquals(new Pair<>("el1", "kek"), keyValue);
        keyValue = list.getPairKeyValue(2);
        assertEquals(new Pair<>("el3", "kik"), keyValue);
        keyValue = list.getPairKeyValue(3);
        assertEquals(new Pair<>("el2", "kok"), keyValue);
        keyValue = list.getPairKeyValue(4);
        assertEquals(new Pair<>("el4", "kuk"), keyValue);
        keyValue = list.getPairKeyValue(-1);
        assertEquals(null, keyValue);
        keyValue = list.getPairKeyValue(5);
        assertEquals(null, keyValue);
    }

    @org.junit.jupiter.api.Test
    void getValueByKey() {
        String value;

        value = list.getValueByKey("el5");
        assertEquals("kak", value);
        value = list.getValueByKey("el1");
        assertEquals("kek", value);
        value = list.getValueByKey("el2");
        assertEquals("kok", value);
        value = list.getValueByKey("el3");
        assertEquals("kik", value);
        value = list.getValueByKey("el4");
        assertEquals("kuk", value);
        value = list.getValueByKey("sad");
        assertEquals(null, value);
    }

}