package me.jcomo.foodie.wrapper;

import me.jcomo.stilltasty.client.StillTastyClient;
import me.jcomo.stilltasty.client.StillTastyClientException;
import org.apache.http.client.HttpClient;

public class StillTastyCachedClient extends StillTastyClient {
    private static final int DEFAULT_CACHE_TTL = 60 * 60 * 24;

    private final Cache<String, String> cache;
    private final int ttl;

    public StillTastyCachedClient(Cache<String, String> cache, HttpClient client) {
        this(cache, client, DEFAULT_CACHE_TTL);
    }

    public StillTastyCachedClient(Cache<String, String> cache, HttpClient client, int ttl) {
        super(client);

        this.cache = cache;
        this.ttl = ttl;
    }

    @Override
    public String fetchSearchResults(String query) throws StillTastyClientException {
        String searchKey = searchKeyFor(query);
        String cachedResult = cache.get(searchKey);

        if (cachedResult != null) {
            return cachedResult;
        } else {
            String result = super.fetchSearchResults(query);
            return cacheAndReturnResult(searchKey, result);
        }
    }

    @Override
    public String fetchGuide(int foodId) throws StillTastyClientException {
        String guideKey = guideKeyFor(foodId);
        String cachedResult = cache.get(guideKey);

        if (cachedResult != null) {
            return cachedResult;
        } else {
            String result = super.fetchGuide(foodId);
            return cacheAndReturnResult(guideKey, result);
        }
    }

    private String searchKeyFor(String query) {
        return "search::" + query;
    }

    private String guideKeyFor(int foodId) {
        return "guide::" + foodId;
    }

    private String cacheAndReturnResult(String key, String result) {
        cache.set(key, result, ttl);
        return result;
    }
}
