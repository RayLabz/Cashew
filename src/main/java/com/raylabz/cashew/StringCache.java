package com.raylabz.cashew;

import com.raylabz.cashew.iterator.CacheIterator;

import java.util.Map;

/**
 * A cache that utilizes strings for keys.
 * @param <V> The value type.
 */
public final class StringCache<V> extends Cache<String, V> {

    /**
     * Constructs a new StringCache.
     * @param timeToLive The cache's TTL.
     * @param cleanupInterval The cache's cleanup interval time.
     */
    public StringCache(long timeToLive, long cleanupInterval) {
        super(timeToLive, cleanupInterval);
    }

    /**
     * Iterates through all items in a cache.
     * @param iterator The iterator.
     */
    @Override
    public void forAll(CacheIterator<String, V> iterator) {
        for (Map.Entry<String, CacheItem<V>> entry : getMap().entrySet()) {
            iterator.forEach(entry.getKey(), entry.getValue());
        }
    }

}
