package com.raylabz.cashew.tcpserver.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.raylabz.cashew.tcpserver.ServiceType;
import com.raylabz.cashew.tcpserver.server.request.*;
import com.raylabz.mocha.server.Server;
import com.raylabz.mocha.server.TCPHandler;
import com.raylabz.responz.ErrorResponse;
import com.raylabz.responz.Response;
import com.raylabz.servexpresso.InputParam;
import com.raylabz.servexpresso.InputParams;

public class CashewTCPServer extends Server {

    public static final int PORT = 50000;

    /**
     * Constructs a new server.
     *
     */
    public CashewTCPServer() {
        super("Cashew Server");
    }

    @Override
    public void initialize() {
        System.out.println("Initializing Cashew TCP Server...");
        addTCPHandler(new TCPHandler(PORT, (tcpConnection, data) -> {

            System.out.println("Got: " + data);

            final Gson gson = new Gson();

            try {
                JsonObject request = gson.fromJson(data, JsonObject.class);

                switch (request.get("service").getAsString()) {
                    case "ADD":
                        try {
                            final InputParams params = new InputParams();
                            params.put("key", new InputParam("key", request.get("key").getAsString()));
                            params.put("value", new InputParam("value", request.get("value").getAsString()));
                            if (request.has("objectClass")) {
                                params.put("objectClass", new InputParam("objectClass", request.get("objectClass").getAsString()));
                            }
                            final Response response = ServiceType.ADD.getService().processRequest(params);
                            tcpConnection.send(response.toJSON());
                        } catch (JsonParseException addException) {
                            final ErrorResponse errorResponse = new ErrorResponse(
                                    "Command error",
                                    "Could not parse ADD request. Provide the following parameters as JSON object: key (String), value (String), objectClass (String)."
                            );
                            tcpConnection.send(errorResponse.toJSON());
                        }
                        break;
//                    case DELETE:
//                        try {
//                            final DeleteRequest deleteRequest = (DeleteRequest) request;
//                            final InputParams params = new InputParams();
//                            params.put("key", new InputParam("key", deleteRequest.getKey()));
//                            params.put("objectClass", new InputParam("objectClass", deleteRequest.getObjectClass()));
//                            final Response response = deleteRequest.getService().getService().processRequest(params);
//                            tcpConnection.send(response.toJSON());
//                        } catch (JsonParseException addException) {
//                            final ErrorResponse errorResponse = new ErrorResponse(
//                                    "Command error",
//                                    "Could not parse DELETE request. Provide the following parameters as JSON object: key (String), objectClass (String)."
//                            );
//                            tcpConnection.send(errorResponse.toJSON());
//                        }
//                        break;
//                    case UPDATE:
//                        try {
//                            final UpdateRequest updateRequest = (UpdateRequest) request;
//                            final InputParams params = new InputParams();
//                            params.put("key", new InputParam("key", updateRequest.getKey()));
//                            params.put("value", new InputParam("value", updateRequest.getValue()));
//                            params.put("objectClass", new InputParam("objectClass", updateRequest.getObjectClass()));
//                            final Response response = updateRequest.getService().getService().processRequest(params);
//                            tcpConnection.send(response.toJSON());
//                        } catch (JsonParseException addException) {
//                            final ErrorResponse errorResponse = new ErrorResponse(
//                                    "Command error",
//                                    "Could not parse UDPATE request. Provide the following parameters as JSON object: key (String), value (String), objectClass (String)."
//                            );
//                            tcpConnection.send(errorResponse.toJSON());
//                        }
//                        break;
                    case "GET":
                        try {
                            final InputParams params = new InputParams();
                            params.put("key", new InputParam("key", request.get("key").getAsString()));
                            if (request.has("objectClass")) {
                                params.put("objectClass", new InputParam("objectClass", request.get("objectClass").getAsString()));
                            }
                            final Response response = ServiceType.GET.getService().processRequest(params);
                            tcpConnection.send(response.toJSON());
                        } catch (JsonParseException addException) {
                            final ErrorResponse errorResponse = new ErrorResponse(
                                    "Command error",
                                    "Could not parse GET request. Provide the following parameters as JSON object: key (String), objectClass (String)."
                            );
                            tcpConnection.send(errorResponse.toJSON());
                        }
                        break;
//                    case LIST:
//                        try {
//                            final ListRequest listRequest = (ListRequest) request;
//                            final InputParams params = new InputParams();
//                            params.put("objectClass", new InputParam("objectClass", listRequest.getObjectClass()));
//                            final Response response = listRequest.getService().getService().processRequest(params);
//                            tcpConnection.send(response.toJSON());
//                        } catch (JsonParseException addException) {
//                            final ErrorResponse errorResponse = new ErrorResponse(
//                                    "Command error",
//                                    "Could not parse LIST request. Provide the following parameters as JSON object: objectClass (String)."
//                            );
//                            tcpConnection.send(errorResponse.toJSON());
//                        }
//                        break;
                }

            }
            catch (JsonParseException e) {
                final ErrorResponse errorResponse = new ErrorResponse(
                        "Unknown command request",
                        "Could not parse request. Requests should contain a service (ADD, REMOVE, UPDATE, GET, LIST) and corresponding parameters."
                );
                tcpConnection.send(errorResponse.toJSON());
            }

        }));
    }

    @Override
    public void process() {
        //DO NOTHING
    }

}
