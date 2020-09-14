package com.raylabz.cashew;

import com.google.gson.Gson;
import com.raylabz.cashew.listener.OnItemDeleteListener;
import com.raylabz.cashew.listener.OnItemUpdateListener;

/**
 * Models an item that can exist in the cache. This object type behaves as a wrapper around stored objects, giving them
 * several additional properties.
 * @param <T> The type of the object stored.
 */
public final class CacheItem<T> {

    /**
     * The time this object was created on.
     */
    private final long createdOn;

    /**
     * The time this object was last updated.
     */
    private long updatedOn;

    /**
     * The time this object was last accessed.
     */
    private long lastAccessed;

    /**
     * The object.
     */
    private T value;

    /**
     * The item's OnItemUpdateListener.
     */
    private OnItemUpdateListener onItemUpdateListener = null;

    /**
     * The item's OnItemDeleteListener.
     */
    private OnItemDeleteListener onItemDeleteListener = null;

    /**
     * Constructs a CacheItem.
     * @param value The object to wrap.
     */
    protected CacheItem(T value) {
        this.createdOn = System.currentTimeMillis();
        this.updatedOn = createdOn;
        this.lastAccessed = updatedOn;
        this.value = value;
    }

    /**
     * Retrieves the creation time of this object.
     * @return Returns a long.
     */
    public long getCreatedOn() {
        return createdOn;
    }

    /**
     * Retrieves the update time of this object.
     * @return Returns a long.
     */
    public long getUpdatedOn() {
        return updatedOn;
    }

    /**
     * Sets the update time for this object.
     * @param updatedOn The update time.
     */
    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * Retrieves the last access time of this object.
     * @return Returns a long.
     */
    public long getLastAccessed() {
        return lastAccessed;
    }

    /**
     * Sets the last access time for this object.
     * @param lastAccessed The last access time.
     */
    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    /**
     * Retrieves the object of this CacheItem.
     * @return Returns an object.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the object of this CacheItem.
     * @param value The object.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Retrieves the item's OnItemUpdateListener.
     * @return Returns an OnItemUpdateListener.
     */
    public OnItemUpdateListener getOnItemUpdateListener() {
        return onItemUpdateListener;
    }

    /**
     * Sets the item's OnItemUpdateListener.
     * @param onItemUpdateListener The listener to set.
     */
    public void setOnItemUpdateListener(OnItemUpdateListener onItemUpdateListener) {
        this.onItemUpdateListener = onItemUpdateListener;
    }

    /**
     * Retrieves the item's OnItemDeleteListener.
     * @return Returns an OnItemDeleteListener.
     */
    public OnItemDeleteListener getOnItemDeleteListener() {
        return onItemDeleteListener;
    }

    /**
     * Sets the item's OnItemDeleteListener.
     * @param onItemDeleteListener The listener to set.
     */
    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    /**
     * Clones an item into a new item.
     * @return Returns a new CacheItem with the same value as the cloned item.
     */
    public CacheItem<T> copy() {
        final Gson GSON = new Gson();
        String jsonStr = GSON.toJson(this);
        return GSON.fromJson(jsonStr, CacheItem.class);
    }

}
