package me.jcomo.foodie.resources;

import com.codahale.metrics.annotation.Timed;
import me.jcomo.foodie.api.SearchRequest;
import me.jcomo.stilltasty.client.StillTastyClient;
import me.jcomo.stilltasty.client.StillTastyClientException;
import me.jcomo.stilltasty.core.SearchResult;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @PermitAll
    public List<SearchResult> search(@Valid @BeanParam SearchRequest req) throws StillTastyClientException {
        return client.search(req.getQuery());
    }
}
