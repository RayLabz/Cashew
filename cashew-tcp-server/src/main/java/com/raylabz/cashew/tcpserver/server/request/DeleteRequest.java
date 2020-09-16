package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

/**
 * Models a DeleteRequest.
 */
public class DeleteRequest extends GenericRequest {

    private String key;

    private String objectClass;

    /**
     * Constructs a new DeleteRequest.
     * @param key The key of the object to remove.
     * @param objectClass The class of the object to remove.
     */
    public DeleteRequest(String key, String objectClass) {
        super(ServiceType.DELETE);
        this.key = key;
        this.objectClass = objectClass;
    }

    /**
     * Mandatory public empty constructor.
     */
    public DeleteRequest() {
        super(ServiceType.DELETE);
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
