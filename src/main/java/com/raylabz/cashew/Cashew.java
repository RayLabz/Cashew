package com.raylabz.cashew;

import java.util.HashMap;

public final class Cashew {

    private static final HashMap<Class<?>, StringCache<?>> caches = new HashMap<>();
    private static final StringCache<Object> mainCache = new StringCache<>(Cache.DEFAULT_TIME_TO_LIVE, Cache.DEFAULT_CLEANUP_INTERVAL);

    public static void registerClass(Class<?> aClass, long timeToLive, long cleanupInterval) {
        if (!caches.containsKey(aClass)) {
            StringCache<?> cache = new StringCache<>(timeToLive, cleanupInterval);
            caches.put(aClass, cache);
        }
    }

    public static void registerClass(Class<?> aClass) {
        registerClass(aClass, Cache.DEFAULT_TIME_TO_LIVE, Cache.DEFAULT_CLEANUP_INTERVAL);
    }

    public static StringCache<Object> getMainCache() throws NoCacheException {
        return mainCache;
    }

    public static <T> StringCache<T> getCache(Class<?> aClass) throws NoCacheException {
        final StringCache<T> cache = (StringCache<T>) caches.get(aClass);
        if (cache != null) {
            return cache;
        } else {
            throw new NoCacheException(aClass);
        }
    }

}
