package me.jcomo.foodie.health;

import com.codahale.metrics.health.HealthCheck;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisHealthCheck extends HealthCheck {
    private final JedisPool pool;

    public RedisHealthCheck(JedisPool pool) {
        this.pool = pool;
    }

    @Override
    protected Result check() throws Exception {
        try (Jedis jedis = pool.getResource()) {
            return pingRedis(jedis);
        }
    }

    private Result pingRedis(Jedis jedis) {
        String pong = jedis.ping();
        if ("PONG".equals(pong)) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Invalid ping response received from Redis at " + redisConfig(jedis));
        }
    }

    private String redisConfig(Jedis jedis) {
        return jedis.getClient().getHost() + ":" + jedis.getClient().getPort();
    }
}
