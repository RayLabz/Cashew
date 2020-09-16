package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

/**
 * Models a ListRequest.
 */
public class ListRequest extends GenericRequest {

    private String objectClass;

    /**
     * Constructs a new ListRequest.
     * @param objectClass The class of objects to list.
     */
    public ListRequest(String objectClass) {
        super(ServiceType.LIST);
        this.objectClass = objectClass;
    }

    /**
     * Mandatory public empty constructor.
     */
    public ListRequest() {
        super(ServiceType.LIST);
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
