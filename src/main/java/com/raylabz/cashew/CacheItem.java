package com.raylabz.cashew;

class CacheItem<T> {

    private long createdOn;
    private long updatedOn;
    private long lastAccessed;
    private T value;

    protected CacheItem(T value) {
        this.createdOn = System.currentTimeMillis();
        this.updatedOn = createdOn;
        this.lastAccessed = updatedOn;
        this.value = value;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public long getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
