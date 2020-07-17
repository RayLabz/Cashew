package com.raylabz.cashew;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;
import java.util.ArrayList;

public class Cache<K, V> {

    static final long DEFAULT_TIME_TO_LIVE = 7 * 24 * 60 * 60 * 1000L; //1 week
    static final long DEFAULT_CLEANUP_INTERVAL = 60 * 60 * 1000L; //1 hour

    private long timeToLive;
    private long cleanupInterval;
    private final CacheType type;
    private final LRUMap<K, CacheItem<V>> map;

    //TODO - Listeners!

    Cache(long timeToLive, long cleanupInterval, CacheType type) {
        this.type = type;
        this.timeToLive = timeToLive;
        this.map = new LRUMap<>();
        if (timeToLive > 0 && cleanupInterval > 0) {
            Thread cleanupThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(cleanupInterval);
                    }
                    catch (InterruptedException e) {
                        System.err.println("Amplify error: " + e.getMessage());
                        e.printStackTrace();
                    }
                    cleanup();
                }
            });
            cleanupThread.setDaemon(true);
            cleanupThread.start();
        }
    }

    Cache(CacheType type, Class<?> cacheClass) {
        this(DEFAULT_TIME_TO_LIVE, DEFAULT_CLEANUP_INTERVAL, type);
    }

    public void put(K key, V value) {
        synchronized (map) {
            map.put(key, new CacheItem<>(value));
        }
    }

    public V get(K key) {
        synchronized (map) {
            CacheItem<V> item = map.get(key);
            if (item == null) {
                return null;
            }
            else {
                item.setLastAccessed(System.currentTimeMillis());
                return item.getValue();
            }
        }
    }

    public CacheItem<V> getAsCacheItem(K key) {
        synchronized (map) {
            CacheItem<V> item = map.get(key);
            if (item == null) {
                return null;
            }
            else {
                item.setLastAccessed(System.currentTimeMillis());
                return item;
            }
        }
    }

    public void remove(K key) {
        synchronized (map) {
            map.remove(key);
        }
    }

    public int size() {
        synchronized (map) {
            return map.size();
        }
    }

    void cleanup() {
        final long now = System.currentTimeMillis();
        ArrayList<K> keysToDelete;
        synchronized (map) {
            MapIterator<K, CacheItem<V>> i = map.mapIterator();
            keysToDelete = new ArrayList<K>((map.size() / 2) + 1);

            K key;
            CacheItem<V> item;

            while (i.hasNext()) {
                key = i.next();
                item = i.getValue();
                if (item != null && (now > (timeToLive + item.getLastAccessed()))) {
                    keysToDelete.add(key);
                }
            }
        }

        for (K key : keysToDelete) {
            remove(key);
            Thread.yield();
        }
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public long getCleanupInterval() {
        return cleanupInterval;
    }

    public void setCleanupInterval(long cleanupInterval) {
        this.cleanupInterval = cleanupInterval;
    }

    public CacheType getType() {
        return type;
    }

}
