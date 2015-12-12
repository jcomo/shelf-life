package me.jcomo.foodie.managers;

import io.dropwizard.lifecycle.Managed;
import redis.clients.jedis.JedisPool;

public class JedisPoolManager implements Managed {
    private final JedisPool pool;

    public JedisPoolManager(JedisPool pool) {
        this.pool = pool;
    }

    @Override
    public void start() throws Exception {
        pool.getResource();
    }

    @Override
    public void stop() throws Exception {
        pool.destroy();
    }
}
