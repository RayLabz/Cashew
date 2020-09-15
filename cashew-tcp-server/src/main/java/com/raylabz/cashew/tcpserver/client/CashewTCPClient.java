package com.raylabz.cashew.tcpserver.client;

import com.google.gson.Gson;
import com.raylabz.cashew.tcpserver.server.CashewTCPServer;
import com.raylabz.cashew.tcpserver.server.request.AddRequest;
import com.raylabz.cashew.tcpserver.server.request.GetRequest;
import com.raylabz.mocha.client.TCPClient;

import java.io.IOException;

public class CashewTCPClient extends TCPClient {

    public CashewTCPClient(String ipAddress) throws IOException {
        super("Cashew client", ipAddress, CashewTCPServer.PORT);
    }

    @Override
    public void onConnectionRefused() {
        System.err.println("Connection refused.");
    }

    @Override
    public void initialize() {
        //TODO REMOVE - make abstract
        AddRequest addRequest = new AddRequest("key1", "value1", null);
        String sending = new Gson().toJson(addRequest);
        System.out.println("Sending: " + sending);
        send(sending);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GetRequest getRequest = new GetRequest("key1", null);
        sending = new Gson().toJson(getRequest);
        System.out.println("Sending: " + sending);
        send(sending);

    }

    @Override
    public void process() {

    }

    @Override
    public void onReceive(String data) {
        System.out.println("Got: " + data);
    }

}
