import com.raylabz.cashew.Cache;
import com.raylabz.cashew.CacheType;
import com.raylabz.cashew.Cashew;
import com.raylabz.cashew.NoCacheException;

public class Example {

    public static void main(String[] args) {
        Cashew.registerClass(Person.class, CacheType.STRING);
        try {
            final Cache<String, Person> cache = Cashew.getCache(Person.class);
            Person person = new Person("nicos", "kasenides", 27);
            cache.put(person.getFirstname(), person);

            final Person person1 = cache.get(person.getFirstname());
            System.out.println(person1);


        } catch (NoCacheException e) {
            e.printStackTrace();
        }

    }

}
