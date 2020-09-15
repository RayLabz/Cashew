package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

public class DeleteRequest extends GenericRequest {

    private String key;

    private String objectClass;

    public DeleteRequest(String key, String objectClass) {
        super(ServiceType.DELETE);
        this.key = key;
        this.objectClass = objectClass;
    }

    public DeleteRequest() {
        super(ServiceType.DELETE);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

}
