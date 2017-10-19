package com.mikhail.pravilov.mit.trie;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class TrieTest {
    private Trie trie;

    @Before
    public void setUp() throws Exception {
        trie = new Trie();
        trie.add("kek");
        trie.add("ket");
        trie.add("kuk");
        trie.add("ukk");
    }

    @Test
    public void size() throws Exception {
        assertEquals(4, trie.size());
    }

    @Test
    public void add() throws Exception {
        boolean returned;
        returned = trie.add("ukk");
        assertFalse(returned);
        returned = trie.add("aaa");
        assertTrue(returned);
        assertEquals(5, trie.size());
    }

    @Test
    public void contains() throws Exception {
        assertTrue(trie.contains("kek"));
        assertTrue(trie.contains("ket"));
        assertTrue(trie.contains("kuk"));
        assertTrue(trie.contains("ukk"));
    }

    @Test
    public void remove() throws Exception {
        boolean returned;
        returned = trie.remove("kek");
        assertTrue(returned);
        assertFalse(trie.contains("kek"));
        assertEquals(3, trie.size());
        returned = trie.remove("no_element");
        assertFalse(returned);
        assertFalse(trie.contains("no_element"));
        assertEquals(3, trie.size());
        returned = trie.remove("kuk");
        assertTrue(returned);
        assertFalse(trie.contains("kuk"));
        assertEquals(2, trie.size());
        returned = trie.remove("ket");
        assertTrue(returned);
        assertFalse(trie.contains("kuk"));
        assertEquals(1, trie.size());
    }

    @Test
    public void howManyStartsWithPrefix() throws Exception {
        int numberStartsWith;
        numberStartsWith = trie.howManyStartsWithPrefix("k");
        assertEquals(3, numberStartsWith);
        numberStartsWith = trie.howManyStartsWithPrefix("");
        assertEquals(4, numberStartsWith);
        numberStartsWith = trie.howManyStartsWithPrefix("u");
        assertEquals(1, numberStartsWith);
        numberStartsWith = trie.howManyStartsWithPrefix("ukk");
        assertEquals(1, numberStartsWith);
        numberStartsWith = trie.howManyStartsWithPrefix("ukkk");
        assertEquals(0, numberStartsWith);
        numberStartsWith = trie.howManyStartsWithPrefix("kek");
        assertEquals(1, numberStartsWith);
        numberStartsWith = trie.howManyStartsWithPrefix("ke");
        assertEquals(2, numberStartsWith);
        numberStartsWith = trie.howManyStartsWithPrefix("kuk");
        assertEquals(1, numberStartsWith);
        numberStartsWith = trie.howManyStartsWithPrefix("aaaaa");
        assertEquals(0, numberStartsWith);
    }

    @Test
    public void deserialize() throws Exception {
        try (FileOutputStream out = new FileOutputStream("testSerializeDeserialize.object")) {
            trie.serialize(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* No test on the exact coincidence because int[].equals doesn't check on equality!!!!! */
        Trie deserializedTrie = new Trie();
        try (FileInputStream in = new FileInputStream("testSerializeDeserialize.object")) {
            deserializedTrie.deserialize(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(4, deserializedTrie.size());
        assertTrue(deserializedTrie.contains("kek"));
        assertTrue(deserializedTrie.contains("ket"));
        assertTrue(deserializedTrie.contains("kuk"));
        assertTrue(deserializedTrie.contains("ukk"));
        assertFalse(deserializedTrie.contains("asd"));

        File testFile = new File("testSerializeDeserialize.object");
        testFile.delete();
    }

}