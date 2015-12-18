package me.jcomo.foodie.services;

import me.jcomo.foodie.core.User;
import me.jcomo.foodie.db.UsersDAO;

public class RegistrationService {
    private final UsersDAO users;

    public RegistrationService(UsersDAO users) {
        this.users = users;
    }

    public User register(String username, String password) {
        // TODO: what happens if the user already exists?
        final User newUser = new User(username, password);
        users.create(newUser);

        return newUser;
    }
}
