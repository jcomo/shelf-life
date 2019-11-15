package me.jcomo.stilltasty.client;

import me.jcomo.stilltasty.core.SearchResult;
import me.jcomo.stilltasty.core.StorageGuide;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StillTastyHttpClientTest {
    private static final StillTastyHttpClient CLIENT = new StillTastyHttpClient(defaultHttpClient());

    @Test
    public void testSearch() throws Exception {
        List<SearchResult> results = CLIENT.search("watermelon");
        assertThat(results.size()).isGreaterThan(0);
    }

    @Test
    public void testGuide() throws Exception {
        Optional<StorageGuide> guide = CLIENT.guide(18665);
        assertThat(guide).isPresent();
    }

    private static HttpClient defaultHttpClient() {
        return HttpClientBuilder.create().build();
    }
}
