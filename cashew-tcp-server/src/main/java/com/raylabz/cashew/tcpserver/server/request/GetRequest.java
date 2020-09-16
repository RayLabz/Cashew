package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

/**
 * Models a GetRequest.
 */
public class GetRequest extends GenericRequest {

    private String key;

    private String objectClass;

    /**
     * Constructs a new GetRequest.
     * @param key The key of the object to get.
     * @param objectClass The class of the object to get.
     */
    public GetRequest(String key, String objectClass) {
        super(ServiceType.GET);
        this.key = key;
        this.objectClass = objectClass;
    }

    /**
     * Mandatory public empty constructor.
     */
    public GetRequest() {
        super(ServiceType.GET);
    }

    /**
     * Retrieves the key of the object.
     * @return Returns a String.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key of the object.
     * @param key The key (String).
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Retrieves the object's class.
     * @return Returns a string-formatted class name.
     */
    public String getObjectClass() {
        return objectClass;
    }

    /**
     * Sets the object's class.
     * @param objectClass The object class to set.
     */
    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

}
