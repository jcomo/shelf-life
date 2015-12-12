package me.jcomo.stilltasty.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class StillTastyHttpClient extends StillTastyClient {
    private HttpClient client;
    private String baseUrl = "http://stilltasty.com";

    public StillTastyHttpClient(HttpClient client) {
        this.client = client;
    }

    @Override
    public String fetchSearchResults(String query) throws StillTastyClientException {
        HttpGet req = new HttpGet(baseUrl + "/searchitems/search/" + query);
        return getResponseBody(req);
    }

    @Override
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
