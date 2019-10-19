package me.jcomo.foodie.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import me.jcomo.foodie.core.SearchRequest;
import me.jcomo.stilltasty.client.StillTastyClient;
import me.jcomo.stilltasty.core.SearchResult;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class StillTastySearchResourceTest {
    private static final StillTastyClient client = mock(StillTastyClient.class);

    private static final StillTastySearchResource resource = new StillTastySearchResource(client);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(resource).build();

    private final List<SearchResult> results =
            Collections.singletonList(new SearchResult("Watermelon", "http://localhost/items/123"));

    @Before
    public void setUp() throws Exception {
        when(client.search(eq("watermelon"))).thenReturn(results);
    }

    @After
    public void tearDown() throws Exception {
        reset(client);
    }

    @Test
    public void testSearchReturnsResults() throws Exception {
        SearchRequest req = new SearchRequest();
        req.setQuery("watermelon");

        List<SearchResult> actualResults = resource.search(req);
        assertThat(actualResults).isEqualTo(results);
    }

    @Test
    public void testValidSearchReturnsOK() throws Exception {
        URI searchUri = UriBuilder.fromUri("/search").queryParam("q", "some food").build();
        Response response = resources.client().target(searchUri).request().get();
        assertEquals(200, response.getStatus());
        verify(client).search("some food");
    }

    @Test
    public void testInvalidSearchReturnsBadResponse() throws Exception {
        URI searchUri = UriBuilder.fromUri("/search").queryParam("q", "no").build();
        Response response = resources.client().target(searchUri).request().get();
        assertEquals(400, response.getStatus());
        verify(client, never()).search("no");
    }
}