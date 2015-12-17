package me.jcomo.foodie.services;

import me.jcomo.foodie.core.User;
import me.jcomo.foodie.db.UserDAO;

public class RegistrationService {
    private final UserDAO users;

    public RegistrationService(UserDAO users) {
        this.users = users;
    }

    public User register(String username, String password) {
        // TODO: what happens if the user already exists?
        final User newUser = new User(username, password);
        users.create(newUser);

        return newUser;
    }
}
