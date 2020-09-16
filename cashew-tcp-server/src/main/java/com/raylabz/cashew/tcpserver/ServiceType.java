package com.raylabz.cashew.tcpserver;

import com.raylabz.cashew.tcpserver.server.API;
import com.raylabz.servexpresso.Service;

/**
 * Models ServiceTypes - services provided by the CashewTCPServer's API.
 */
public enum ServiceType {

    CREATE_CACHE("CREATE_CACHE", API.CREATE_CACHE_SERVICE),
    ADD("ADD", API.ADD_SERVICE),
    DELETE("DELETE", API.DELETE_SERVICE),
    UPDATE("UPDATE", API.UPDATE_SERVICE),
    GET("GET", API.GET_SERVICE),
    LIST("LIST", API.LIST_SERVICE)

    ;

    private final String name;
    private final Service service;

    /**
     * Constructs a new ServiceType.
     * @param name The name of the service type.
     * @param service The service tied to this service type.
     */
    ServiceType(String name, Service service) {
        this.name = name;
        this.service = service;
    }

    /**
     * Retrieves the name of the ServiceType.
     * @return Returns a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the service of the ServiceType.
     * @return Returns a Service.
     */
    public Service getService() {
        return service;
    }

}
