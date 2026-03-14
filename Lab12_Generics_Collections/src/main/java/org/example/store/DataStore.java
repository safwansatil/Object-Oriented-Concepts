package org.example.store;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete implementation of the Store interface backed by an ArrayList.
 *
 * @param <T> the type of items stored in this data store
 */
public class DataStore<T> implements Store<T> {

    private final ArrayList<T> storage;
    public DataStore() {
        this.storage = new ArrayList<>();
    }

    /**
     * Adds an item to the data store.
     */
    @Override
    public void add(T item) {
        this.storage.add(item);
    }

    /**
     * Retrieves an item from the data store at the specified index.
     *
     * @param index the index of the item to retrieve
     * @return the item at the specified index
     */
    @Override
    public T get(int index) {
        return this.storage.get(index);
    }

    /**
     * Retrieves all items from the data store.
     * Returns a defensive copy of the internal storage.
     *
     * @return a new list containing all stored items
     */
    @Override
    public List<T> getAll() {
        return new ArrayList<>(this.storage);
    }

    /**
     * Returns the number of items in the data store.
     *
     * @return the number of items
     */
    @Override
    public int size() {
        return this.storage.size();
    }
}
