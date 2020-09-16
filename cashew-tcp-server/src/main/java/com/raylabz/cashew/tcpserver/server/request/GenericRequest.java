package com.raylabz.cashew.tcpserver.server.request;

import com.raylabz.cashew.tcpserver.ServiceType;

/**
 * Models a GenericRequest - superclass of all other requests.
 */
public class GenericRequest {

    private ServiceType service;

    /**
     * Constructs a GenericRequest.
     * @param service The service tied to this request.
     */
    public GenericRequest(ServiceType service) {
        this.service = service;
    }

    /**
     * Mandatory public empty constructor.
     */
    public GenericRequest() {

    }

    /**
     * The request's service
     * @return Returns a ServiceType.
     */
    public ServiceType getService() {
        return service;
    }

}
