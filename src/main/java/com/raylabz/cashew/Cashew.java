package com.raylabz.cashew;

import com.raylabz.cashew.exception.NoCacheException;

import java.util.HashMap;

/**
 * Allows access to the caches using simple commands.
 */
public final class Cashew {

    /**
     * A map of classes to caches which allows objects to be stored according to their types.
     */
    private static final HashMap<Class<?>, StringCache<?>> OBJECT_CACHES = new HashMap<>();

    /**
     * A map of string-named caches.
     */
    private static final HashMap<String, StringCache<?>> GENERIC_CACHES = new HashMap<>();

    /**
     * The main cache, used for storing any type of object.
     */
    private static final StringCache<Object> MAIN_CACHE = new StringCache<>(Cache.DEFAULT_TIME_TO_LIVE, Cache.DEFAULT_CLEANUP_INTERVAL);

    /**
     * Registers a class for use in the object-oriented cache.
     * @param aClass The class to register.
     * @param timeToLive The class cache TTL.
     * @param cleanupInterval The class cache cleanup interval.
     */
    public static void registerClass(Class<?> aClass, long timeToLive, long cleanupInterval) {
        if (!OBJECT_CACHES.containsKey(aClass)) {
            StringCache<?> cache = new StringCache<>(timeToLive, cleanupInterval);
            OBJECT_CACHES.put(aClass, cache);
        }
    }

    /**
     * Registers a class for use in the object-oriented cache.
     * @param aClass The class to register.
     */
    public static void registerClass(Class<?> aClass) {
        registerClass(aClass, Cache.DEFAULT_TIME_TO_LIVE, Cache.DEFAULT_CLEANUP_INTERVAL);
    }

    /**
     * Creates a generic cache.
     * @param cacheName The cache name.
     * @param timeToLive The cache TTL.
     * @param cleanupInterval The cleanup interval.
     */
    public static void createCache(String cacheName, long timeToLive, long cleanupInterval) {
        StringCache<?> cache = new StringCache<>(timeToLive, cleanupInterval);
        GENERIC_CACHES.put(cacheName, cache);
    }

    /**
     * Creates a generic cache.
     * @param cacheName The cache name.
     */
    public static void createCache(String cacheName) {
        createCache(cacheName, Cache.DEFAULT_TIME_TO_LIVE, Cache.DEFAULT_CLEANUP_INTERVAL);
    }

    /**
     * Retrieves an instance of the main cache.
     * @return Returns the main cache.
     */
    public static StringCache<Object> getMainCache() {
        return MAIN_CACHE;
    }

    /**
     * Retrieves a class cache.
     * @param aClass The class that the cache belongs to.
     * @param <T> A type matching the class of the cache.
     * @return Returns a StringCache of type T.
     * @throws NoCacheException Throws an exception when no such cache is registered.
     */
    public static <T> StringCache<T> getCache(Class<?> aClass) throws NoCacheException {
        final StringCache<T> cache = (StringCache<T>) OBJECT_CACHES.get(aClass);
        if (cache != null) {
            return cache;
        } else {
            throw new NoCacheException(aClass);
        }
    }

    /**
     * Retrieves a generic cache.
     * @param cacheName The cache name.
     * @return Returns a StringCache.
     * @throws NoCacheException Throws an exception when the cache was not found.
     */
    public static StringCache<String> getCache(String cacheName) throws NoCacheException {
        final StringCache<String> cache = (StringCache<String>) GENERIC_CACHES.get(cacheName);
        if (cache != null) {
            return cache;
        } else {
            throw new NoCacheException(cacheName);
        }
    }

}
