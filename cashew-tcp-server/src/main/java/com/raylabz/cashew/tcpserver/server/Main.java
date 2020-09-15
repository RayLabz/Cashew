package com.raylabz.cashew.tcpserver.server;

import com.raylabz.mocha.server.Mocha;

public class Main {

    public static void main(String[] args) {
        CashewTCPServer cashewTCPServer = new CashewTCPServer();
        Mocha.start(cashewTCPServer);
    }

}
