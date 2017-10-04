package cacheengine;

import java.lang.management.MemoryNotificationInfo;
import javax.management.Notification;

/**
 * Created by Serdar on 04.10.2017.
 */
public class GCEventListener implements javax.management.NotificationListener {

  @Override
  public void handleNotification(Notification notification, Object handback) {
    String notifType = notification.getType();
    if (notifType.equals(notifType.equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED) ||
        notifType.equals(MemoryNotificationInfo.MEMORY_COLLECTION_THRESHOLD_EXCEEDED))) {
      if(handback instanceof CacheEngine){
        ((CacheEngine) handback).clearCache();
      }
    }
  }
}
