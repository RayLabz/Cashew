package com.raylabz.cashew.tcpserver;

import com.raylabz.cashew.tcpserver.server.API;
import com.raylabz.servexpresso.Service;

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

    ServiceType(String name, Service service) {
        this.name = name;
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public Service getService() {
        return service;
    }

}
