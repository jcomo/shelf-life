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
        final String resultsHtml = fixture("search.html");
        final List<SearchResult> expected = Arrays.asList(
                new SearchResult("Watermelon — Fresh, Raw, Cut Up", "http://stilltasty.com/fooditems/index/18665"),
                new SearchResult("Watermelon — Fresh, Raw, Whole", "http://stilltasty.com/fooditems/index/18666"));

        assertThat(PARSER.parse(resultsHtml)).isEqualTo(expected);
    }
}