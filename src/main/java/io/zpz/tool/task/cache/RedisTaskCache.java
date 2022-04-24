package io.zpz.tool.task.cache;

import redis.clients.jedis.Jedis;

public class RedisTaskCache implements TaskCache {

    private Jedis jedis;

    public RedisTaskCache(String redisUrl, String password) {
        this.jedis = new Jedis(redisUrl);
        if (password != null) {
            jedis.auth(password);
        }
    }

    @Override
    public boolean containsCacheKey(String cacheKey) {
        return jedis.exists(cacheKey);
    }

    @Override
    public void addCacheKey(String key, String value) {
        jedis.set(key, value);
    }


    public static void main(String[] args) {
        // 测试redis。
    }
}
