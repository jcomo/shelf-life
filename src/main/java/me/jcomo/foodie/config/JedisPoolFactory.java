package me.jcomo.foodie.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import me.jcomo.foodie.managers.JedisPoolManager;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URL;

public class JedisPoolFactory {
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 1024;

    @NotNull
    @JsonProperty
    private URI uri;

    @JsonProperty
    private int timeout = Protocol.DEFAULT_TIMEOUT;

    @JsonProperty
    private int minIdle = 0;

    @JsonProperty
    private int maxIdle = 0;

    @JsonProperty
    private int maxTotal = DEFAULT_MAX_TOTAL_CONNECTIONS;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public JedisPool build(Environment environment) {
        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(getMinIdle());
        config.setMaxIdle(getMaxIdle());
        config.setMaxTotal(getMaxTotal());

        final JedisPool pool = new JedisPool(config, getUri(), getTimeout());

        environment.lifecycle().manage(new Managed() {
            @Override
            public void start() throws Exception {
                pool.getResource();
            }

            @Override
            public void stop() throws Exception {
                pool.destroy();
            }
        });

        return pool;
    }
}
