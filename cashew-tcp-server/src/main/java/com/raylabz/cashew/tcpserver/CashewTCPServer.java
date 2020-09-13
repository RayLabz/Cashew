package com.raylabz.cashew.tcpserver;

import com.raylabz.mocha.server.Server;
import com.raylabz.mocha.server.TCPHandler;

public class CashewTCPServer extends Server {

    public static final int PORT = 50000;

    /**
     * Constructs a new server.
     *
     */
    public CashewTCPServer() {
        super("");
    }

    @Override
    public void initialize() {
        System.out.println("Initializing Cashew TCP Server...");
        addTCPHandler(new TCPHandler(PORT, (tcpConnection, data) -> {

        }));
    }

    @Override
    public void process() {

    }

}
