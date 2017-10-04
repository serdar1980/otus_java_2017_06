package cacheengine;
/**
 * Created by tully.
 */
public class CacheMain {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("eternalCacheExample");
    new CacheMain().eternalCacheExample();
    System.out.println("lifeCacheExample");
    new CacheMain().lifeCacheExample();
  }

  private void eternalCacheExample() {
    int size = 5;
    CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 0, 0, true);

    for (int i = 0; i < size * 2; i++) {
      cache.put(i, "String: " + i);
    }

    for (int i = 0; i < size * 2; i++) {
       String element = cache.get(i);
      System.out.println("String for " + i + ": " + (element != null ? element : "null"));
    }

    System.out.println("Cache hits: " + cache.getHitCount());
    System.out.println("Cache misses: " + cache.getMissCount());

    cache.dispose();
  }

  private void lifeCacheExample() throws InterruptedException {
    int size = 5;
    CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 1000, 0, false);

    for (int i = 0; i < size; i++) {
      cache.put(i, "String: " + i);
    }

    for (int i = 0; i < size; i++) {
       String element = cache.get(i);
      System.out.println("String for " + i + ": " + (element != null ? element : "null"));
    }

    System.out.println("Cache hits: " + cache.getHitCount());
    System.out.println("Cache misses: " + cache.getMissCount());

    Thread.sleep(1001);

    for (int i = 0; i < size; i++) {
      String element = cache.get(i);
      System.out.println("String for " + i + ": " + (element != null ? element : "null"));
    }

    System.out.println("Cache hits: " + cache.getHitCount());
    System.out.println("Cache misses: " + cache.getMissCount());

    cache.dispose();
  }
}
