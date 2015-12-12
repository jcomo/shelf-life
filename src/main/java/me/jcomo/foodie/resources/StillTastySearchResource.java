package me.jcomo.foodie.resources;

import com.codahale.metrics.annotation.Timed;
import me.jcomo.foodie.core.SearchRequest;
import me.jcomo.stilltasty.client.StillTastyClient;
import me.jcomo.stilltasty.client.StillTastyClientException;
import me.jcomo.stilltasty.core.SearchResult;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class StillTastySearchResource {
    private final StillTastyClient client;

    public StillTastySearchResource(StillTastyClient client) {
        this.client = client;
    }

    @GET
    @Timed
    public List<SearchResult> search(@Valid @BeanParam SearchRequest req) throws StillTastyClientException {
        return client.search(req.getQuery());
    }
}
