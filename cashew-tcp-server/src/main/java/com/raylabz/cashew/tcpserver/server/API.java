package com.raylabz.cashew.tcpserver.server;

import com.google.gson.*;
import com.raylabz.cashew.Cache;
import com.raylabz.cashew.Cashew;
import com.raylabz.cashew.StringCache;
import com.raylabz.cashew.exception.NoCacheException;
import com.raylabz.responz.ErrorResponse;
import com.raylabz.responz.Response;
import com.raylabz.responz.SuccessResponse;
import com.raylabz.servexpresso.InputParams;
import com.raylabz.servexpresso.ParamType;
import com.raylabz.servexpresso.Service;
import com.raylabz.servexpresso.Serviceable;

/**
 * Models services.
 */
public class API {

    /**
     * Creates a new cache.
     */
    public static final Service CREATE_CACHE_SERVICE = new Service.Builder()
            .expectParam("objectClass", ParamType.STRING, true)
            .expectParam("timeToLive", ParamType.LONG, false)
            .expectParam("cleanupInterval", ParamType.LONG, false)
            .implement(new Serviceable() {
                @Override
                public Response serve(InputParams params) {

                    final String objectClass = params.getString("objectClass");
                    try {
                        Cashew.getCache(objectClass);
                        return new ErrorResponse("Cache exists", "A cache for this class already exists.");
                    } catch (NoCacheException e) {
                        long ttl = Cache.DEFAULT_TIME_TO_LIVE;
                        long interval = Cache.DEFAULT_CLEANUP_INTERVAL;
                        if (params.paramExists("timeToLive")) {
                            ttl = params.getLong("timeToLive");
                        }
                        if (params.paramExists("cleanupInterval")) {
                            interval = params.getLong("cleanupInterval");
                        }
                        Cashew.createCache(objectClass, ttl, interval);
                        return new SuccessResponse("Cache created", "Cache '" + objectClass + "' created.");
                    }

                }
            })
            .build();

    /**
     * Adds a new item in a specified cache.
     */
    public static final Service ADD_SERVICE = new Service.Builder()
            .expectParam("key", ParamType.STRING, true)
            .expectParam("value", ParamType.STRING, true)
            .expectParam("objectClass", ParamType.STRING, false)
            .implement(new Serviceable() {
                @Override
                public Response serve(InputParams params) {

                    final String key = params.getString("key");
                    final String value = params.getString("value");
                    if (params.paramExists("objectClass")) {
                        final String objectClass = params.getString("objectClass");
                        try {
                            StringCache<String> cache = Cashew.getCache(objectClass);
                            cache.add(key, value);
                            return new SuccessResponse("Value added", "The value has been added.");
                        } catch (NoCacheException e) {
                            return new ErrorResponse("No cache", "The cache '" + objectClass + "' does not exist.");
                        }
                    }
                    else {
                        StringCache<Object> cache = Cashew.getMainCache();
                        cache.add(key, value);
                        return new SuccessResponse("Value added", "The value has been added.");
                    }
                }
            })
            .build();

    /**
     * Delets an item from a cache.
     */
    public static final Service DELETE_SERVICE = new Service.Builder()
            .expectParam("key", ParamType.STRING, true)
            .expectParam("objectClass", ParamType.STRING, false)
            .implement(new Serviceable() {
                @Override
                public Response serve(InputParams params) {

                    final String key = params.getString("key");
                    if (params.paramExists("objectClass")) {
                        final String objectClass = params.getString("objectClass");
                        try {
                            StringCache<String> cache = Cashew.getCache(objectClass);
                            cache.delete(key);
                            return new SuccessResponse("Value deleted", "The value has been deleted.");
                        } catch (NoCacheException e) {
                            return new ErrorResponse("No cache", "The cache '" + objectClass + "' does not exist.");
                        }
                    }
                    else {
                        StringCache<Object> cache = Cashew.getMainCache();
                        cache.delete(key);
                        return new SuccessResponse("Value deleted", "The value has been deleted.");
                    }

                }
            })
            .build();

    /**
     * Updates an item in a cache.
     */
    public static final Service UPDATE_SERVICE = new Service.Builder()
            .expectParam("key", ParamType.STRING, true)
            .expectParam("value", ParamType.STRING, true)
            .expectParam("objectClass", ParamType.STRING, false)
            .implement(new Serviceable() {
                @Override
                public Response serve(InputParams params) {

                    final String key = params.getString("key");
                    final String value = params.getString("value");
                    if (params.paramExists("objectClass")) {
                        final String objectClass = params.getString("objectClass");
                        try {
                            StringCache<String> cache = Cashew.getCache(objectClass);
                            cache.update(key, value);
                            return new SuccessResponse("Value updated", "The value has been updated.");
                        } catch (NoCacheException e) {
                            return new ErrorResponse("No cache", "The cache '" + objectClass + "' does not exist.");
                        }
                    }
                    else {
                        StringCache<Object> cache = Cashew.getMainCache();
                        cache.update(key, value);
                        return new SuccessResponse("Value updated", "The value has been updated.");
                    }

                }
            })
            .build();

    /**
     * Retrieves an item from a cache.
     */
    public static final Service GET_SERVICE = new Service.Builder()
            .expectParam("key", ParamType.STRING, true)
            .expectParam("objectClass", ParamType.STRING, false)
            .implement(new Serviceable() {
                @Override
                public Response serve(InputParams params) {

                    final String key = params.getString("key");
                    if (params.paramExists("objectClass")) {
                        final String objectClass = params.getString("objectClass");
                        try {
                            StringCache<String> cache = Cashew.getCache(objectClass);
                            final String value = cache.get(key);
                            if (value != null) {
                                JsonElement valueElement = new Gson().fromJson(value, JsonObject.class);
                                JsonObject data = new JsonObject();
                                data.add("value", valueElement);
                                return new SuccessResponse("Value fetched", "The value was fetched", data);
                            }
                            else {
                                return new ErrorResponse("Failed", "Could not fetch value.");
                            }
                        } catch (NoCacheException e) {
                            return new ErrorResponse("No cache", "The cache '" + objectClass + "' does not exist.");
                        }
                    }
                    else {
                        StringCache<Object> cache = Cashew.getMainCache();
                        final Object value = cache.get(key);
                        if (value != null) {
                            System.out.println("value: " + value);
                            JsonElement valueElement = null;
                            try {
                                valueElement = new Gson().fromJson(value.toString(), JsonObject.class);
                            } catch (JsonSyntaxException e) {
                                try {
                                    valueElement = new Gson().fromJson(value.toString(), JsonPrimitive.class);
                                } catch (JsonSyntaxException jsonSyntaxException) {
                                    jsonSyntaxException.printStackTrace();
                                }
                            }

                            if (valueElement != null) {
                                JsonObject data = new JsonObject();
                                data.add("value", valueElement);
                                return new SuccessResponse("Value fetched", "The value was fetched", data);
                            }
                            else {
                                return new ErrorResponse("Failed", "Could not fetch value. Invalid data type.");
                            }
                        }
                        else {
                            return new ErrorResponse("Failed", "Could not fetch value.");
                        }
                    }

                }
            })
            .build();

    /**
     * Lists all items inside a given cache.
     */
    public static final Service LIST_SERVICE = new Service.Builder()
            .expectParam("objectClass", ParamType.STRING, true)
            .implement(new Serviceable() {
                @Override
                public Response serve(InputParams params) {

                    final String objectClass = params.getString("objectClass");
                    try {
                        StringCache<String> cache = Cashew.getCache(objectClass);

                        final JsonArray jsonArray = new JsonArray(cache.size());
                        final Gson gson = new Gson();

                        cache.forAll((key, item) -> {

                            JsonElement valueElement = null;
                            try {
                                valueElement = new Gson().fromJson(item.getValue(), JsonObject.class);
                            } catch (JsonSyntaxException e) {
                                try {
                                    valueElement = new Gson().fromJson(item.getValue(), JsonPrimitive.class);
                                } catch (JsonSyntaxException jsonSyntaxException) {
                                    jsonSyntaxException.printStackTrace();
                                }
                            }

                            if (valueElement != null) {
                                JsonObject entireObject = new JsonObject();
                                entireObject.addProperty("key", key);
                                JsonObject itemObject = new JsonObject();
                                itemObject.add("value", valueElement);
                                itemObject.addProperty("createdOn", item.getCreatedOn());
                                itemObject.addProperty("updatedOn", item.getUpdatedOn());
                                itemObject.addProperty("lastAccessed", item.getLastAccessed());
                                entireObject.add("item", itemObject);
                                jsonArray.add(entireObject);
                            }
                        });

                        JsonObject data = new JsonObject();
                        data.addProperty("class", objectClass);
                        data.add("items", jsonArray);
                        return new SuccessResponse("Items listed", "Items listed for class '" + objectClass + "'.", data);

                    } catch (NoCacheException e) {
                        return new ErrorResponse("No cache", "The cache '" + objectClass + "' does not exist.");
                    }

                }
            })
            .build();


}
