package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

public class UpdateRequest extends GenericRequest {

    private String key;

    private String value;

    private String objectClass;

    public UpdateRequest(String key, String value, String objectClass) {
        super(ServiceType.UPDATE);
        this.key = key;
        this.value = value;
        this.objectClass = objectClass;
    }

    public UpdateRequest() {
        super(ServiceType.UPDATE);
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
