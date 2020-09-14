import com.raylabz.cashew.*;
import com.raylabz.cashew.listener.OnItemDeleteListener;
import com.raylabz.cashew.listener.OnItemUpdateListener;

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

            final CacheItem<String> nicosCacheItem = personCache.getAsCacheItem("Nicos");
            nicosCacheItem.setOnItemUpdateListener(new OnItemUpdateListener() {
                @Override
                public void onUpdate(CacheItem<?> item) {
                    System.out.println("The item with key 'Nicos' was updated: " + item.getValue() + "-- updated on: " + item.getUpdatedOn());
                }
            });

            nicosCacheItem.setOnItemDeleteListener(new OnItemDeleteListener() {
                @Override
                public void onDelete(CacheItem<?> item) {
                    System.out.println("Item with key 'Nicos' was deleted : " + item.getValue());
                }
            });

            personCache.update("Nicos", "x123x");

            Thread.sleep(1000);

            personCache.delete("Nicos");

            System.out.println(personCache.size());


        } catch (NoCacheException | InterruptedException e) {
            e.printStackTrace();
        }



    }

}
