package me.jcomo.foodie.wrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PrefixedRedisCacheTest {
    private final JedisPool pool = mock(JedisPool.class);
    private final Jedis jedis = mock(Jedis.class);
    private final PrefixedRedisCache cache = new PrefixedRedisCache(pool, "cache::");

    @Before
    public void setUp() throws Exception {
        when(pool.getResource()).thenReturn(jedis);
    }

    @After
    public void tearDown() throws Exception {
        reset(jedis);
    }

    @Test
    public void testGet() throws Exception {
        cache.get("key");

        verify(jedis, times(1)).get("cache::key");
    }

    @Test
    public void testSet() throws Exception {
        cache.set("key", "value", 60);

        verify(jedis, times(1)).set("cache::key", "value");
        verify(jedis, times(1)).expire("cache::key", 60);
    }

    @Test
    public void testDelete() throws Exception {
        cache.delete("key");

        verify(jedis, times(1)).del("cache::key");
    }

    @Test
    public void testClearUsesWildCard() throws Exception {
        Set<String> keys = new HashSet<>(Arrays.asList("cache::key1", "cache::key2"));
        when(jedis.keys(contains("*"))).thenReturn(keys);

        cache.clear();

        verify(jedis, times(1)).keys("cache::*");
        verify(jedis, times(1)).del("cache::key1");
        verify(jedis, times(1)).del("cache::key2");
    }
}