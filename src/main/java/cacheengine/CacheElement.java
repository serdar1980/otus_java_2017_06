package cacheengine;

import java.lang.ref.SoftReference;

public class CacheElement<V>{
    private final long creationTime;
    private long lastAccessTime;
    private  V element;

    CacheElement(V element) {
        this.element = element;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public V getElement() {
        return element;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}
