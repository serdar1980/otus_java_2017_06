package cacheengine;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import javax.management.NotificationEmitter;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {

  private static final int TIME_THRESHOLD_MS = 5;
  private final int maxElements;
  private final long lifeTimeMs;
  private final long idleTimeMs;
  private final boolean isEternal;
  private final Map<K, SoftReference<CacheElement<V>>> elements = new LinkedHashMap<>();
  private final Timer timer = new Timer();
  private int hit = 0;
  private int miss = 0;

  public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
    this.maxElements = maxElements;
    this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
    this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
    this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;

    MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
    NotificationEmitter emitter = (NotificationEmitter) mbean;
    GCEventListener listener = new GCEventListener();
    emitter.addNotificationListener(listener, null, null);
  }


  @Override
  public void put(K key, V elemet) {
    if (elements.size() == maxElements) {
      K firstKey = elements.keySet().iterator().next();
      elements.remove(firstKey);
    }

    elements.put(key, new SoftReference<CacheElement<V>>(new CacheElement<V>(elemet)));

    if (!isEternal) {
      if (lifeTimeMs != 0) {
        TimerTask lifeTimerTask = getTimerTask(key,
            lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
        timer.schedule(lifeTimerTask, lifeTimeMs);
      }
      if (idleTimeMs != 0) {
        TimerTask idleTimerTask = getTimerTask(key,
            idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
        timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
      }
    }
  }

  @Override
  public V get(Object key) {
    SoftReference<CacheElement<V>> reference = elements.get(key);
    V res = null;
    if (reference != null) {
      CacheElement<V> element = reference.get();
      if (element != null) {
        res = element.getElement();
        hit++;
      } else {
        miss++;
      }
    } else {
      miss++;
    }
    return res;
  }

  public int getHitCount() {
    return hit;
  }

  public int getMissCount() {
    return miss;
  }


  @Override
  public void dispose() {
    timer.cancel();
  }

  private TimerTask getTimerTask(final K key, Function<CacheElement<V>, Long> timeFunction) {
    return new TimerTask() {
      @Override
      public void run() {
        CacheElement<V> element = elements.get(key).get();
        if (element == null || isT1BeforeT2(timeFunction.apply(element),
            System.currentTimeMillis())) {
          elements.remove(key);
          this.cancel();
        }
      }
    };
  }

  @Override
  public void clearCache() {
    for(K key : elements.keySet()){
      CacheElement<V> element = elements.get(key).get();
      if (element == null ) {
        elements.remove(key);
      }
    }
  }

  private boolean isT1BeforeT2(long t1, long t2) {
    return t1 < t2 + TIME_THRESHOLD_MS;
  }
}
