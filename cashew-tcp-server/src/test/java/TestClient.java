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

        try {

            Person person = new Person("Nicos", "Kasenides");
            doAddRequest("person1", person, Person.class);
            Thread.sleep(1000);

            doGetRequest("person1", Person.class);
            Thread.sleep(1000);

            doAddRequest("myNumber", 3, null);
            Thread.sleep(1000);

            doGetRequest("myNumber", null);
            Thread.sleep(1000);

            Person person2 = new Person("Panayiota", "Michaelides");
            doAddRequest("person2", person2, Person.class);
            Thread.sleep(1000);

            doListRequest(Person.class);
            Thread.sleep(1000);

            doDeleteRequest("person1", Person.class);
            Thread.sleep(1000);

            stop();

        }
        catch (Exception e) {
            e.printStackTrace();
        }


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
