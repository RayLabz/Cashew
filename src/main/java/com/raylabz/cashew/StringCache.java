package com.raylabz.cashew;

public final class StringCache<V> extends Cache<String, V> {

    public StringCache(long timeToLive, long cleanupInterval) {
        super(timeToLive, cleanupInterval);
    }

}
