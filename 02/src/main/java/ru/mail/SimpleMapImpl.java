package ru.mail;

import java.util.*;
import java.util.HashMap;

class SimpleMapNode<K, V> {
    final private K key;
    private V value;

    SimpleMapNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

public class SimpleMapImpl<K, V> implements SimpleMap<K, V> {
    private static int MIN_CAPACITY = 16;
    private static int INITIAL_CAPACITY = 16;
    private static double MIN_LOAD_FACTOR = 0.25;
    private static double MAX_LOAD_FACTOR = 0.75;
    // currently used elements
    private int size = 0;
    // should be always power of 2
    private int capacity = MIN_CAPACITY;
    private List<SimpleMapNode<K, V>>[] table;

    public SimpleMapImpl(int initialCapacityHint) {
        if (initialCapacityHint <= 0) {
            throw new IllegalArgumentException("initial capacity hint should be > 0");
        }
        initialCapacityHint--;
        initialCapacityHint |= initialCapacityHint >> 1;
        initialCapacityHint |= initialCapacityHint >> 2;
        initialCapacityHint |= initialCapacityHint >> 4;
        initialCapacityHint |= initialCapacityHint >> 8;
        initialCapacityHint |= initialCapacityHint >> 16;
        initialCapacityHint++;
        capacity = initialCapacityHint;
        table = new LinkedList[capacity];
    }

    public SimpleMapImpl() {
        this(INITIAL_CAPACITY);
    }

    @Override
    public V put(K key, V value) {
        final int hash = key.hashCode() & (capacity - 1);
        List<SimpleMapNode<K, V>> list = table[hash];
        if (list == null) {
            list = new LinkedList<>();
            table[hash] = list;
        }
        for (SimpleMapNode<K, V> node : list) {
            if (node.getKey().equals(key)) {
                node.setValue(value);
                return value;
            }
        }
        list.add(new SimpleMapNode<>(key, value));
        sizeUp();
        return null;
    }

    @Override
    public V get(K key) {
        final int hash = key.hashCode() & (capacity - 1);
        List<SimpleMapNode<K, V>> list = table[hash];
        if (list == null) return null;
        for (SimpleMapNode<K, V> node : list) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        final int hash = key.hashCode() & (capacity - 1);
        List<SimpleMapNode<K, V>> list = table[hash];
        if (list == null) {
            return null;
        }
        for (SimpleMapNode<K, V> node : list) {
            if (node.getKey().equals(key)) {
                list.remove(node);
                sizeDown();
                return node.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        final int hash = key.hashCode() & (capacity - 1);
        List<SimpleMapNode<K, V>> list = table[hash];
        if (list == null) {
            return false;
        }
        for (SimpleMapNode<K, V> node : list) {
            if (node.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<K>(size);
        for (int i = 0; i < capacity; i++) {
            List<SimpleMapNode<K, V>> list = table[i];
            if (list == null) continue;
            for (SimpleMapNode<K, V> node : list) {
                keys.add(node.getKey());
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            List<SimpleMapNode<K, V>> list = table[i];
            if (list == null) continue;
            for (SimpleMapNode<K, V> node : list) {
                values.add(node.getValue());
            }
        }
        return values;
    }

    private void sizeUp() {
        size++;
        if (size > capacity * MAX_LOAD_FACTOR) {
            rebuildTable(capacity << 1);
        }
    }

    private void sizeDown() {
        size--;
        if (capacity >= MIN_CAPACITY && size < capacity * MIN_LOAD_FACTOR) {
            rebuildTable(capacity >> 1);
        }
    }

    private void rebuildTable(int newCapacity) {
        List<SimpleMapNode<K, V>>[] newTable = new LinkedList[newCapacity];
        for (int i = 0; i < capacity; i++) {
            List<SimpleMapNode<K, V>> list = table[i];
            if (list == null) continue;
            for (SimpleMapNode<K, V> node : list) {
                // if we are sizing down, we can just unset the higher bit
                final int hash = newCapacity < capacity
                    ? (i & ~newCapacity)
                    : node.getKey().hashCode() & (newCapacity - 1);
                List<SimpleMapNode<K, V>> newList = newTable[hash];
                if (newList == null) {
                    newList = new LinkedList<>();
                    newTable[hash] = newList;
                }
                newList.add(node);
            }
        }
        table = newTable;
        capacity = newCapacity;
    }
}
