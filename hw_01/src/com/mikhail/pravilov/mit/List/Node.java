package com.mikhail.pravilov.mit.List;

/**
 *  Package-private access class.
 *  Stores data for List class.
 */
class Node {
    /**
     * Data. Node stores key and value.
     */
    private String key, value;
    /**
     * Next and previous nodes.
     */
    private Node prevNode;
    private Node nextNode;

    /**
     * Constructor. Sets given key, value and next and previous nodes to <code>null</code>.
     * @param key key to be set
     * @param value value to be set
     */
    Node(String key, String value) {
        this.key = key;
        this.value = value;
        this.prevNode = null; // By default null?
        this.nextNode = null; // By default null?
    }

    /**
     * Method to get key of node
     * @return field key(type String) of Node class
     */
    String getKey() {
        return key;
    }

    /**
     * Method to get value of node
     * @return field value(type String) of Node class
     */
    String getValue() {
        return value;
    }

    /**
     * Method to get previous node
     * @return field prevNode(type Node) of Node class
     */
    Node getPrevNode() {
        return prevNode;
    }

    /**
     * Method to set prevNode(previous node) field
     */
    void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    /**
     * Method to get next node
     * @return field nextNode(type Node) of Node class
     */
    Node getNextNode() {
        return nextNode;
    }

    /**
     * Method to set nextNode(next node) field
     */
    void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
}
