package com.mikhail.pravilov.mit.list;

import javafx.util.Pair;

/** Class for realization list data structure. */
public class List {
    /**
     *  Private access class.
     *  Stores data for list class.
     */
    class Node {
        /**
         * Class Data. Node stores key and value.
         */
        private String key, value;
        /**
         * Next and previous nodes.
         */
        private Node prevNode = null;
        private Node nextNode = null;

        /**
         * Constructor. Sets given key, value and next and previous nodes to <code>null</code>.
         * @param key key to be set
         * @param value value to be set
         */
        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Head node to start iterating on list
     */
    private Node head = null;
    /**
     * Number of elements in list
     */
    private int size = 0;

    /**
     * Adds new node to the beginning of the list
     * @param newNode node to add
     */
    private void addToBeginning(Node newNode) {
        if (head == null) {
            newNode.nextNode = null;
            newNode.prevNode = null;
            head = newNode;
        }
        else {
            newNode.nextNode = head;
            newNode.prevNode = null;
            head.prevNode = newNode;
            head = newNode;
        }

        size++;
    }

    /**
     * Adds new node to the end of the list
     * @param newNode node to add
     */
    private void addToEnd(Node newNode) {
        Node currentEndNode = head;

        while (currentEndNode.nextNode != null)
            currentEndNode = currentEndNode.nextNode;

        newNode.prevNode = currentEndNode;
        newNode.nextNode = null;
        currentEndNode.nextNode = newNode;

        size++;
    }

    /**
     * Adds new node in index position. Calls addToBeginning and addToEnd in case of index = 0 or size
     * @param newNode node to add
     * @param index position to add at
     */
    private void add(Node newNode, int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("index: " + index + ", Size: "+size);

        if (index == 0)
            addToBeginning(newNode);
        else if (index == size)
            addToEnd(newNode);
        else {
            Node currentNodeInIndex = head;

            for (int i = 0; i < index; i++)
                currentNodeInIndex = currentNodeInIndex.nextNode;

            newNode.nextNode = currentNodeInIndex;
            newNode.prevNode = currentNodeInIndex.prevNode;
            currentNodeInIndex.prevNode.nextNode = newNode;
            currentNodeInIndex.prevNode = newNode;

            size++;
        }
    }

    /**
     * Adds node with key and value in index position
     * @param key key stored in new node
     * @param value value stored in new node
     * @param index position to add at
     */
    public void add(String key, String value, int index) {
        Node newNode = new Node(key, value);

        add(newNode, index);
    }

    /**
     * Deletes node from beginning
     * @return value of deleted node
     */
    private String deleteFromBeginning() {
        String value = head.value;

        head = head.nextNode;
        if (head != null)
            head.prevNode = null;
        size--;

        return value;
    }

    /**
     * Deletes node from end
     * @return value of deleted node
     */
    private String deleteFromEnd() {
        Node currentEndNode = head;

        while (currentEndNode.nextNode != null)
            currentEndNode = currentEndNode.nextNode;

        String value = currentEndNode.value;
        currentEndNode.prevNode.nextNode = null;
        size--;

        return value;
    }

    /**
     * Deletes node in index position
     * @param index position to delete at
     * @return value of deleted node
     */
    public String deleteByIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("index: " + index + ", Size: "+size);

        if (index == 0)
            return deleteFromBeginning();
        else if (index == size - 1)
            return deleteFromEnd();
        else {
            Node nodeToDelete = head;

            for (int i = 0; i < index; i++)
                nodeToDelete = nodeToDelete.nextNode;

            String value = nodeToDelete.value;
            nodeToDelete.prevNode.nextNode = nodeToDelete.nextNode;
            nodeToDelete.nextNode.prevNode = nodeToDelete.prevNode;

            size--;

            return value;
        }
    }

    /**
     * Deletes node with specific key
     * @param key key of node to be deleted
     * @return value of deleted node
     */
    public String delete(String key) {
        Node nodeInIndex = head;
        int index = 0;

        while (nodeInIndex != null && !nodeInIndex.key.equals(key)) {
            nodeInIndex = nodeInIndex.nextNode;
            index++;
        }

        return nodeInIndex == null ? null : deleteByIndex(index);
    }

    /**
     * Removes all elements in list
     */
    public void clear() {
        while (head != null)
            deleteFromBeginning();
    }

    /**
     * Get key by index
     * @param index position
     * @return key in index position, if invalid index return <code>null</code>
     */
    public String getKey(int index) {
        if (index < 0 || index >= size)
            return null;

        Node nodeInIndex = head;

        for (int i = 0; i < index; i++)
            nodeInIndex = nodeInIndex.nextNode;

        return nodeInIndex.key;
    }

    /**
     * Get value by index
     * @param index position
     * @return value in index position, if invalid index return <code>null</code>
     */
    public String getValue(int index) {
        if (index < 0 || index >= size)
            return null;

        Node nodeInIndex = head;

        for (int i = 0; i < index; i++)
            nodeInIndex = nodeInIndex.nextNode;

        return nodeInIndex.value;
    }

    /**
     * Get pair key and value in specific position
     * @param index position
     * @return pair key and value, if invalid index return <code>null</code>
     */
    public Pair<String, String> getPairKeyValue(int index) {
        if (index < 0 || index >= size)
            return null;

        Node nodeInIndex = head;

        for (int i = 0; i < index; i++)
            nodeInIndex = nodeInIndex.nextNode;

        return new Pair<>(nodeInIndex.key, nodeInIndex.value);
    }

    /**
     * Get value that key has
     * @param key key which value we want to get
     * @return value of the given key
     */
    public String getValueByKey(String key) {
        Node nodeInIndex = head;

        while (nodeInIndex != null && !nodeInIndex.key.equals(key))
            nodeInIndex = nodeInIndex.nextNode;

        return nodeInIndex == null ? null : nodeInIndex.value;
    }
}
