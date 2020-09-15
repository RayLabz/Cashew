package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

public class GenericRequest {

    private ServiceType service;

    public GenericRequest(ServiceType service) {
        this.service = service;
    }

    public GenericRequest() {

    }

    public ServiceType getService() {
        return service;
    }

}
