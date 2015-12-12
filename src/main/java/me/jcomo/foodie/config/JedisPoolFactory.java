package me.jcomo.foodie.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.setup.Environment;
import me.jcomo.foodie.managers.JedisPoolManager;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class JedisPoolFactory {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 6379;
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 1024;

    @JsonProperty
    private String host = DEFAULT_HOST;

    @Min(1)
    @Max(65535)
    @JsonProperty
    private int port = DEFAULT_PORT;

    private String password;

    @JsonProperty
    private int timeout = Protocol.DEFAULT_TIMEOUT;

    @JsonProperty
    private int minIdle = 0;

    @JsonProperty
    private int maxIdle = 0;

    @JsonProperty
    private int maxTotal = DEFAULT_MAX_TOTAL_CONNECTIONS;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

        final JedisPool pool =
                new JedisPool(config, getHost(), getPort(), getTimeout(), getPassword());

        environment.lifecycle().manage(new JedisPoolManager(pool));

        return pool;
    }
}
