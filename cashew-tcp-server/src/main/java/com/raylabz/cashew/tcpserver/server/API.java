package com.raylabz.cashew.tcpserver.server;

import com.google.gson.*;
import com.raylabz.cashew.Cache;
import com.raylabz.cashew.CacheItem;
import com.raylabz.cashew.Cashew;
import com.raylabz.cashew.StringCache;
import com.raylabz.cashew.exception.NoCacheException;
import com.raylabz.cashew.iterator.CacheIterator;
import com.raylabz.responz.ErrorResponse;
import com.raylabz.responz.Response;
import com.raylabz.responz.SuccessResponse;
import com.raylabz.servexpresso.InputParams;
import com.raylabz.servexpresso.ParamType;
import com.raylabz.servexpresso.Service;
import com.raylabz.servexpresso.Serviceable;
import com.sun.net.httpserver.Authenticator;

public class API {

    public static final Service CREATE_CACHE_SERVICE = new Service.Builder()
            .expectParam("objectClass", ParamType.STRING, true)
            .expectParam("timeToLive", ParamType.LONG, false)
            .expectParam("cleanupInterval", ParamType.LONG, false)
            .implement(new Serviceable() {
                @Override
                public Response serve(InputParams params) {

                    final String objectClass = params.getString("objectClass");
                    try {
                        StringCache<String> cache = Cashew.getCache(objectClass);
                        return new ErrorResponse("Cache exists", "A cache for this class already exists.");
                    } catch (NoCacheException e) {
                        long ttl = Cashew
                        long interval;
                        if ()
                        Cashew.createCache()
                    }

                }
            })
            .build();

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

    public static final Service LIST_SERVICE = new Service.Builder()
            .expectParam("objectClass", ParamType.STRING, true)
            .implement(new Serviceable() {
                @Override
                public Response serve(InputParams params) {

                    final String objectClass = params.getString("objectClass");
                    try {
                        StringCache<String> cache = Cashew.getCache(objectClass);

                        final JsonArray jsonArray = new JsonArray(cache.size());

                        cache.forAll((key, item) -> {
                            final class InterimArrayItem {
                                public String key;
                                public CacheItem<String> value;
                            }
                            InterimArrayItem arrayItem = new InterimArrayItem();
                            arrayItem.key = key;
                            arrayItem.value = item;
                            JsonElement jsonElement = new Gson().toJsonTree(arrayItem);
                            jsonArray.add(jsonElement);
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
