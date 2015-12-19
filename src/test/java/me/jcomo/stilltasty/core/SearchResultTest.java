package me.jcomo.stilltasty.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchResultTest {

    @Test
    public void testExtractsItemIdFromUrl() throws Exception {
        SearchResult result = new SearchResult("Watermelon", "http://localhost/items/123");
        assertThat(result.getId()).isEqualTo(123);
    }

    @Test
    public void testHasNoIdWhenMissingFromUrl() throws Exception {
        SearchResult result = new SearchResult("Watermelon", "http://localhost/items");
        assertThat(result.getId()).isNull();
    }
}