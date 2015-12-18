package me.jcomo.foodie;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import me.jcomo.foodie.config.JedisPoolFactory;

import javax.validation.Valid;

@SuppressWarnings("unused")
public class FoodieConfiguration extends Configuration {
    @JsonProperty
    private String stillTastyCachePrefix = "";

    @JsonProperty
    private String sessionsCachePrefix = "";

    @Valid
    @JsonProperty("redis")
    private JedisPoolFactory jedisPool = new JedisPoolFactory();

    @Valid
    @JsonProperty("db")
    private DataSourceFactory databaseConfiguration = new DataSourceFactory();

    @Valid
    @JsonProperty("httpClient")
    private HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration();

    public String getStillTastyCachePrefix() {
        return stillTastyCachePrefix;
    }

    public void setStillTastyCachePrefix(String stillTastyCachePrefix) {
        this.stillTastyCachePrefix = stillTastyCachePrefix;
    }

    public String getSessionsCachePrefix() {
        return sessionsCachePrefix;
    }

    public void setSessionsCachePrefix(String sessionsCachePrefix) {
        this.sessionsCachePrefix = sessionsCachePrefix;
    }

    public JedisPoolFactory getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPoolFactory jedisPool) {
        this.jedisPool = jedisPool;
    }

    public DataSourceFactory getDataSourceFactory() {
        return databaseConfiguration;
    }

    public void setDatabaseConfiguration(DataSourceFactory databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    public HttpClientConfiguration getHttpClientConfiguration() {
        return httpClientConfiguration;
    }

    public void setHttpClientConfiguration(HttpClientConfiguration httpClientConfiguration) {
        this.httpClientConfiguration = httpClientConfiguration;
    }
}
