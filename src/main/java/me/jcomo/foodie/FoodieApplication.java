package me.jcomo.foodie;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.jcomo.foodie.api.StorageGuideSerializer;
import me.jcomo.foodie.auth.SessionAuthenticator;
import me.jcomo.foodie.cli.UpdateFixturesCommand;
import me.jcomo.foodie.core.User;
import me.jcomo.foodie.db.SessionDAO;
import me.jcomo.foodie.db.UserDAO;
import me.jcomo.foodie.health.RedisHealthCheck;
import me.jcomo.foodie.resources.RegistrationResource;
import me.jcomo.foodie.resources.SessionResource;
import me.jcomo.foodie.resources.StillTastyGuideResource;
import me.jcomo.foodie.resources.StillTastySearchResource;
import me.jcomo.foodie.services.LoginService;
import me.jcomo.foodie.services.RegistrationService;
import me.jcomo.foodie.tasks.ClearCacheTask;
import me.jcomo.foodie.wrapper.PrefixedRedisCache;
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
        final PrefixedRedisCache stillTastyCache = new PrefixedRedisCache(pool, config.getCacheKeyPrefix());
        final StillTastyHttpClient client = new StillTastyHttpClient(httpClient);
        final StillTastyCachedClient cachedClient = new StillTastyCachedClient(stillTastyCache, client);

        final UserDAO users = new UserDAO();

        final PrefixedRedisCache sessionCache = new PrefixedRedisCache(pool, "sessions::");
        final SessionDAO sessions = new SessionDAO(sessionCache, users);

        final RegistrationService registrationService = new RegistrationService(users);
        final LoginService loginService = new LoginService(users, sessions);

        environment.jersey().register(new StillTastySearchResource(cachedClient));
        environment.jersey().register(new StillTastyGuideResource(cachedClient));

        environment.jersey().register(new RegistrationResource(registrationService));
        environment.jersey().register(new SessionResource(loginService));

        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new SessionAuthenticator(sessions))
                        .setPrefix("Bearer")
                        .buildAuthFilter()));

        environment.healthChecks().register("redis", new RedisHealthCheck(pool));

        environment.admin().addTask(new ClearCacheTask<>(stillTastyCache));
    }
}
