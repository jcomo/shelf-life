package me.jcomo.stilltasty.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static me.jcomo.stilltasty.core.Humanize.titleize;

public class SearchResult {
    private Integer id;
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

    private Integer pluckItemId() {
        String[] urlParts = url.toString().split("/");
        String itemId = urlParts[urlParts.length - 1];
        try {
            return Integer.parseInt(itemId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getId() {
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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
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
