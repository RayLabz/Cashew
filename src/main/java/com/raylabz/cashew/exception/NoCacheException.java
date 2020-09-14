package com.raylabz.cashew.exception;

/**
 * An exception thrown when there is no cache found (not registered) for a specific class.
 */
public class NoCacheException extends Exception {

    /**
     * Constructs a NoCacheException.
     * @param aClass The class of the cache.
     */
    public NoCacheException(final Class<?> aClass) {
        super("No cache found for class '" + aClass.getSimpleName() + "'. Use Cashew.registerClass() to create a new cache for this class.");
    }

    public NoCacheException(final String cacheName) {
        super("No cache found for name '" + cacheName + "'. Use Cashew.createCache() to create a new cache.");
    }


    /**
     * Constructs a NoCacheException.
     */
    NoCacheException() {
        super("No main cache found");
    }

}
