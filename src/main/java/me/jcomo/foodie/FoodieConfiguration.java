package me.jcomo.foodie;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import me.jcomo.foodie.config.JedisPoolFactory;

import javax.validation.Valid;

public class FoodieConfiguration extends Configuration {
    @JsonProperty
    private String cacheKeyPrefix = "";

    @Valid
    @JsonProperty("redis")
    private JedisPoolFactory jedisPool = new JedisPoolFactory();

    @Valid
    @JsonProperty("httpClient")
    private HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration();

    public String getCacheKeyPrefix() {
        return cacheKeyPrefix;
    }

    public void setCacheKeyPrefix(String cacheKeyPrefix) {
        this.cacheKeyPrefix = cacheKeyPrefix;
    }

    public JedisPoolFactory getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPoolFactory jedisPool) {
        this.jedisPool = jedisPool;
    }

    public HttpClientConfiguration getHttpClientConfiguration() {
        return httpClientConfiguration;
    }

    public void setHttpClientConfiguration(HttpClientConfiguration httpClientConfiguration) {
        this.httpClientConfiguration = httpClientConfiguration;
    }
}
