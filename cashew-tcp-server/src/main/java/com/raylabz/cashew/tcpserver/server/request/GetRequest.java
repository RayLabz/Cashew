package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

public class GetRequest extends GenericRequest {

    private String key;

    private String objectClass;

    public GetRequest(String key, String objectClass) {
        super(ServiceType.GET);
        this.key = key;
        this.objectClass = objectClass;
    }

    public GetRequest() {
        super(ServiceType.GET);
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
