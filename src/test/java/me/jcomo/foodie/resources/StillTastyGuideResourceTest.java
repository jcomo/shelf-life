package me.jcomo.foodie.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import me.jcomo.stilltasty.client.StillTastyClient;
import me.jcomo.stilltasty.core.StorageGuide;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StillTastyGuideResourceTest {
    private static final StillTastyClient client = mock(StillTastyClient.class);

    private static final StillTastyGuideResource resource = new StillTastyGuideResource(client);

    @ClassRule
    public static final ResourceTestRule resources =
            ResourceTestRule.builder().addResource(resource).build();

    private static final StorageGuide guide =
            new StorageGuide("Watermelon", new ArrayList<>(), new ArrayList<>());

    @Before
    public void setUp() throws Exception {
        when(client.guide(123)).thenReturn(Optional.of(guide));
    }

    @After
    public void tearDown() throws Exception {
        reset(client);
    }

    @Test
    public void testReturnsGuideForFood() throws Exception {
        StorageGuide actualGuide = resource.getStorageGuide(123);
        assertThat(actualGuide).isEqualTo(guide);
    }

    @Test
    public void testGuideFoundReturnsOK() throws Exception {
        final Response response = resources.client().target("/guides/123").request().get();
        assertEquals(200, response.getStatus());
        verify(client).guide(123);
    }

    @Test
    public void testGuideNotFoundReturnsNotFound() throws Exception {
        when(client.guide(999)).thenReturn(Optional.empty());

        final Response response = resources.client().target("/guides/999").request().get();
        assertEquals(404, response.getStatus());
        verify(client).guide(999);
    }
}