package me.jcomo.foodie.api;

import org.hibernate.validator.constraints.Length;

import javax.ws.rs.QueryParam;

public class SearchRequest {
    @Length(min = 3)
    @QueryParam("q")
    private String query = "";

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
