package com.raylabz.cashew;

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

}
