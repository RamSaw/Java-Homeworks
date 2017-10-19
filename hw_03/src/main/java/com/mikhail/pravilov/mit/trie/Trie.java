package com.mikhail.pravilov.mit.trie;

import java.io.*;
import java.util.ArrayList;

/** Class for realizing trie data structure */
public class Trie implements Serializable {
    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    /** Class of vertices in trie */
    private class Vertex implements Serializable {
        private int[] indexOfNextVertex = new int[ALPHABET_SIZE];
        private boolean isTerminal = false;
        private int size = 0;
    }

    /** Root is stored at 0 index **/
    private ArrayList<Vertex> vertices;

    {
        vertices = new ArrayList<>();
        vertices.add(0, new Vertex());
    }

    /**
     * Method to get size of trie
     * @return size of root vertex stored at 0 in ArrayList.
     */
    public int size() {
        return vertices.get(0).size;
    }


    /**
     * Goes deep in trie, finding vertex that path set by element lead to.
     * @param element string that sets a path in trie
     * @return vertex specified by path
     */
    private Vertex findVertex(String element) {
        Vertex currentVertex = vertices.get(0);
        for (char symbol : element.toCharArray()) {
            if (currentVertex.indexOfNextVertex[symbol] == 0) {
                currentVertex.indexOfNextVertex[symbol] = vertices.size();
                vertices.add(new Vertex());
            }
            currentVertex = vertices.get(currentVertex.indexOfNextVertex[symbol]);
        }
        return currentVertex;
    }


    /**
     * Decreases size by 1 for each vertex on path set by given string
     * @param element path on which to decrease key
     */
    private void decreaseSize(String element) {
        Vertex currentVertex = vertices.get(0);

        currentVertex.size--;
        for (char symbol : element.toCharArray()) {
            currentVertex = vertices.get(currentVertex.indexOfNextVertex[symbol]);
            currentVertex.size--;
        }
    }

    /**
     * Increases size by 1 for each vertex on path set by given string
     * @param element path on which to decrease key
     */
    private void increaseSize(String element) {
        Vertex currentVertex = vertices.get(0);

        currentVertex.size++;
        for (char symbol : element.toCharArray()) {
            currentVertex = vertices.get(currentVertex.indexOfNextVertex[symbol]);
            currentVertex.size++;
        }
    }

    /**
     * Adds string to trie. O(|element|)
     * @param element string to add
     * @return true if element didn't exist, otherwise false
     */
    public boolean add(String element) {
        Vertex leaf = findVertex(element);
        if (!leaf.isTerminal)
            increaseSize(element);
        return !leaf.isTerminal && (leaf.isTerminal = true);
    }

    /**
     * Checks whether given strings exists in trie. O(|element|)
     * @param element string to check for existence in trie
     * @return true if it is exists, otherwise false
     */
    public boolean contains(String element) {
        return findVertex(element).isTerminal;
    }

    /**
     * Removes string from trie. O(|element|)
     * @param element string to remove
     * @return true if string existed in trie, otherwise false
     */
    public boolean remove(String element) {
        Vertex leaf = findVertex(element);
        if (leaf.isTerminal)
            decreaseSize(element);
        return leaf.isTerminal && !(leaf.isTerminal = false);
    }

    /**
     * Counts how many strings that store in trie starts with given prefix. O(|prefix|)
     * @param prefix to search for
     * @return number of strings that start with given prefix
     */
    public int howManyStartsWithPrefix(String prefix) {
        Vertex prefixVertex = findVertex(prefix);
        return prefixVertex.size;
    }


    /**
     * Serializes Trie object
     * @param out stream to print bytes of Trie object
     * @throws IOException exception occurred during work with output stream
     */
    public void serialize(OutputStream out) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
        }
    }

    /**
     * Deserializes input stream and replaces this with read Trie object.
     * @param in input stream from where to read Trie object
     * @throws IOException exception occurred during work with input stream
     */
    public void deserialize(InputStream in) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            Trie trie = (Trie)objectInputStream.readObject();
            this.vertices = trie.vertices;
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.print(classNotFoundException.getMessage());
        }
    }
}
