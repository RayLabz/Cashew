package com.raylabz.cashew;

import com.raylabz.cashew.iterator.CacheIterator;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Models a cache, which can store objects with a specified key type and value type.
 * @param <K> The key type of the cache.
 * @param <V> The value type of the cache.
 */
public class Cache<K, V> {

    /**
     * The default time for objects to be kept in the cache without being deleted.
     * 1 week.
     */
    static final long DEFAULT_TIME_TO_LIVE = 7 * 24 * 60 * 60 * 1000L;

    /**
     * The default time interval for running cleanup operations.
     * 1 hour.
     */
    static final long DEFAULT_CLEANUP_INTERVAL = 60 * 60 * 1000L; //1 hour

    /**
     * The time for objects to be kept in the cache without being deleted.
     */
    private long timeToLive;

    /**
     * The time interval for running cleanup operations.
     */
    private long cleanupInterval;

    /**
     * A map holding items in this cache.
     */
    private final LRUMap<K, CacheItem<V>> map;

    //TODO - Background backup into files

    /**
     * Constructs a cache.
     * @param timeToLive The cache's default TTL.
     * @param cleanupInterval The cache's cleanup interval.
     */
    Cache(long timeToLive, long cleanupInterval) {
        this.timeToLive = timeToLive;
        this.map = new LRUMap<>();
        if (timeToLive > 0 && cleanupInterval > 0) {
            Thread cleanupThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(cleanupInterval);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cleanup();
                }
            });
            cleanupThread.setDaemon(true);
            cleanupThread.start();
        }
    }

    /**
     * Constucts a cache.
     * @param cacheClass The cache class.
     */
    Cache(Class<?> cacheClass) {
        this(DEFAULT_TIME_TO_LIVE, DEFAULT_CLEANUP_INTERVAL);
    }

    /**
     * Adds an object into the cache, replacing any previous objects with the same key.
     * @param key The object key.
     * @param value The object value.
     */
    public boolean add(K key, V value) {
        synchronized (map) {
            return map.put(key, new CacheItem<>(value)) != null;
        }
    }

    /**
     * Updates an object in the cache with a new value.
     * @param key The key of the object.
     * @param newValue The new value of the object.
     * @return Returns true if the value was updated, false otherwise.
     */
    public boolean update(K key, V newValue) {
        synchronized (map) {
            CacheItem<V> item = map.get(key);
            if (item != null) {
                item.setValue(newValue);
                item.setLastAccessed(System.currentTimeMillis());
                item.setUpdatedOn(System.currentTimeMillis());
                if (item.getOnItemUpdateListener() != null) {
                    item.getOnItemUpdateListener().onUpdate(item);
                }
                return true;
            }
            return false;
        }
    }

    /**
     * Retrieves an object from the cache.
     * @param key The key of the object to retrieve.
     * @return Returns a value.
     */
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

    /**
     * Retrieves an object from the cache as a CacheItem.
     * @param key The key of the object to retrieve.
     * @return Returns a CacheItem.
     */
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

    /**
     * Deletes an object from the cache.
     * @param key The key of the object to remove.
     * @return Returns true if the item was deleted, false otherwise.
     */
    public boolean delete(K key) {
        synchronized (map) {
            final CacheItem<V> removedItem = map.remove(key);
            if (removedItem != null) {
                if (removedItem.getOnItemDeleteListener() != null) {
                    removedItem.getOnItemDeleteListener().onDelete(removedItem);
                }
                return true;
            }
            return false;
        }
    }

    /**
     * Retrieves the size of the cache (number of objects added to the cache).
     * @return Returns an integer.
     */
    public int size() {
        synchronized (map) {
            return map.size();
        }
    }

    /**
     * Cleans up the cache by deleting all objects that have expired.
     */
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
            delete(key);
            Thread.yield();
        }
    }

    /**
     * Retrieves the time to live (TTL) of this cache.
     * @return Returns a long.
     */
    public long getTimeToLive() {
        return timeToLive;
    }

    /**
     * Sets the time to live (TTL) of this cache.
     * @param timeToLive The TTL to set.
     */
    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * Retrieves the cleanup interval time of this cache.
     * @return Returns a long.
     */
    public long getCleanupInterval() {
        return cleanupInterval;
    }

    /**
     * Sets the cleanup interval time of this cache.
     * @param cleanupInterval The cleanup interval to set.
     */
    public void setCleanupInterval(long cleanupInterval) {
        this.cleanupInterval = cleanupInterval;
    }

    /**
     * Retrieves the cache map.
     * @return Returns an LRUMap.
     */
    LRUMap<K, CacheItem<V>> getMap() {
        return map;
    }

    /**
     * Iterates through all items in a cache.
     * @param iterator The iterator.
     */
    public void forAll(CacheIterator<K, V> iterator) {
        for (Map.Entry<K, CacheItem<V>> entry : map.entrySet()) {
            iterator.forEach(entry.getKey(), entry.getValue());
        }
    }

}
