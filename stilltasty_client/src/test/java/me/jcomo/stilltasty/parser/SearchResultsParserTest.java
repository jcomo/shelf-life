package me.jcomo.stilltasty.parser;

import me.jcomo.stilltasty.core.SearchResult;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchResultsParserTest {
    private static final SearchResultsParser PARSER = new SearchResultsParser();

    @Test
    public void testItReturnsEmptyListWithNoResults() {
        final String resultsHtml = "<html></html>";
        assertThat(PARSER.parse(resultsHtml)).isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    public void testItReturnsListOfResults() throws Exception {
        final String resultsHtml = fixture("search_single_category.html");
        final List<SearchResult> expected = Arrays.asList(
                new SearchResult("Watermelon — Fresh, Raw, Cut Up", "http://stilltasty.com/fooditems/index/18665"),
                new SearchResult("Watermelon — Fresh, Raw, Whole", "http://stilltasty.com/fooditems/index/18666"));

        assertThat(PARSER.parse(resultsHtml)).isEqualTo(expected);
    }

    @Test
    public void testItPullsFromAllCategoriesInSearchResults() throws Exception {
        final String resultsHtml = fixture("search_multi_category.html");
        final List<SearchResult> results = PARSER.parse(resultsHtml);

        assertThat(results).contains(
                new SearchResult("Steak Sauce, Commercially Bottled — Opened", "http://stilltasty.com/fooditems/index/18405"),
                new SearchResult("Shark Steak — Fresh, Cooked", "http://stilltasty.com/fooditems/index/18305"),
                new SearchResult("Bison Steak — Fresh, Raw", "http://stilltasty.com/fooditems/index/16531"));
    }
}