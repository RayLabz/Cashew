package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

public class ListRequest extends GenericRequest {

    private String objectClass;

    public ListRequest(String objectClass) {
        super(ServiceType.LIST);
        this.objectClass = objectClass;
    }

    public ListRequest() {
        super(ServiceType.LIST);
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

}
