package me.jcomo.foodie.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.dropwizard.jackson.Jackson;
import me.jcomo.stilltasty.core.SearchResult;
import me.jcomo.stilltasty.core.StorageGuide;
import me.jcomo.stilltasty.core.StorageMethod;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.dropwizard.testing.FixtureHelpers.*;
import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;

public class StillTastySerializationTest {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = Jackson.newObjectMapper();
        MAPPER.addMixIn(StorageGuide.class, StorageGuideMixin.class);
    }

    @Test
    public void testStorageGuideSerializesToJson() throws Exception {
        final List<String> tips = Arrays.asList("Leave seeds in watermelon", "Wrap with plastic");
        final List<StorageMethod> methods = Arrays.asList(
                new StorageMethod("Refrigerator", "4 days"),
                new StorageMethod("Freezer", "1 month"));
        final StorageGuide guide = new StorageGuide("Watermelon", methods, tips);

        final String expected = fixture("fixtures/storage_guide.json");

        assertThatJson(MAPPER.writeValueAsString(guide)).isEqualTo(expected);
    }

    @Test
    public void testStorageMethodSerializesToJson() throws Exception {
        final StorageMethod method = new StorageMethod("Freezer", "1 month");
        final String expected = fixture("fixtures/storage_method.json");

        assertThatJson(MAPPER.writeValueAsString(method)).isEqualTo(expected);
    }

    @Test
    public void testSearchResultSerializesToJson() throws Exception {
        final SearchResult result = new SearchResult("Watermelon", "http://localhost/items/123");
        final String expected = fixture("fixtures/search_result.json");

        assertThatJson(MAPPER.writeValueAsString(result)).isEqualTo(expected);
    }
}