//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.raylabz.cashew.tcpserver.client.CashewTCPClient;
//import com.raylabz.cashew.tcpserver.server.CashewTCPServer;
//import com.raylabz.cashew.tcpserver.server.request.AddRequest;
//import com.raylabz.cashew.tcpserver.server.request.GetRequest;
//import com.raylabz.mocha.client.TCPClient;
//import com.raylabz.mocha.server.Mocha;
//
//import java.io.IOException;
//
//public class TestClient extends CashewTCPClient {
//
//    public TestClient(String ipAddress) throws IOException {
//        super(ipAddress);
//    }
//
//    @Override
//    public void onConnectionRefused() {
//        System.err.println("Connection refused.");
//    }
//
//    @Override
//    public void initialize() {
//
//        long DELAY = 1000;
//
//        try {
//
//            doCreateCacheRequest(Person.class);
//            Thread.sleep(DELAY);
//
//            Person person = new Person("Nicos", "Kasenides");
//            doAddRequest("person1", person, Person.class);
//            Thread.sleep(DELAY);
//
//            doGetRequest("person1", Person.class);
//            Thread.sleep(DELAY);
//
//            doAddRequest("myNumber", 3, null);
//            Thread.sleep(DELAY);
//
//            doGetRequest("myNumber", null);
//            Thread.sleep(DELAY);
//
//            Person person2 = new Person("Panayiota", "Michaelides");
//            doAddRequest("person2", person2, Person.class);
//            Thread.sleep(DELAY);
//
//            doListRequest(Person.class);
//            Thread.sleep(DELAY);
//
//            doDeleteRequest("person1", Person.class);
//            Thread.sleep(DELAY);
//
//            stop();
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Override
//    public void process() {
//
//    }
//
//    @Override
//    public void onReceive(String data) {
//        System.out.println("Got: " + data);
//    }
//
//    public static void main(String[] args) throws IOException {
//        final TestClient client = new TestClient("localhost");
//        Mocha.start(client);
//    }
//
//}
