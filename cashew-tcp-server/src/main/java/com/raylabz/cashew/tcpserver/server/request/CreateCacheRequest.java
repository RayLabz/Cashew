package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.Cache;
import com.raylabz.cashew.tcpserver.ServiceType;

/**
 * Models a CreateCacheRequest.
 */
public class CreateCacheRequest extends GenericRequest {

    private String objectClass;

    private long timeToLive = Cache.DEFAULT_TIME_TO_LIVE;

    private long cleanupInterval = Cache.DEFAULT_CLEANUP_INTERVAL;

    /**
     * Constructs a new CreateCacheRequest.
     * @param objectClass The class of the cache.
     */
    public CreateCacheRequest(String objectClass) {
        super(ServiceType.CREATE_CACHE);
        this.objectClass = objectClass;
    }

    /**
     * Constructs a new CreateCacheRequest.
     */
    public CreateCacheRequest() {
        super(ServiceType.CREATE_CACHE);
    }

    /**
     * Retrieves the class of the cache.
     * @return Returns a String-formatted class name.
     */
    public String getObjectClass() {
        return objectClass;
    }

    /**
     * Sets the class of the cache.
     * @param objectClass An object class, string-formatted.
     */
    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    /**
     * Retrieves the cache's TTL.
     * @return Returns a long.
     */
    public long getTimeToLive() {
        return timeToLive;
    }

    /**
     * Sets the cache's TTL.
     * @param timeToLive The cache TTL to set.
     */
    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * Retrieves the cache's cleanup interval.
     * @return Returns a long.
     */
    public long getCleanupInterval() {
        return cleanupInterval;
    }

    /**
     * Sets the cache's cleanup interval.
     * @param cleanupInterval The cleanup interval to set for the cache.
     */
    public void setCleanupInterval(long cleanupInterval) {
        this.cleanupInterval = cleanupInterval;
    }

}
