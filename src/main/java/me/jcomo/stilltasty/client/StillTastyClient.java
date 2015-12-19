package me.jcomo.stilltasty.client;

import me.jcomo.stilltasty.core.SearchResult;
import me.jcomo.stilltasty.core.StorageGuide;
import me.jcomo.stilltasty.parser.SearchResultsParser;
import me.jcomo.stilltasty.parser.StorageGuideParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StillTastyClient {
    private HttpClient client;
    private String baseUrl = "http://stilltasty.com";

    private final SearchResultsParser searchResultsParser = new SearchResultsParser();
    private final StorageGuideParser storageGuideParser = new StorageGuideParser();

    public StillTastyClient(HttpClient client) {
        this.client = client;
    }

    public List<SearchResult> search(String query) throws StillTastyClientException {
        return searchResultsParser.parse(fetchSearchResults(query));
    }

    public String fetchSearchResults(String query) throws StillTastyClientException {
        HttpGet req = new HttpGet(baseUrl + "/searchitems/search/" + query);
        return getResponseBody(req);
    }

    public Optional<StorageGuide> guide(int foodId) throws StillTastyClientException {
        return storageGuideParser.parse(fetchGuide(foodId));
    }

    public String fetchGuide(int foodId) throws StillTastyClientException {
        HttpGet req = new HttpGet(baseUrl + "/fooditems/index/" + foodId);
        return getResponseBody(req);
    }

    public String getResponseBody(HttpUriRequest request) throws StillTastyClientException {
        HttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            throw new StillTastyClientException("Failed to fetch data from StillTasty", e);
        }

        try {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
           throw new StillTastyClientException("Failed to parse response body", e);
        }
    }
}
