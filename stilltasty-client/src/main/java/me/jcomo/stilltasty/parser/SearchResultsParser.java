package me.jcomo.stilltasty.parser;

import me.jcomo.stilltasty.core.SearchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchResultsParser implements HtmlParser<List<SearchResult>> {
    public List<SearchResult> parse(String html) {
        Document doc = Jsoup.parse(html);
        Elements searchResults = doc.getElementsByClass("categorySearch");
        if (searchResults.size() > 0) {
            return parseResults(searchResults);
        } else {
            return Collections.emptyList();
        }
    }

    private List<SearchResult> parseResults(Elements searchResults) {
        return searchResults
                .stream()
                .flatMap(this::parseCategory)
                .collect(Collectors.toList());
    }

    private Stream<SearchResult> parseCategory(Element searchResultCategory) {
        return searchResultCategory.getElementsByTag("a")
                .stream()
                .map(e -> new SearchResult(e.text(), e.attr("href")))
                .filter(r -> r.getUrl() != null);
    }
}
