package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.Cache;
import com.raylabz.cashew.tcpserver.ServiceType;

public class CreateCacheRequest extends GenericRequest {

    private String objectClass;

    private long timeToLive = Cache.DEFAULT_TIME_TO_LIVE;

    private long cleanupInterval = Cache.DEFAULT_CLEANUP_INTERVAL;

    public CreateCacheRequest(String objectClass) {
        super(ServiceType.CREATE_CACHE);
        this.objectClass = objectClass;
    }

    public CreateCacheRequest() {
        super(ServiceType.CREATE_CACHE);
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
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

}
