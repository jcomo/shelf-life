package me.jcomo.foodie;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.jcomo.foodie.api.StorageGuideSerializer;
import me.jcomo.foodie.cli.UpdateFixturesCommand;
import me.jcomo.foodie.health.RedisHealthCheck;
import me.jcomo.foodie.resources.StillTastyGuideResource;
import me.jcomo.foodie.resources.StillTastySearchResource;
import me.jcomo.foodie.tasks.ClearCacheTask;
import me.jcomo.foodie.wrapper.RedisCache;
import me.jcomo.foodie.wrapper.StillTastyCachedClient;
import me.jcomo.stilltasty.client.StillTastyHttpClient;
import org.apache.http.client.HttpClient;
import redis.clients.jedis.JedisPool;

public class FoodieApplication extends Application<FoodieConfiguration> {
    public static void main(String[] args) throws Exception {
        new FoodieApplication().run(args);
    }

    @Override
    public String getName() {
        return "foodie";
    }

    @Override
    public void initialize(Bootstrap<FoodieConfiguration> bootstrap) {
        bootstrap.addCommand(new UpdateFixturesCommand());
    }

    @Override
    public void run(FoodieConfiguration config, Environment environment) {
        final SimpleModule stillTastyModule = new SimpleModule("StillTastyModels");
        stillTastyModule.addSerializer(new StorageGuideSerializer());
        environment.getObjectMapper().registerModule(stillTastyModule);

        final HttpClient httpClient = new HttpClientBuilder(environment)
                .using(config.getHttpClientConfiguration())
                .build(getName());

        final JedisPool pool = config.getJedisPool().build(environment);
        final RedisCache cache = new RedisCache(pool, config.getCacheKeyPrefix());
        final StillTastyHttpClient client = new StillTastyHttpClient(httpClient);
        final StillTastyCachedClient cachedClient = new StillTastyCachedClient(cache, client);

        environment.jersey().register(new StillTastySearchResource(cachedClient));
        environment.jersey().register(new StillTastyGuideResource(cachedClient));

        environment.healthChecks().register("redis", new RedisHealthCheck(pool));

        environment.admin().addTask(new ClearCacheTask<>(cache));
    }
}
