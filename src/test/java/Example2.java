import com.raylabz.cashew.Cache;
import com.raylabz.cashew.Cashew;
import com.raylabz.cashew.NoCacheException;

import java.util.UUID;

public class Example2 {

    public static void main(String[] args) throws NoCacheException {
        Cashew.registerClass(Person.class, 30000, 1000);

        Cashew.getMainCache().put("counter", 1);

        int counter = (int) Cashew.getMainCache().get("counter");
        System.out.println("Counter = " + counter);

    }

}
