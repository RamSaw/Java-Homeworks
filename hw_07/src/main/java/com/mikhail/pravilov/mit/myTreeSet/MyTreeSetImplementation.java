package com.mikhail.pravilov.mit.myTreeSet;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that implements MyTreeSet interface by storing values in binary search tree.
 * @param <E> - type of stored values
 */
public class MyTreeSetImplementation<E> implements MyTreeSet<E>  {
    private Node root = null;
    private int size = 0;
    private Comparator<? super E> comparator;

    public MyTreeSetImplementation() {
    }

    public MyTreeSetImplementation(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    private int compare(E e1, E e2) {
        return (comparator == null) ? ((Comparable<? super E>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private E nextValue = first();

            @Override
            public boolean hasNext() {
                return nextValue != null;
            }

            @Override
            public E next() {
                E next = nextValue;
                nextValue = higher(nextValue);

                return next;
            }
        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            private E nextValue = last();

            @Override
            public boolean hasNext() {
                return nextValue != null;
            }

            @Override
            public E next() {
                E next = nextValue;
                nextValue = lower(nextValue);

                return next;
            }
        };
    }

    @Override
    public MyTreeSet<E> descendingSet() {
        return new MyTreeSet<E>() {
            @Override
            public Iterator<E> descendingIterator() {
                return MyTreeSetImplementation.this.iterator();
            }

            @NotNull
            @Override
            public Iterator<E> iterator() {
                return MyTreeSetImplementation.this.descendingIterator();
            }

            @Override
            public MyTreeSet<E> descendingSet() {
                return MyTreeSetImplementation.this;
            }

            @Override
            public E first() {
                return MyTreeSetImplementation.this.last();
            }

            @Override
            public E last() {
                return MyTreeSetImplementation.this.first();
            }

            @Override
            public E lower(E e) {
                return MyTreeSetImplementation.this.higher(e);
            }

            @Override
            public E floor(E e) {
                return MyTreeSetImplementation.this.ceiling(e);
            }

            @Override
            public E ceiling(E e) {
                return MyTreeSetImplementation.this.floor(e);
            }

            @Override
            public E higher(E e) {
                return MyTreeSetImplementation.this.lower(e);
            }

            @Override
            public int size() {
                return MyTreeSetImplementation.this.size();
            }

            @Override
            public boolean isEmpty() {
                return MyTreeSetImplementation.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return MyTreeSetImplementation.this.contains(o);
            }

            @NotNull
            @Override
            public Object[] toArray() {
                return MyTreeSetImplementation.this.toArray();
            }

            @NotNull
            @Override
            public <T> T[] toArray(@NotNull T[] a) {
                return MyTreeSetImplementation.this.toArray(a);
            }

            @Override
            public boolean add(E e) {
                return MyTreeSetImplementation.this.add(e);
            }

            @Override
            public boolean remove(Object o) {
                return MyTreeSetImplementation.this.remove(o);
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                return MyTreeSetImplementation.this.containsAll(c);
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends E> c) {
                return MyTreeSetImplementation.this.addAll(c);
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                return MyTreeSetImplementation.this.retainAll(c);
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                return MyTreeSetImplementation.this.removeAll(c);
            }

            @Override
            public void clear() {
                MyTreeSetImplementation.this.clear();
            }
        };
    }

    @NotNull
    private Node firstNode(@NotNull Node node) {
        return node.left == null ? node : firstNode(node.left);
    }

    @Nullable
    @Override
    public E first() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();

        return firstNode(root).storedValue;
    }

    @NotNull
    private Node lastNode(@NotNull Node node) {
        return node.right == null ? node : lastNode(node.right);
    }

    @Nullable
    @Override
    public E last() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();

        return lastNode(root).storedValue;
    }

    @Nullable
    private E lower(@NotNull Node node, E e) {
        E returnValue;

        if (node.right != null) {
            returnValue = lower(node.right, e);
            if (returnValue != null)
                return returnValue;
        }

        if (compare(node.storedValue, e) < 0)
            return node.storedValue;

        if (node.left != null) {
            returnValue = lower(node.left, e);
            if (returnValue != null)
                return returnValue;
        }

        return null;
    }

    @Nullable
    @Override
    public E lower(@Nullable E e) throws NullPointerException {
        if (isEmpty())
            return null;

        return lower(root, e);
    }

    @Nullable
    private E floor(@NotNull Node node, E e) {
        E returnValue;

        if (node.right != null) {
            returnValue = floor(node.right, e);
            if (returnValue != null)
                return returnValue;
        }

        if (compare(node.storedValue, e) <= 0)
            return node.storedValue;

        if (node.left != null) {
            returnValue = floor(node.left, e);
            if (returnValue != null)
                return returnValue;
        }

        return null;
    }

    @Nullable
    @Override
    public E floor(E e) throws NullPointerException {
        if (isEmpty())
            return null;

        return floor(root, e);
    }

    @Nullable
    private E ceiling(@NotNull Node node, E e) {
        E returnValue;

        if (node.left != null) {
            returnValue = ceiling(node.left, e);
            if (returnValue != null)
                return returnValue;
        }

        if (compare(node.storedValue, e) >= 0)
            return node.storedValue;

        if (node.right != null) {
            returnValue = ceiling(node.right, e);
            if (returnValue != null)
                return returnValue;
        }

        return null;
    }

    @Override
    public E ceiling(E e) {
        if (isEmpty())
            return null;

        return ceiling(root, e);
    }

    @Nullable
    private E higher(@NotNull Node node, E e) {
        E returnValue;

        if (node.left != null) {
            returnValue = higher(node.left, e);
            if (returnValue != null)
                return returnValue;
        }

        if (compare(node.storedValue, e) > 0)
            return node.storedValue;

        if (node.right != null) {
            returnValue = higher(node.right, e);
            if (returnValue != null)
                return returnValue;
        }

        return null;
    }

    @Override
    public E higher(E e) {
        if (isEmpty())
            return null;

        return higher(root, e);
    }

    /**
     * Method chooses left or right subtree to insert element and if it is null then creates new node.
     * @param node root of subtree.
     * @param e element to insert in set.
     */
    private boolean addToSubTree(@NotNull Node node, @Nullable E e) {
        if ((e == null ? node.storedValue == null : e.equals(node.storedValue)))
            return false;

        if (compare(e, node.storedValue) < 0) {
            if (node.left == null) {
                node.left = new Node(e);
                size++;
                return true;
            }
            return addToSubTree(node.left, e);
        }
        if (compare(e, node.storedValue) > 0) {
            if (node.right == null) {
                node.right = new Node(e);
                size++;
                return true;
            }
            return addToSubTree(node.right, e);
        }

        return false;
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * More formally, adds the specified element e to this set
     * if the set contains no element e2 such that (e==null ? e2==null : e.equals(e2)).
     * If this set already contains the element, the call leaves the set unchanged and returns false.
     * @param e element to add.
     * @return true if there wasn't such element otherwise false.
     */
    @Override
    public boolean add(@Nullable E e) {
        if (root == null) {
            root = new Node(e);
            size++;
            return true;
        }
        return addToSubTree(root, e);
    }

    @Contract("null, _ -> false")
    private boolean containsSubTree(Node node, Object o) throws ClassCastException {
        if (node == null)
            return false;

        if (compare((E) o, node.storedValue) < 0) {
            return containsSubTree(node.left, o);
        }
        else if (compare((E) o, node.storedValue) > 0) {
            return containsSubTree(node.right, o);
        }

        return true;
    }

    /**
     * Checks whether given element is in set
     * @param o element to check
     * @return true if exists otherwise else
     */
    @Override
    public boolean contains(Object o) {
        return containsSubTree(root, o);
    }

    /**
     * Method to get number of elements stored in set
     * @return number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if tree set has no elements.
     * @return - true if there is no elements otherwise false
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Class to store vertex of binary search tree.
     * Fields: left subtree, right subtree, stored value in vertex.
     */
    private class Node {
        private Node left = null;
        private Node right = null;
        E storedValue;

        private Node(E storedValue) {
            this.storedValue = storedValue;
        }
    }

    // Not implemented methods.
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
    }
}
