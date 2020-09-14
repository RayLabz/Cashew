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

    /**
     * Clones this cache into another cache.
     * @return Returns a new cache contains the same items as the cloned cache.
     */
    public StringCache<V> copy() {
        StringCache<V> newCache = new StringCache<V>(getTimeToLive(), getCleanupInterval());
        for (Map.Entry<String, CacheItem<V>> entry : getMap().entrySet()) {
            final String newKey = entry.getKey();
            final CacheItem<V> newCacheItem = entry.getValue().copy();
            newCache.getMap().put(newKey, newCacheItem);
        }
        return newCache;
    }

}
