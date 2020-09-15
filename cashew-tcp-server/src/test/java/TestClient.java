import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.raylabz.cashew.tcpserver.client.CashewTCPClient;
import com.raylabz.cashew.tcpserver.server.CashewTCPServer;
import com.raylabz.cashew.tcpserver.server.request.AddRequest;
import com.raylabz.cashew.tcpserver.server.request.GetRequest;
import com.raylabz.mocha.client.TCPClient;
import com.raylabz.mocha.server.Mocha;

import java.io.IOException;

public class TestClient extends CashewTCPClient {

    public TestClient(String ipAddress) throws IOException {
        super(ipAddress);
    }

    @Override
    public void onConnectionRefused() {
        System.err.println("Connection refused.");
    }

    @Override
    public void initialize() {
        Person person = new Person("Nicos", "Kasenides");
        final JsonElement jsonElement = new Gson().toJsonTree(person);
        AddRequest addRequest = new AddRequest("key1", new Gson().toJson(jsonElement), null);
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

    public static void main(String[] args) throws IOException {
        final TestClient client = new TestClient("localhost");
        Mocha.start(client);
    }

}
