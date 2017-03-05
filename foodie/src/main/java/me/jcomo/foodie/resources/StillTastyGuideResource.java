package me.jcomo.foodie.resources;

import com.codahale.metrics.annotation.Timed;
import me.jcomo.stilltasty.client.StillTastyClient;
import me.jcomo.stilltasty.client.StillTastyClientException;
import me.jcomo.stilltasty.core.StorageGuide;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/guides/{foodId}")
@Produces(MediaType.APPLICATION_JSON)
public class StillTastyGuideResource {
    private final StillTastyClient client;

    public StillTastyGuideResource(StillTastyClient client) {
        this.client = client;
    }

    @GET
    @Timed
    public StorageGuide getStorageGuide(@PathParam("foodId") int foodId) throws StillTastyClientException {
        return client.guide(foodId).orElseThrow(NotFoundException::new);
    }
}
