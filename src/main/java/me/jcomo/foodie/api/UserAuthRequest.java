package me.jcomo.foodie.api;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;

public class UserAuthRequest {
    @NotNull
    @Length(min = 3)
    @FormParam("username")
    private String username;

    @NotNull
    @Length(min = 3)
    @FormParam("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
