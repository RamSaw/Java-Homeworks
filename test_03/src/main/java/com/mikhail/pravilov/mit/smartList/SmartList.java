package com.mikhail.pravilov.mit.smartList;

import java.util.*;

public class SmartList<E> extends AbstractList<E> implements List<E> {
    private static final int SECOND_TYPE_MAX = 5;
    private int size = 0;
    private Object reference;

    public SmartList(Collection<? extends E> collection) {
        this.addAll(collection);
    }

    public SmartList() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        if (size == 0) {
            reference = e;
        }
        else if (size < SECOND_TYPE_MAX) {
            if (size == 1) {
                Object copy = reference;

                reference = new Object[SECOND_TYPE_MAX];
                ((Object[]) reference)[0] = copy;
            }

            E prev = e;
            for (int i = index; i <= size; i++) {
                E copy = (E) ((Object[]) reference)[index];
                ((Object[]) reference)[index] = prev;
                prev = copy;
            }
        }
        else {
            if (size == SECOND_TYPE_MAX) {
                reference = new ArrayList<>(Arrays.asList((Object[]) reference));
            }
            ((ArrayList<E>) reference).add(index, e);
        }

        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        if (size == 1) {
            return (E) reference;
        }
        else if (size > 1 && size <= SECOND_TYPE_MAX) {
            return (E) ((Object[]) reference)[index];
        }
        else {
            return ((ArrayList<E>) reference).get(index);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E e) {
        E prevValue = get(index);

        if (size == 1) {
            reference = e;
        }
        else if (size > 1 && size <= SECOND_TYPE_MAX) {
            ((Object[]) reference)[index] = e;
        }
        else {
            return ((ArrayList<E>) reference).set(index, e);
        }
        return prevValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        E removedValue = get(index);

        if (size == 1) {
            reference = null;
        }
        else if (size == 2) {
            if (index == 0) {
                reference = ((Object[]) reference)[1];
            }
            else {
                reference = ((Object[]) reference)[0];
            }
        }
        else if (size <= SECOND_TYPE_MAX) {
            System.arraycopy(reference, index + 1, reference, index, size - 1 - index);
        }
        else if (size == SECOND_TYPE_MAX + 1) {
            ArrayList<E> copy = (ArrayList<E>) reference;
            reference = new Object[SECOND_TYPE_MAX];

            for (int i = 0; i < SECOND_TYPE_MAX; i++) {
                if (i == index) {
                    continue;
                }
                ((Object[]) reference)[i] = copy.get(i);
            }
        }
        else {
            size--;
            return ((ArrayList<E>) reference).remove(index);
        }

        size--;
        return removedValue;
    }
}
