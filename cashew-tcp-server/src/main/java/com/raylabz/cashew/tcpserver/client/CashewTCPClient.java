package com.raylabz.cashew.tcpserver.client;

import com.raylabz.cashew.tcpserver.server.CashewTCPServer;
import com.raylabz.mocha.client.TCPClient;

import java.io.IOException;

public abstract class CashewTCPClient extends TCPClient {

    public CashewTCPClient(String ipAddress) throws IOException {
        super("Cashew client", ipAddress, CashewTCPServer.PORT);
    }

}
