package me.jcomo.foodie.resources;

import io.dropwizard.auth.Auth;
import me.jcomo.foodie.api.UserAuthRequest;
import me.jcomo.foodie.core.User;
import me.jcomo.foodie.services.LoginService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/session")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {
    private LoginService loginService;

    public SessionResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Path("/login")
    public String login(@Valid @BeanParam UserAuthRequest req) {
        return loginService.login(req.getUsername(), req.getPassword()).orElseThrow(() ->
            new NotAuthorizedException("Unauthorized"));
    }

    @POST
    @Path("/logout")
    public void logout(@Auth User user) {
        loginService.logout(user);
    }

}
