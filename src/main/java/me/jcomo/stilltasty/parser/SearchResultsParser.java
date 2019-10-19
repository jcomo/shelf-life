package me.jcomo.stilltasty.parser;

import me.jcomo.stilltasty.core.SearchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchResultsParser implements HtmlParser<List<SearchResult>> {
    public List<SearchResult> parse(String html) {
        Document doc = Jsoup.parse(html);
        Elements searchResults = doc.getElementsByClass("srclisting");
        if (searchResults.size() > 0) {
            return parseResults(searchResults);
        } else {
            return Collections.emptyList();
        }
    }

    private List<SearchResult> parseResults(Elements searchResults) {
        return searchResults
                .stream()
                .map(e -> e.getElementsByTag("a").first())
                .map(e -> new SearchResult(e.text(), e.attr("href")))
                .collect(Collectors.toList());
    }
}
