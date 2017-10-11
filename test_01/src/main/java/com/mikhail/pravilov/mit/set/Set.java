package com.mikhail.pravilov.mit.set;

import com.mikhail.pravilov.mit.list.List;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

public class Set<E> implements java.util.Set<E> {
    List<Integer, E> elements;

    public Set() {
        this.elements = new List<>();
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentPosition = 0;

            @Override
            public boolean hasNext() {
                return elements.getValueByKey(currentPosition) != null;
            }

            @Override
            public E next() {
                return elements.getValueByKey(currentPosition++);
            }
        };
    }

    @Override
    public int size() {
        return elements.getSize();
    }

    @Override
    public boolean add(E e) {
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (e.equals(iterator.next()))
                return false;
        }
        elements.add(elements.getSize(), e, elements.getSize());
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
