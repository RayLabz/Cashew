package com.raylabz.cashew.iterator;

import com.raylabz.cashew.CacheItem;

/**
 * Allows iteration through a cache.
 */
public interface CacheIterator<K, V> {

    /**
     * Executes code for each given item.
     * @param item An item inside a cache.
     * @param key The cache item's key.
     */
    void forEach(K key, CacheItem<V> item);

}
