package com.mikhail.pravilov.mit.entry;

import java.util.Map;

public class MyEntry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public MyEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public K setKey(K key) {
        K oldKey = this.key;
        this.key = key;
        return oldKey;
    }

    public boolean equals(Map.Entry<K, V> e) {
        return (this.getKey()==null ? e.getKey() == null : this.getKey().equals(e.getKey()))
                && (this.getValue()==null ? e.getValue() == null : this.getValue().equals(e.getValue()));
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
