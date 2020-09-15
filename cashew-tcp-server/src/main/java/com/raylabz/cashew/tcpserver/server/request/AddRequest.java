package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

public class AddRequest extends GenericRequest {

    private String key;

    private String value;

    private String objectClass;

    public AddRequest(String key, String value, String objectClass) {
        super(ServiceType.ADD);
        this.key = key;
        this.value = value;
        this.objectClass = objectClass;
    }

    public AddRequest() {
        super(ServiceType.ADD);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

}
