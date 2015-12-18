package me.jcomo.foodie.resources;

import me.jcomo.foodie.api.UserAuthRequest;
import me.jcomo.foodie.core.User;
import me.jcomo.foodie.services.RegistrationService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/register")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {
    private final RegistrationService registrationService;

    public RegistrationResource(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @POST
    public User register(@Valid @BeanParam UserAuthRequest req) {
        return registrationService.register(req.getUsername(), req.getPassword()).orElseThrow(() ->
                new BadRequestException("User with that name already exists"));
    }
}
