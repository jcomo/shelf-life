package me.jcomo.stilltasty.client;

import me.jcomo.stilltasty.parser.SearchResultsParser;
import me.jcomo.stilltasty.parser.StorageGuideParser;
import me.jcomo.stilltasty.core.SearchResult;
import me.jcomo.stilltasty.core.StorageGuide;

import java.util.List;
import java.util.Optional;

public abstract class StillTastyClient {
    private final SearchResultsParser searchResultsParser = new SearchResultsParser();
    private final StorageGuideParser storageGuideParser = new StorageGuideParser();

    public abstract String fetchSearchResults(String query) throws StillTastyClientException;
    public abstract String fetchGuide(int foodId) throws StillTastyClientException;

    public List<SearchResult> search(String query) throws StillTastyClientException {
        return searchResultsParser.parse(fetchSearchResults(query));
    }

    public Optional<StorageGuide> guide(int foodId) throws StillTastyClientException {
        return storageGuideParser.parse(fetchGuide(foodId));
    }
}
