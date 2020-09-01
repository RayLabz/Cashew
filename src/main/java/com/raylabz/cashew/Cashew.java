package com.raylabz.cashew;

import java.util.HashMap;

/**
 * Allows access to the caches using simple commands.
 */
public final class Cashew {

    /**
     * A map of classes to caches which allows objects to be stored according to their types.
     */
    private static final HashMap<Class<?>, StringCache<?>> caches = new HashMap<>();

    /**
     * The main cache, used for storing any type of object.
     */
    private static final StringCache<Object> mainCache = new StringCache<>(Cache.DEFAULT_TIME_TO_LIVE, Cache.DEFAULT_CLEANUP_INTERVAL);

    /**
     * Registers a class for use in the object-oriented cache.
     * @param aClass The class to register.
     * @param timeToLive The class cache TTL.
     * @param cleanupInterval The class cache cleanup interval.
     */
    public static void registerClass(Class<?> aClass, long timeToLive, long cleanupInterval) {
        if (!caches.containsKey(aClass)) {
            StringCache<?> cache = new StringCache<>(timeToLive, cleanupInterval);
            caches.put(aClass, cache);
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
     * Retrieves an instance of the main cache.
     * @return Returns the main cache.
     */
    public static StringCache<Object> getMainCache() {
        return mainCache;
    }

    /**
     * Retrieves a class cache.
     * @param aClass The class that the cache belongs to.
     * @param <T> A type matching the class of the cache.
     * @return Returns a StringCache of type T.
     * @throws NoCacheException Throws an exception when no such cache is registered.
     */
    public static <T> StringCache<T> getCache(Class<?> aClass) throws NoCacheException {
        final StringCache<T> cache = (StringCache<T>) caches.get(aClass);
        if (cache != null) {
            return cache;
        } else {
            throw new NoCacheException(aClass);
        }
    }

}
