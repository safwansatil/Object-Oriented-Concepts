package org.example.store;

import java.util.List;

/**
 * A generic interface defining the operations for a data store.
 *
 * @param <T> the type of items stored in this data store
 */
public interface Store<T> {

    /**
     * Adds an item to the data store.
     *
     * @param item the item to add
     */
    void add(T item);

    /**
     * Retrieves an item from the data store at the specified index.
     *
     * @param index the index of the item to retrieve
     * @return the item at the specified index
     */
    T get(int index);

    /**
     * Retrieves all items from the data store.
     *
     * @return a list containing all stored items
     */
    List<T> getAll();

    /**
     * Returns the number of items in the data store.
     *
     * @return the number of items
     */
    int size();
}
