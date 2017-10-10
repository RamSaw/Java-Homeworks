package com.mikhail.pravilov.mit;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Class that realizes binary search tree data structure.
 * @param <T> type of stored values, must be comparable.
 */
public class Set<T extends Comparable<T>> {
    /**
     * Class to store vertex of binary search tree.
     * Fields: left subtree, right subtree, stored value in vertex.
     */
    private class Node {
        Node left = null, right = null;
        T storedValue;

        private Node(T storedValue) {
            this.storedValue = storedValue;
        }
    }

    private Node root = null;
    private int size = 0;

    /**
     * Method chooses left or right subtree to insert element and if it is null then creates new node.
     * @param node root of subtree.
     * @param valueToAdd element to insert in set.
     */
    private void addToSubTree(@NotNull Node node, T valueToAdd) {
        if (valueToAdd.compareTo(node.storedValue) < 0) {
            if (node.left == null)  {
                node.left = new Node(valueToAdd);
                size++;
                return;
            }
            addToSubTree(node.left, valueToAdd);
        }
        if (valueToAdd.compareTo(node.storedValue) > 0) {
            if (node.right == null)  {
                node.right = new Node(valueToAdd);
                size++;
                return;
            }
            addToSubTree(node.right, valueToAdd);
        }
    }

    /**
     * Adds element to set
     * @param valueToAdd element to insert in set
     */
    public void add(T valueToAdd) {
        if (root == null) {
            root = new Node(valueToAdd);
            size++;
            return;
        }
        addToSubTree(root, valueToAdd);
    }

    @Contract("null, _ -> false")
    private boolean containsSubTree(Node node, T valueToFind) {
        if (node == null)
            return false;

        if (valueToFind.compareTo(node.storedValue) < 0) {
            return containsSubTree(node.left, valueToFind);
        }
        if (valueToFind.compareTo(node.storedValue) > 0) {
            return containsSubTree(node.right, valueToFind);
        }

        return true;
    }

    /**
     * Checks whether given element is in set
     * @param valueToFind element to check
     * @return true if exists otherwise else
     */
    public boolean contains(T valueToFind) {
        return containsSubTree(root, valueToFind);
    }

    /**
     * Method to get number of elements stored in set
     * @return number of elements
     */
    public int size() {
        return size;
    }
}
