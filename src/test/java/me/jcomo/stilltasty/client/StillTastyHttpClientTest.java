package me.jcomo.stilltasty.client;

import me.jcomo.stilltasty.core.SearchResult;
import me.jcomo.stilltasty.core.StorageGuide;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StillTastyHttpClientTest {
    private static final HttpResponse FAKE_RESPONSE;
    private static final StillTastyClient CLIENT =
            new StillTastyHttpClient(HttpClients.createMinimal());

    static {
        FAKE_RESPONSE = mock(HttpResponse.class);
        when(FAKE_RESPONSE.getEntity()).thenReturn(new StringEntity("content", "UTF-8"));
    }

    @Test
    public void testItCallsTheAppropriateSearchEndpoint() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        StillTastyClient client = new StillTastyHttpClient(httpClient);

        when(httpClient.execute(any())).thenReturn(FAKE_RESPONSE);
        client.search("watermelon");

        ArgumentCaptor<HttpUriRequest> argument = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClient).execute(argument.capture());
        assertThat(argument.getValue().getURI().toString())
                .isEqualTo("http://stilltasty.com/searchitems/search/watermelon");
    }

    @Test
    public void testItCallsTheAppropriateGuideEndpoint() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        StillTastyClient client = new StillTastyHttpClient(httpClient);

        when(httpClient.execute(any())).thenReturn(FAKE_RESPONSE);
        client.guide(123);

        ArgumentCaptor<HttpUriRequest> argument = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClient).execute(argument.capture());
        assertThat(argument.getValue().getURI().toString())
                .isEqualTo("http://stilltasty.com/fooditems/index/123");
    }

    @Test
    public void testItGetsSearchResults() throws Exception {
        List<SearchResult> results = CLIENT.search("watermelon");

        assertThat(results).isNotEmpty();
        assertThat(results)
                .filteredOn((r) -> r.getName().toLowerCase().contains("watermelon"))
                .isEqualTo(results);
    }

    @Test
    public void testItGetsRealStorageGuides() throws Exception {
        StorageGuide guide = CLIENT.guide(18665).get();

        assertThat(guide.getFood()).isNotEmpty();
        assertThat(guide.getStorageMethods()).isNotEmpty();
        assertThat(guide.getStorageTips()).isNotEmpty();
    }
}