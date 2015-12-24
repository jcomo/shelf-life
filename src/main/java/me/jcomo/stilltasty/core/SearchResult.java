package me.jcomo.stilltasty.core;

import java.net.MalformedURLException;
import java.net.URL;

import static me.jcomo.stilltasty.core.Humanize.titleize;

public class SearchResult {
    private int id = 0;
    private String name;
    private URL url;

    public SearchResult(String name, String url) {
        this.name = titleize(name);
        try {
            this.url = new URL(url);
            this.id = pluckItemId();
        } catch (MalformedURLException e) {
            // Do nothing - we can't get the item id from a null url
        }
    }

    private int pluckItemId() {
        String[] urlParts = url.toString().split("/");
        String itemId = urlParts[urlParts.length - 1];
        return Integer.parseInt(itemId);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchResult that = (SearchResult) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url=" + url +
                '}';
    }
}
