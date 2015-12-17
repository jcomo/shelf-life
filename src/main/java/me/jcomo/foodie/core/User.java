package me.jcomo.foodie.core;

import java.security.Principal;

public class User implements Principal {
    private String username;
    private String passwordHash;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    @Override
    public String getName() {
        return getUsername();
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean passwordMatches(String password) {
        return hashPassword(password).equals(getPasswordHash());
    }

    public static String hashPassword(String password) {
        return password;
    }
}


