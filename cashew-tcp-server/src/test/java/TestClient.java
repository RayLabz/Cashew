import com.raylabz.cashew.tcpserver.client.CashewTCPClient;
import com.raylabz.mocha.server.Mocha;

import java.io.IOException;

public class TestClient {

    public static void main(String[] args) throws IOException {
        final CashewTCPClient client = new CashewTCPClient("localhost");;
        Mocha.start(client);
    }

}
