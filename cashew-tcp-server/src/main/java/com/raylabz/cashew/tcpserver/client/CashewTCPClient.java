package com.raylabz.cashew.tcpserver.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.raylabz.cashew.tcpserver.server.CashewTCPServer;
import com.raylabz.cashew.tcpserver.server.request.*;
import com.raylabz.mocha.client.TCPClient;

import java.io.IOException;

/**
 * Models a TCP cache client.
 */
public abstract class CashewTCPClient extends TCPClient {

    /**
     * Constructs a new CashewTCPClient.
     * @param ipAddress The IP address to connect the client to.
     * @throws IOException Throws an exception when the socket to the server cannot be instantiated.
     */
    public CashewTCPClient(String ipAddress) throws IOException {
        super("Cashew client", ipAddress, CashewTCPServer.PORT);
    }

    /**
     * Sends a CreateCacheRequest to the server.
     * @param objectClass The class of the cache.
     * @param timeToLive The cache TTL.
     * @param cleanupInterval The cache cleanup interval.
     */
    public void doCreateCacheRequest(Class<?> objectClass, long timeToLive, long cleanupInterval) {
        CreateCacheRequest createCacheRequest = new CreateCacheRequest(objectClass.getName());
        createCacheRequest.setTimeToLive(timeToLive);
        createCacheRequest.setCleanupInterval(cleanupInterval);
        String requestString = new Gson().toJson(createCacheRequest);
        send(requestString);
    }

    /**
     * Sends a CreateCacheRequest.
     * @param objectClass The class of the cache.
     */
    public void doCreateCacheRequest(Class<?> objectClass) {
        CreateCacheRequest createCacheRequest = new CreateCacheRequest(objectClass.getName());
        String requestString = new Gson().toJson(createCacheRequest);
        send(requestString);
    }

    /**
     * Sends an AddRequest to the server.
     * @param key The key of the object.
     * @param value The value of the object.
     * @param objectClass The object class.
     */
    public void doAddRequest(String key, Object value, Class<?> objectClass) {
        final JsonElement jsonElement = new Gson().toJsonTree(value);
        final String className = (objectClass == null) ? null : objectClass.getName();
        AddRequest addRequest = new AddRequest(key, new Gson().toJson(jsonElement), className);
        String requestString = new Gson().toJson(addRequest);
        send(requestString);
    }

    /**
     * Sends a DeleteRequest to the server.
     * @param key The key of the object to delete.
     * @param objectClass The class of the object to delete.
     */
    public void doDeleteRequest(String key, Class<?> objectClass) {
        final String className = (objectClass == null) ? null : objectClass.getName();
        DeleteRequest deleteRequest = new DeleteRequest(key, className);
        String requestString = new Gson().toJson(deleteRequest);
        send(requestString);
    }

    /**
     * Sends an UpdateRequest to the server.
     * @param key The key of the object to update.
     * @param value The value of the object to update.
     */
    public void doUpdateRequest(String key, Object value) {
        final JsonElement jsonElement = new Gson().toJsonTree(value);
        UpdateRequest updateRequest = new UpdateRequest(key, new Gson().toJson(jsonElement), value.getClass().getName());
        String requestString = new Gson().toJson(updateRequest);
        send(requestString);
    }

    /**
     * Sends a GetRequest to the server.
     * @param key The key of the object to retrieve.
     * @param objectClass The class of the object to retrieve.
     */
    public void doGetRequest(String key, Class<?> objectClass) {
        final String className = (objectClass == null) ? null : objectClass.getName();
        GetRequest getRequest = new GetRequest(key, className);
        String requestString = new Gson().toJson(getRequest);
        send(requestString);
    }

    /**
     * Sends a ListRequest to the server.
     * @param objectClass The class to list.
     */
    public void doListRequest(Class<?> objectClass) {
        if (objectClass != null) {
            ListRequest listRequest = new ListRequest(objectClass.getName());
            String requestString = new Gson().toJson(listRequest);
            send(requestString);
        }
        else {
            System.err.println("Cannot list entities of type 'null'.");
        }
    }

}
