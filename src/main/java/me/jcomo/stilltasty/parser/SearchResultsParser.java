package me.jcomo.stilltasty.parser;

import me.jcomo.stilltasty.core.SearchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultsParser implements HtmlParser<List<SearchResult>> {
    public List<SearchResult> parse(String html) {
        Document doc = Jsoup.parse(html);
        Element searchResults = doc.getElementsByClass("categorySearch").first();
        return searchResults.getElementsByTag("a")
                .stream()
                .map(e -> new SearchResult(e.text(), e.attr("href")))
                .filter(r -> r.getUrl() != null)
                .collect(Collectors.toList());
    }
}
