package io.zpz.tool.task.cache;

public interface TaskCache {

    boolean containsCacheKey(String cacheKey);

    void addCacheKey(String key, String value);

}
