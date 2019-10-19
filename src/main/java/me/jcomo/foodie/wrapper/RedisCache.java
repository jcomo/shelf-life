package me.jcomo.foodie.wrapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCache implements Cache<String, String> {
    private final JedisPool pool;
    private final String keyPrefix;

    public RedisCache(JedisPool pool) {
        this(pool, "");
    }

    public RedisCache(JedisPool pool, String keyPrefix) {
        this.pool = pool;
        this.keyPrefix = keyPrefix;
    }

    public String get(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(prefixedKey(key));
        }
    }

    public void set(String key, String value, int ttl) {
        try (Jedis jedis = pool.getResource()) {
            String prefixedKey = prefixedKey(key);
            jedis.set(prefixedKey, value);
            jedis.expire(prefixedKey, ttl);
        }
    }

    @Override
    public void clear() {
        try (Jedis jedis = pool.getResource()) {
            String globPattern = prefixedKey("*");
            jedis.keys(globPattern).forEach(jedis::del);
        }
    }

    private String prefixedKey(String key) {
        return keyPrefix + key;
    }
}
