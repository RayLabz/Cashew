package com.raylabz.cashew.tcpserver.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.raylabz.cashew.Cache;
import com.raylabz.cashew.tcpserver.server.CashewTCPServer;
import com.raylabz.cashew.tcpserver.server.request.*;
import com.raylabz.mocha.client.TCPClient;

import java.io.IOException;

public abstract class CashewTCPClient extends TCPClient {

    public CashewTCPClient(String ipAddress) throws IOException {
        super("Cashew client", ipAddress, CashewTCPServer.PORT);
    }

    public void doCreateCacheRequest(Class<?> objectClass, long timeToLive, long cleanupInterval) {
        CreateCacheRequest createCacheRequest = new CreateCacheRequest(objectClass.getName());
        createCacheRequest.setTimeToLive(timeToLive);
        createCacheRequest.setCleanupInterval(cleanupInterval);
        String requestString = new Gson().toJson(createCacheRequest);
//        System.out.println("Sending: " + requestString); 
        send(requestString);
    }

    public void doCreateCacheRequest(Class<?> objectClass) {
        CreateCacheRequest createCacheRequest = new CreateCacheRequest(objectClass.getName());
        String requestString = new Gson().toJson(createCacheRequest);
//        System.out.println("Sending: " + requestString); 
        send(requestString);
    }

    public void doAddRequest(String key, Object value, Class<?> objectClass) {
        final JsonElement jsonElement = new Gson().toJsonTree(value);
        final String className = (objectClass == null) ? null : objectClass.getName();
        AddRequest addRequest = new AddRequest(key, new Gson().toJson(jsonElement), className);
        String requestString = new Gson().toJson(addRequest);
//        System.out.println("Sending: " + requestString); 
        send(requestString);
    }

    public void doDeleteRequest(String key, Class<?> objectClass) {
        final String className = (objectClass == null) ? null : objectClass.getName();
        DeleteRequest deleteRequest = new DeleteRequest(key, className);
        String requestString = new Gson().toJson(deleteRequest);
//        System.out.println("Sending: " + requestString); 
        send(requestString);
    }

    public void doUpdateRequest(String key, Object value) {
        final JsonElement jsonElement = new Gson().toJsonTree(value);
        UpdateRequest updateRequest = new UpdateRequest(key, new Gson().toJson(jsonElement), value.getClass().getName());
        String requestString = new Gson().toJson(updateRequest);
//        System.out.println("Sending: " + requestString); 
        send(requestString);
    }

    public void doGetRequest(String key, Class<?> objectClass) {
        final String className = (objectClass == null) ? null : objectClass.getName();
        GetRequest getRequest = new GetRequest(key, className);
        String requestString = new Gson().toJson(getRequest);
//        System.out.println("Sending: " + requestString); 
        send(requestString);
    }

    public void doListRequest(Class<?> objectClass) {
        if (objectClass != null) {
            ListRequest listRequest = new ListRequest(objectClass.getName());
            String requestString = new Gson().toJson(listRequest);
//            System.out.println("Sending: " + requestString); 
            send(requestString);
        }
        else {
            System.err.println("Cannot list entities of type 'null'.");
        }
    }

}
