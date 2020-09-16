package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

/**
 * Models an add request.
 */
public class AddRequest extends GenericRequest {

    private String key;

    private String value;

    private String objectClass;

    /**
     * Constructs a new AddRequest.
     * @param key The key of the object to add.
     * @param value The value of the object to add.
     * @param objectClass The class of the object to add.
     */
    public AddRequest(String key, String value, String objectClass) {
        super(ServiceType.ADD);
        this.key = key;
        this.value = value;
        this.objectClass = objectClass;
    }

    /**
     * Mandatory public empty constructor.
     */
    public AddRequest() {
        super(ServiceType.ADD);
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
     * Retrieves the value of the object.
     * @return Returns the value (String).
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the object.
     * @param value The value (String).
     */
    public void setValue(String value) {
        this.value = value;
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
