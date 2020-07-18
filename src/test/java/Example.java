import com.raylabz.cashew.Cache;
import com.raylabz.cashew.Cashew;
import com.raylabz.cashew.NoCacheException;

public class Example {

    public static void main(String[] args) {
        Cashew.registerClass(Person.class, 30000, 1000);
        long startTime = System.currentTimeMillis();
        try {
            final Cache<String, Person> cache = Cashew.getCache(Person.class);
            Person person = new Person("nicos", "kasenides", 27);
            cache.put(person.getFirstname(), person);

            while (true) {
                System.out.println("Time since start: " + (System.currentTimeMillis() - startTime));
                System.out.println("Size: " + cache.size());
                Thread.sleep(2000);
            }

        } catch (NoCacheException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
