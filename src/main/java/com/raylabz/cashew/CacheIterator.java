package com.raylabz.cashew;

/**
 * Allows iteration through a cache.
 */
public interface CacheIterator {

    /**
     * Executes code for each given item.
     * @param item An item inside a cache.
     */
    <T> void forEach(String key, CacheItem<T> item);

}
