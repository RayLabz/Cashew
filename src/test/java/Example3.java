import com.raylabz.cashew.*;

public class Example3 {

    public static void main(String[] args) {
        Cashew.createCache("Person");

        try {
            final StringCache<String> personCache = Cashew.getCache("Person");
            personCache.add("Nicos", "1234");
            personCache.add("Panayiota", "4321");


            final String nicos = personCache.get("Nicos");
            final String panayiota = personCache.get("Panayiota");

            System.out.println("nicos->" + nicos);
            System.out.println("panayiota->" + panayiota);

            Cashew.forAll(personCache, new CacheIterator() {
                @Override
                public <T> void forEach(String key, CacheItem<T> item) {
                    System.out.println(key + ": " + item.getValue());
                }
            });


        } catch (NoCacheException e) {
            e.printStackTrace();
        }



    }

}
